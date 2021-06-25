package it.polimi.ingsw.network.server.components;

import it.polimi.ingsw.controller.model.ModelController;
import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.actions.data.NoneActionData;
import it.polimi.ingsw.controller.model.handlers.ModelControllerIOHandler;
import it.polimi.ingsw.controller.model.handlers.MPModelControllerIOHandler;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.NetworkPacketType;
import it.polimi.ingsw.network.common.social.SocialPacket;
import it.polimi.ingsw.network.common.SystemMessage;
import it.polimi.ingsw.network.server.GameServer;
import it.polimi.ingsw.util.JSONUtility;

import java.awt.desktop.SystemSleepEvent;
import java.util.Hashtable;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Framework class for a game room.
 */
public class GameRoom {

    private final String roomId;        // room id
    private final int maxPlayers;
    private Object lock;                // concurrency-safe table lock
    private Hashtable<String, RemoteUser> users;
    private final ModelControllerIOHandler modelControllerIOHandler;
    private ModelController modelController;
    private Hashtable<String, String> miaPlayers;
    private final AtomicBoolean mp;

    /**
     * Class constructor.
     */
    public GameRoom(String roomId, int maxPlayers) {
        this.roomId = roomId;
        this.maxPlayers = maxPlayers;
        this.lock = new Object();
        this.users = new Hashtable<String, RemoteUser>();
        this.modelControllerIOHandler = new MPModelControllerIOHandler(this);
        this.modelController = null;
        this.miaPlayers = new Hashtable<String, String>();
        this.mp = new AtomicBoolean(false);
    }

    /**
     * Room id getter.
     */
    public String getId(){
        return roomId;
    }

    public boolean gameInProgress(){
        return modelController != null;
    }

    /**
     * Add a user to the room.
     * @param nickname User's local (room) nickname.
     * @param user User reference.
     * @throws GameRoomException if the nickname is already taken.
     */
    public void addUser(String nickname, RemoteUser user) throws GameRoomException{

        if(gameInProgress()) throw new GameRoomException("The game has already started in this room!");
        if(nickname.contains(" ")) throw new GameRoomException("This nickname is not valid.");

        boolean startGame = false;
        synchronized (lock) {
            if(users.containsKey(nickname)) throw new GameRoomException("The nickname \"" + nickname +"\" is already taken in this room.");
            if(users.size() >= maxPlayers) throw new GameRoomException("The room is currently full.");
            users.put(nickname, user);
            user.assignRoom(roomId, nickname);
            if (users.size() >= maxPlayers) startGame = true;
        }
        StringJoiner stringJoiner = new StringJoiner(" ");
        for(String k: users.keySet())stringJoiner.add(k);
        String messageContent = SystemMessage.PLAYERS_IN_ROOM.addBody(stringJoiner.toString());
        broadcast(new NetworkPacket(NetworkPacketType.SYSTEM, messageContent));
    }

    /**
     * Rejoin a user to the room.
     * @param nickname Nickname of the user to rejoin.
     * @param user the user's new RemoteUser reference.
     */
    public void rejoinUser(String nickname, RemoteUser user) {
        synchronized (lock) {
            // Move the user from the MIA players to the room's user table
            miaPlayers.remove(user.getId());
            users.put(nickname, user);
            // Assign the room to the user
            user.assignRoom(roomId, nickname);
            System.out.println("User " + user.getId() + " REjoined room " + roomId + " as " + nickname + "!");
        }
        // Send catch up update
        sendTo(nickname, NetworkPacket.buildSystemMessagePacket(SystemMessage.IN_GAME.addBody(users.size() > 1 ? "mp" : "sp")));
        modelController.updateAll();
    }

    /**
     * Remove a user from the room.
     * @param nickname In-Room nickname of the user to remove.
     * @return whether the operation was successfully completed.
     */
    public boolean removeUser(String nickname){
        boolean result = false;
        synchronized (lock){
            if(users.containsKey(nickname)){
                RemoteUser removed = users.remove(nickname);
                result = true;
                // If the game is in progress save the player as MIA
                if(gameInProgress()){
                    System.out.println("[Room " + roomId + "] Player " + nickname + " (" + removed.getId()+ ") is MIA.");
                    markAsMIA(removed.getId(), nickname);
                    // Send MIA Action to the model controller
                    notifyMIA(nickname);
                } else {
                    // Update the player list for everyone.
                    StringJoiner stringJoiner = new StringJoiner(" ");
                    for(String k: users.keySet())stringJoiner.add(k);
                    String messageContent = SystemMessage.PLAYERS_IN_ROOM.addBody(stringJoiner.toString());
                    broadcast(new NetworkPacket(NetworkPacketType.SYSTEM, messageContent));
                }
            }

            if(users.size() < 1 && (!gameInProgress() || mp.get())) {
                System.out.println("Removing room '" + roomId +"' since it's been left empty.");
                GameServer.getInstance().getRoomTable().removeRoom(roomId);
            }
        }
        return result;
    }

    /**
     * Mark a user as missing in action (MIA).
     * @param userId ID of the user.
     * @param playerNickname In-game nickname of the user.
     */
    private void markAsMIA(String userId, String playerNickname) {
        synchronized (lock) {
            miaPlayers.put(userId, playerNickname);
        }
    }

    /**
     * Send a NP to a specific user in the room
     */
    public void sendTo(String player, NetworkPacket packet){
        synchronized (lock) {
            if (miaPlayers.containsValue(player) && NetworkPacketType.isGameRelated(packet)){
                // Send MIA Action to the model controller if the NP is game related
                notifyMIA(player);
            }
            if(users.containsKey(player))
                users.get(player).send(packet);
        }
    }

    /**
     * Broadcast a NP to all users in the room
     * @param packet
     */
    public void broadcast(NetworkPacket packet){
        for (String player: users.keySet()){
            /*users.get(player).send(packet);*/
            sendTo(player, packet);
        }
    }

    /**
     * Notify an action packet to the room's ModelControllerIOHandler
     * @param packet (ActionPacket) Network Packet to handle.
     */
    public void notify(NetworkPacket packet) {
        modelControllerIOHandler.notify(packet);
    }

    /**
     * Start the game in the room.
     * @return whether the operation could complete.
     */
    public boolean startGame() {

         if(gameInProgress())
             return false;

         mp.set(true);
         synchronized (lock){
             if(users.size() < 2){
                 mp.set(false);
             }
         }
        // Instantiate controller
        modelController = new ModelController(modelControllerIOHandler);
        // add players
        for(String player: users.keySet()) {
            modelController.addPlayer(player);
        }
        if(mp.get()) {
            // randomize order
            modelController.getGame().shufflePlayers();
        } else {
            modelController.setSinglePlayer(true);
        }

        // Send start
        String startMessage = SystemMessage.START_ROOM.addBody(mp.get() ? "mp" : "sp");
        broadcast(new NetworkPacket(NetworkPacketType.SYSTEM, startMessage));
        // Start the game
        modelController.setupGame();

        return true;
    }

    /**
     * Handle a social packet by either broadcasting it (CHAT) or sending it to the recipient (WHISPER).
     * @param networkPacket
     */
    public void handleSocialPacket(NetworkPacket networkPacket) {
        SocialPacket socialPacket = JSONUtility.fromJson(networkPacket.getPayload(), SocialPacket.class);
        switch(socialPacket.getType()) {
            case CHAT:
                broadcast(networkPacket);
                break;
            case WHISPER:
                sendTo(socialPacket.getTo(), networkPacket);
                break;
        }
    }

    /**
     * Check if a player is Missing In Action.
     * @return the nickname of the player who's missing.
     */
    public String checkMIA(String userId){
        synchronized (lock){
            return miaPlayers.get(userId);
        }
    }

    /**
     * Tell the room's ModelControllerIOHandler that the player is not in the room atm.
     * @param nickname In-Game nickname of the player.
     */
    private void notifyMIA(String nickname){
        NoneActionData nad = new NoneActionData();
        nad.setPlayer(nickname);
        notify(NetworkPacket.buildActionPacket(new ActionPacket(Action.DISCONNECTED, JSONUtility.toJson(nad, NoneActionData.class))));
        /*System.out.println("GameRoom.notifyMIA player " + nickname + " is not in the room");*/
    }

    /**
     * Check if the room can host any more players.
     */
    public boolean isFull() {
        synchronized (lock) {
            return maxPlayers <= users.size();
        }
    }
}