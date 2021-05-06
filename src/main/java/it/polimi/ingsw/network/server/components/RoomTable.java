package it.polimi.ingsw.network.server.components;

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

    public void joinRoom(String roomId, String nickname, RemoteUser user) throws GameRoomException{
        synchronized (lock){
            // Throw exception if the room doesn't exist.
            if(!rooms.containsKey(roomId)) throw new GameRoomException("There's no room with name \""+roomId+"\" on the server.");
            // Add user to the room
            GameRoom room = rooms.get(roomId);
            room.addUser(nickname, user);
        }
    }
}
