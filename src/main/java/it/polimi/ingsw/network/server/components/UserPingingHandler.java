package it.polimi.ingsw.network.server.components;

import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;

public class UserPingingHandler implements Runnable{

    private static final int pingWaitTimeMs = 5000;

    private UserTable userTable;

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
