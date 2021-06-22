package it.polimi.ingsw.network.server.components;

/**
 * Runnable class pinging handling the pinging of all users ina a single UserTable.
 */
public class UserPingingHandler implements Runnable{

    private static final int pingWaitTimeMs = 5000;

    private UserTable userTable;

    /**
     * Class constructor.
     */
    public UserPingingHandler(UserTable userTable){
        this.userTable = userTable;
    }

    @Override
    public void run() {

        while(true){
            userTable.pingAll();
            try {
                Thread.sleep(pingWaitTimeMs);
            } catch (InterruptedException ex){
                continue;
            }
            userTable.checkPingResponses();
        }
    }
}
