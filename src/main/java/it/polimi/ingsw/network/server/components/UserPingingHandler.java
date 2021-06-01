package it.polimi.ingsw.network.server.components;

import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;

import java.util.HashSet;

public class UserPingingHandler implements Runnable{

    private static final int pingWaitTimeMs = 5000;
    public static final int maxMissedPings = 3;

    private UserTable userTable;
    private HashSet<String> idsToKick;

    public UserPingingHandler(UserTable userTable){
        this.userTable = userTable;
        this.idsToKick = new HashSet<String>();
    }

    @Override
    public void run() {

        while(true){
            idsToKick.clear();

            userTable.broadcast(NetworkPacket.buildSystemMessagePacket(ConnectionMessage.PING.getCode()));

            try {
                Thread.sleep(pingWaitTimeMs);
            } catch (InterruptedException ex){
                continue;
            }

            userTable.checkPingResponses();
        }
    }
}
