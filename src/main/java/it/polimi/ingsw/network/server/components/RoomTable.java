package it.polimi.ingsw.network.server.components;

import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.sysmsg.GameLobbyMessage;

import java.util.Hashtable;

/**
 * Stores all the rooms in a server.
 */
public class RoomTable {

    private static RoomTable instance;

    private Object lock;
    private Hashtable<String, GameRoom> rooms;

    /**
     * Class constructor
     */
    public RoomTable(){
        this.lock = new Object();
        this.rooms = new Hashtable<String, GameRoom>();
    }

    /**
     * Make the instance the singleton instance.
     * @return the instance
     */
    public RoomTable setAsInstance() {
        instance = this;
        return this;
    }

    /**
     * Get the singleton instance;
     */
    public static RoomTable getInstance(){
        if(instance == null){
            instance = new RoomTable();
        }
        return instance;
    }

    /**
     * Register a user room its id.
     * @return whether the operation was successful.
     */
    private void add(GameRoom room){
        synchronized (lock){
            rooms.put(room.getId(), room);
        }
    }

    /**
     * Check whether a room with such id already exists.
     */
    public boolean exists(String roomId){
        boolean result;
        synchronized (lock){
            result = rooms.containsKey(roomId);
        }
        return result;
    }

    /**
     * Get a user using their id.
     */
    public GameRoom getRoom(String id){
        GameRoom room = null;
        synchronized (lock){
            if(rooms.containsKey(id)) room = rooms.get(id);
        }
        return room;
    }

    /**
     * Create a new room.
     * @param roomId Name of the new room.
     * @param ownerNickname Nickname of the owner (creator) of the room.
     * @param owner Owner user reference.
     * @throws GameRoomException if there is already a room with the same name.
     */
    public void createRoom(String roomId, String ownerNickname, RemoteUser owner) throws GameRoomException{
        synchronized (lock){
            // Throw exception if the room already exists.
            if(rooms.containsKey(roomId)) throw new GameRoomException("A room with name \"" + roomId + "\" already exists.");
            // Create room and add owner
            GameRoom room = new GameRoom(roomId);
            room.addUser(ownerNickname, owner);
            add(room);
        }
    }

    /**
     * Join an existing room.
     * @param roomId Name of the room to join.
     * @param nickname Nickname of the user.
     * @param user User reference.
     * @throws GameRoomException either if the room does not exist or if the name is already taken inside of that room.
     */
    public void joinRoom(String roomId, String nickname, RemoteUser user) throws GameRoomException{
        synchronized (lock){
            // Throw exception if the room doesn't exist.
            if(!rooms.containsKey(roomId)) throw new GameRoomException("There's no room with name \""+roomId+"\" on the server.");
            // Add user to the room
            GameRoom room = rooms.get(roomId);
            room.addUser(nickname, user);
        }
    }

    public void removeRoom(String roomId){
        synchronized (lock){
            GameRoom room = rooms.get(roomId);
            if(room != null){
                room.broadcast(NetworkPacket.buildSystemMessagePacket(GameLobbyMessage.IN_LOBBY.getCode()));
                rooms.remove(roomId);
            }
        }
    }
}
