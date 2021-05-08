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

    public String getId(){
        return roomId;
    }

    public void addUser(String nickname, RemoteUser user) throws GameRoomException{
        synchronized (lock) {
            if(users.containsKey(nickname)) throw new GameRoomException("The nickname \"" + nickname +"\" is already taken in this room.");
            users.put(nickname, user);
            user.assignRoom(roomId, nickname);
        }
    }

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
        users.get(player).sendMessage(packet.toJson());
    }

    public void broadcast(NetworkPacket packet){
        for (String player: users.keySet()){
            users.get(player).sendMessage(packet.toJson());
        }
    }

    // todo redirect action network packets from SSCL
    public void notify(NetworkPacket packet) {
        modelControllerIOHandler.notify(packet);
    }

    // todo check ownership
    public boolean startGame() {
        modelController = new ModelController(modelControllerIOHandler);
        for(String player: users.keySet()) {
            modelController.addPlayer(player);
        }
        // todo shuffle players
        modelController.setupGame();
        return true;
    }
}