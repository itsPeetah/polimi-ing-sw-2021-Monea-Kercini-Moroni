package ingsw.pietro.test.network.server.components;

import java.util.ArrayList;

/**
 * Framework class for a game room.
 */
public class GameRoom {

    private String roomId;              // room id

    private Object lock;                // concurrency-safe table lock
    private ArrayList<String> userIDs;  // users in the room

    /**
     * Class constructor.
     */
    public GameRoom(String roomId) {
        this.roomId = roomId;

        this.lock = new Object();
        this.userIDs = new ArrayList<String>();
    }

    public String getId(){
        return roomId;
    }

    /**
     * Add a user to the room.
     * @param userID ID of the user to add.
     * @return whether the operation was successful.
     */
    public boolean addUser(String userID){
        boolean result = false;
        synchronized (lock) {
            if (!userIDs.contains(userID)) {
                userIDs.add(userID);
                result = true;
            }
        }
        return result;
    }

    /**
     * Check if a player is in the room.
     */
    public boolean playerIsIn(String id){
        boolean result;
        synchronized (lock){
            result = userIDs.contains(id);
        }
        return result;
    }

    /**
     * Get user array to iterate upon.
     */
    public String[] getUsers(){
        String[] result;
        synchronized (lock) {
            result = (String[]) userIDs.toArray();
        }
        return  result;
    }


}
