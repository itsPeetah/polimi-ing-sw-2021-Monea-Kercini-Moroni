package it.polimi.ingsw.network.server.protocols;

public class ConnectionSetupResult {

    private final String userID;
    private final boolean checkCanReconnect;

    public ConnectionSetupResult(String userID, boolean checkCanReconnect) {
        this.userID = userID;
        this.checkCanReconnect = checkCanReconnect;
    }

    public String getUserID() {
        return userID;
    }

    public boolean getCheckCanReconnect(){
        return checkCanReconnect;
    }
}
