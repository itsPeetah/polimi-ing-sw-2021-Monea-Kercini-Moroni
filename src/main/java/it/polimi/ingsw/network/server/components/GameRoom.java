package it.polimi.ingsw.network.server.components;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.network.common.NetworkPacket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Framework class for a game room.
 */
public class GameRoom {

    private String roomId;              // room id
    private Object lock;                // concurrency-safe table lock
    private Hashtable<String, RemoteUser> users;

    /**
     * Class constructor.
     */
    public GameRoom(String roomId) {
        this.roomId = roomId;

        this.lock = new Object();
        this.users = new Hashtable<String, RemoteUser>();
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

    // TODO add NetworkPacket
    // TODO error handling?
    public void sendTo(String player, NetworkPacket packet){
        users.get(player).sendMessage(packet.toJson());
    }

    // TODO add NetworkPacket
    public void broadcast(NetworkPacket packet){
        for (String key: users.keySet()){
            users.get(key).sendMessage(packet.toJson());
        }
    }



}
