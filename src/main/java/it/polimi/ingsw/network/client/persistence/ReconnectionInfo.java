package it.polimi.ingsw.network.client.persistence;

import java.util.prefs.Preferences;

/**
 * Class for storing the reconnection info
 */
public class ReconnectionInfo {

    private static final Preferences prefs = Preferences.userNodeForPackage(it.polimi.ingsw.network.client.persistence.ReconnectionInfo.class);
    private static final String clientIDKey = "CLIENT_ID";
    private static final String clientIDDefault = "";

    /**
     * Save the given ID for future use.
     */
    public static void saveID(String id){
        prefs.put(clientIDKey, id);
    }

    /**
     * Reset the saved ID to the default value ("forget" the saved id).
     */
    public static void resetID(){
        prefs.put(clientIDKey, clientIDDefault);
    }

    /**
     * Load a previously saved ID
     * @return The previously saved ID or the default value for an unsaved ID.
     */
    public static String loadID(){
        return prefs.get(clientIDKey, clientIDDefault);
    }

    /**
     * Check if an ID has actually been saved.
     */
    public static boolean existsID(){
        return !clientIDDefault.equals(loadID());
    }
}
