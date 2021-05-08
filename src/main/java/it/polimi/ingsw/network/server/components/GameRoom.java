package it.polimi.ingsw.network.server.components;

import it.polimi.ingsw.controller.model.ModelController;
import it.polimi.ingsw.controller.model.handlers.ModelControllerIOHandler;
import it.polimi.ingsw.controller.model.handlers.MPModelControllerIOHandler;
import it.polimi.ingsw.network.common.NetworkPacket;

import java.util.Hashtable;

/**
 * Framework class for a game room.
 */
public class GameRoom {

    private String roomId;              // room id
    private Object lock;                // concurrency-safe table lock
    private Hashtable<String, RemoteUser> users;
    private final ModelControllerIOHandler modelControllerIOHandler;
    private ModelController modelController;

    /**
     * Class constructor.
     */
    public GameRoom(String roomId) {
        this.roomId = roomId;

        this.lock = new Object();
        this.users = new Hashtable<String, RemoteUser>();
        this.modelControllerIOHandler = new MPModelControllerIOHandler(this);
        this.modelController = null;
    }

    /**
     * Room id getter.
     */
    public String getId(){
        return roomId;
    }

    /**
     * Add a user to the room.
     * @param nickname User's local (room) nickname.
     * @param user User reference.
     * @throws GameRoomException if the nickname is already taken.
     */
    public void addUser(String nickname, RemoteUser user) throws GameRoomException{
        synchronized (lock) {
            if(users.containsKey(nickname)) throw new GameRoomException("The nickname \"" + nickname +"\" is already taken in this room.");
            users.put(nickname, user);
            user.assignRoom(roomId, nickname);
        }
    }

    // TOFIX
    public boolean removeUser(String userID){
        boolean result = false;
        synchronized (lock){
            if(users.containsKey(userID)){
//                userIDs.remove(userID);
                result = true;
            }
        }
        return result;
    }

    // TODO error handling?
    public void sendTo(String player, NetworkPacket packet){
        users.get(player).sendSystemMessage(packet.toJson());
    }

    public void broadcast(NetworkPacket packet){
        for (String player: users.keySet()){
            users.get(player).sendSystemMessage(packet.toJson());
        }
    }

    // todo redirect action network packets from SSCL

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
        // Instantiate controller
        modelController = new ModelController(modelControllerIOHandler);
        // add players
        for(String player: users.keySet()) {
            modelController.addPlayer(player);
        }
        // randomize order
        modelController.getGame().shufflePlayers();
        // Start the game
        modelController.setupGame();
        // todo check ownership
        // todo shuffle players
        modelController.setupGame();
        return true;
    }
}