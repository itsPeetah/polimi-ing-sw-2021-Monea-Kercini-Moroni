package it.polimi.ingsw.network.server.components;

import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;
import java.util.HashSet;

public class UserPingingHandler implements Runnable{

    private static final int pingWaitTimeMs = 5000;
    private static final int maxMissedPings = 3;

    private UserTable userTable;
    private HashSet<String> idsToKick;

    public UserPingingHandler(UserTable userTable){
        this.userTable = userTable;
        this.idsToKick = new HashSet<String>();
    }

    @Override
    public void run() {

        while(true){
            checkForDisconnectedUsers();
            checkForNewUsers();
            idsToKick.clear();

            userTable.broadcast(NetworkPacket.buildSystemMessagePacket(ConnectionMessage.PING.getCode()));

            try {
                Thread.sleep(pingWaitTimeMs);
            } catch (InterruptedException ex){
                continue;
            }

            goThroughResponses();
            kickMissingUsers();
        }
    }

    private void checkForNewUsers(){
        for(String id : userTable.getUserIDs()){
            if(!userTable.getMissedPings().containsKey(id))
                userTable.getMissedPings().put(id, 0);
        }
    }

    private void checkForDisconnectedUsers(){
        for(String id : userTable.getMissedPings().keySet()){
            if(userTable.getUser(id) == null)
                userTable.getMissedPings().remove(id);
        }
    }

    private void goThroughResponses(){
        for(String id: userTable.getMissedPings().keySet()){
            if(!userTable.checkPingResponse(id)){

                int val = userTable.getMissedPings().remove(id).intValue();
                if(val >= maxMissedPings ){
                    idsToKick.add(id);
                } else {
                    userTable.getMissedPings().put(id, val + 1);
                }
            } else {
                if(userTable.getMissedPings().get(id).intValue() > 0) {
                    userTable.getMissedPings().put(id, 0);
                }
            }
        }
    }

    private void kickMissingUsers(){
        for(String id : idsToKick){
            RemoteUser u = userTable.getUser(id);
            if(u != null) {
                u.terminateConnection();
                System.out.println("Kicked '" + id + "' for not responding to pings.");
            }
        }
    }

}
