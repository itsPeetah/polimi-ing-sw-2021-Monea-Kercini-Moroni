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

/**
 * Framework class for a game room.
 */
public class GameRoom {

    private String roomId;              // room id
    private Object lock;                // concurrency-safe table lock
    private Hashtable<String, RemoteUser> users;
    private final ModelControllerIOHandler modelControllerIOHandler;
    private ModelController modelController;
    private Hashtable<String, String> miaPlayers;

    /**
     * Class constructor.
     */
    public GameRoom(String roomId) {
        this.roomId = roomId;

        this.lock = new Object();
        this.users = new Hashtable<String, RemoteUser>();
        this.modelControllerIOHandler = new MPModelControllerIOHandler(this);
        this.modelController = null;
        this.miaPlayers = new Hashtable<String, String>();
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

        synchronized (lock) {
            if(users.containsKey(nickname)) throw new GameRoomException("The nickname \"" + nickname +"\" is already taken in this room.");
            users.put(nickname, user);
            user.assignRoom(roomId, nickname);
        }
        StringJoiner stringJoiner = new StringJoiner(" ");
        for(String k: users.keySet())stringJoiner.add(k);
        String messageContent = SystemMessage.PLAYERS_IN_ROOM.addBody(stringJoiner.toString());
        broadcast(new NetworkPacket(NetworkPacketType.SYSTEM, messageContent));
    }

    public void rejoinUser(String nickname, RemoteUser user) {
        synchronized (lock) {
            miaPlayers.remove(user.getId());
            users.put(nickname, user);
            user.assignRoom(roomId, nickname);
            System.out.println("User " + user.getId() + " rejoined room " + roomId + " as " + nickname + "!");
        }
        // Send catch up update
        sendTo(nickname, NetworkPacket.buildSystemMessagePacket(SystemMessage.IN_GAME.getCode()));
        modelController.updateAll(nickname);
    }

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
                }
            }

            if(users.size() < 1) {
                System.out.println("Removing room '" + roomId +"' since it's been left empty.");
                GameServer.getInstance().getRoomTable().removeRoom(roomId);
            }
        }
        return result;
    }

    private void markAsMIA(String userId, String playerNickname) {
        synchronized (lock) {
            miaPlayers.put(userId, playerNickname);
        }
    }

    public void sendTo(String player, NetworkPacket packet){
        synchronized (lock) {
            if (miaPlayers.containsKey(player) && NetworkPacketType.isGameRelated(packet)){
                // Send MIA Action to the model controller
                notifyMIA(player);
            }
            if(users.containsKey(player))
                users.get(player).send(packet);
        }
    }

    public void broadcast(NetworkPacket packet){
        for (String player: users.keySet()){
            /*users.get(player).send(packet);*/
            sendTo(player, packet);
        }
    }

    /**
     * Notify an action packet to the room's IOHandler
     * @param packet (ActionPacket) Network Packet to handle.
     */
    public void notify(NetworkPacket packet) {
        modelControllerIOHandler.notify(packet);
    }

    /**
     * Start the game
     * @return
     */
    public boolean startGame() {

        System.out.println("GameRoom.startGame");

         if(gameInProgress())
             return false;

         synchronized (lock){
             if(users.size() < 2){
                 broadcast(NetworkPacket.buildSystemMessagePacket(SystemMessage.ERR.addBody("Can't start a multiplayer game by yourself.")));
                 return false;
             }
         }
        // Instantiate controller
        modelController = new ModelController(modelControllerIOHandler);
        // add players
        for(String player: users.keySet()) {
            modelController.addPlayer(player);
        }
        // randomize order
        modelController.getGame().shufflePlayers();
        // Send start
        System.out.println("GameRoom.startGame: Game set up in room " + roomId);
        String startMessage = SystemMessage.START_ROOM.addBody("Game started");
        broadcast(new NetworkPacket(NetworkPacketType.SYSTEM, startMessage));
        // Start the game
        modelController.setupGame();

        return true;
    }

    public void handleSocialPacket(NetworkPacket networkPacket) {
        SocialPacket socialPacket = JSONUtility.fromJson(networkPacket.getPayload(), SocialPacket.class);
        switch(socialPacket.getType()) {
            case CHAT:
                broadcast(networkPacket);
                break;
            case WHISPER:
                System.out.println("Sending WHISPER message");
                sendTo(socialPacket.getTo(), networkPacket);
                break;
        }
    }

    public String checkMIA(String userId){
        synchronized (lock){
            return miaPlayers.get(userId);
        }
    }

    private void notifyMIA(String nickname){
        NoneActionData nad = new NoneActionData();
        nad.setPlayer(nickname);
        notify(NetworkPacket.buildActionPacket(new ActionPacket(Action.DISCONNECTED, JSONUtility.toJson(nad, NoneActionData.class))));
        System.out.println("GameRoom.notifyMIA player " + nickname + " is not in the room");
    }
}