package it.polimi.ingsw.network.client.persistence;

import java.util.prefs.Preferences;

public class ReconnectionInfo {
    private static final Preferences prefs = Preferences.userNodeForPackage(it.polimi.ingsw.network.client.persistence.ReconnectionInfo.class);

    private static final String clientIDKey = "CLIENT_ID";
    private static final String clientIDDefault = "";

    public static void saveID(String id){
        prefs.put(clientIDKey, id);
    }
    public static void resetID(){
        prefs.put(clientIDKey, clientIDDefault);
    }

    public static String loadID(){
        return prefs.get(clientIDKey, clientIDDefault);
    }

    public static boolean existsID(){
        return !clientIDDefault.equals(loadID());
    }
}
