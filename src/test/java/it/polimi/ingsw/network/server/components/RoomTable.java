package ingsw.pietro.test.network.server.components;

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
    public boolean add(GameRoom room){
        boolean result;
        String id = room.getId();
        synchronized (lock){
            result = !rooms.containsKey(id);
            if(result) rooms.put(id, room);
        }
        return result;
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
}
