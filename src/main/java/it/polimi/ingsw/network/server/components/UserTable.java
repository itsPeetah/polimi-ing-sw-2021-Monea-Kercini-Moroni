package it.polimi.ingsw.network.server.components;

import ingsw.pietro.test.network.common.RemoteUser;

import java.util.Hashtable;
import java.util.Random;

/**
 * Class that stores the unique connected users.
 */
public class UserTable {

    private static UserTable instance;

    private Object lock;
    private Hashtable<String, RemoteUser> users;

    /**
     * Class constructor.
     */
    public UserTable(){
        lock = new Object();
        users = new Hashtable<String, RemoteUser>();
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
        return generateId(6);
    }
}
