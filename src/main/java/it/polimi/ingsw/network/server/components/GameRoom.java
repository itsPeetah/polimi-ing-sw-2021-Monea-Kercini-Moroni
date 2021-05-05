package it.polimi.ingsw.network.server.components;

import it.polimi.ingsw.model.game.Game;

import java.util.ArrayList;

/**
 * Framework class for a game room.
 */
public class GameRoom {

    private String roomId;              // room id

    private Object lock;                // concurrency-safe table lock
    private ArrayList<String> userIDs;  // users in the room
    private ArrayList<String> nicknames;

    /**
     * Class constructor.
     */
    public GameRoom(String roomId) {
        this.roomId = roomId;

        this.lock = new Object();
        this.userIDs = new ArrayList<String>();
        this.nicknames = new ArrayList<String>();
    }

    public String getId(){
        return roomId;
    }

    /**
     * Add a user to the room.
     * @param userID ID of the user to add.
     * @return whether the operation was successful.
     */
    public void addUser(String userID, String nickname) throws GameRoomException{
        synchronized (lock) {
            if(userIDs.contains(userID))
                throw new GameRoomException("This ID is already in this room!");
            if(nicknames.contains(nickname))
                throw new GameRoomException("The nickname is already taken for this room");
            userIDs.add(userID);
            nicknames.add(nickname);
        }
    }

    public boolean removeUser(String userID){
        boolean result = false;
        synchronized (lock){
            if(userID.contains(userID)){
                userIDs.remove(userID);
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
