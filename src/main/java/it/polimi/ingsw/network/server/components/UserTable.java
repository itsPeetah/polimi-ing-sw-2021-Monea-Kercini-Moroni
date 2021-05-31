package it.polimi.ingsw.network.server.components;

import it.polimi.ingsw.network.common.NetworkPacket;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;

/**
 * Class that stores the unique connected users.
 */
public class UserTable {

    private static final int DEFAULT_ID_LENGTH = 8;

    private static UserTable instance;

    private Object lock;
    private Hashtable<String, RemoteUser> users;
    private Hashtable<String, Integer> missedPings;
    private HashSet<String> pingResponses;

    /**
     * Class constructor.
     */
    public UserTable(){
        lock = new Object();
        users = new Hashtable<String, RemoteUser>();
        missedPings = new Hashtable<String, Integer>();
        pingResponses = new HashSet<String>();

        Thread pinger = new Thread(new UserPingingHandler(this));
        pinger.setDaemon(true);
        pinger.start();
    }

    public String[] getUserIDs(){
        synchronized (lock) {
            String[] ids = new String[users.size()];
            int i = 0;
            for (String id : users.keySet()) {
                ids[i] = id;
                i++;
            }
            return ids;
        }
    }

    public Hashtable<String, Integer> getMissedPings() {
        return missedPings;
    }

    /**
     * Make the instance the singleton instance.
     * @return the instance
     */
    public UserTable setAsInstance() {
        instance = this;
        return this;
    }

    /**
     * Get the singleton instance;
     */
    public static UserTable getInstance(){
        if(instance == null){
            instance = new UserTable();
        }
        return instance;
    }

    /**
     * Register a user using their id.
     * @return whether the operation was successful.
     */
    public boolean add(RemoteUser user){
        boolean result;
        String id = user.getId();
        synchronized (lock){
            result = !users.containsKey(id);
            if(result) users.put(id, user);
        }
        return result;
    }

    /**
     * Get a user using their id.
     */
    public RemoteUser getUser(String id){
        RemoteUser user = null;
        synchronized (lock){
            if(users.containsKey(id)) user = users.get(id);
        }
        return user;
    }

    public void removeUser(String id){
        synchronized (lock){
            if(getUser(id) != null){
                users.remove(id);
            }
        }
    }

    /**
     * Generate a new random alphanumeric id.
     */
    public String generateId(int targetStringLength){
        String newId;
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        //int targetStringLength = 10;
        Random random = new Random();
        synchronized (lock) {
            do {
                newId = random.ints(leftLimit, rightLimit + 1)
                        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                        .limit(targetStringLength)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();
            } while (users.containsKey(newId));
        }
        return newId;
    }

    /**
     * Generate a new random 6 character alphanumeric id.
     * @return
     */
    public String generateId() {
        return generateId(DEFAULT_ID_LENGTH);
    }

    public void broadcast(NetworkPacket np){
        synchronized (lock) {
            for (String id : users.keySet()) users.get(id).send(np);
        }
    }

    public void setPingResponseForUser(String id){
        //synchronized (lock){
            pingResponses.add(id);
        //}
    }

    public boolean checkPingResponse(String id){
        return pingResponses.remove(id);
    }
}
