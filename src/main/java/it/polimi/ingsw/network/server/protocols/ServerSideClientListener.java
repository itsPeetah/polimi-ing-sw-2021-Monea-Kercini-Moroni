package it.polimi.ingsw.network.server.protocols;

import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.NetworkPacketType;
import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;
import it.polimi.ingsw.network.common.sysmsg.GameLobbyMessage;
import it.polimi.ingsw.network.server.components.RemoteUser;

public class ServerSideClientListener {

    private RemoteUser user;
    private boolean done;
    private boolean continueAfterReturning;

    public ServerSideClientListener(RemoteUser user){
        this.user = user;
    }

    public boolean run() {

        this.done = false;
        this.continueAfterReturning = false;

        while (true){
            if(done) break;

            NetworkPacket np = user.receive();
            switch (np.getPacketType()){
                case SYSTEM:
                    handleSystemMessage(np);
                    break;
                case DEBUG:
                    handleDebugMessage(np);
                    break;
                case ACTION:
                    handleActionPacket(np);
                    break;
            }
        }

        return continueAfterReturning;
    }

    private void handleSystemMessage(NetworkPacket packet){
        String clientMessage = packet.getPayload();

        // QUIT
        if(ConnectionMessage.QUIT.check(clientMessage)){
            done = true;
            continueAfterReturning = false;
            return;
        }
        // LEAVE
        else if (GameLobbyMessage.LEAVE_ROOM.check(clientMessage)){
            user.leaveCurrentRoom();
            done = true;
            continueAfterReturning = true;
            return;
        }
    }

    private void handleDebugMessage(NetworkPacket packet){
        String clientMessage = packet.getPayload();
        System.out.println("[USER "+ user.getId() + "] " + clientMessage);
        clientMessage = "[SERVER ECHO] You said: " + clientMessage;
        user.send(new NetworkPacket(NetworkPacketType.DEBUG, clientMessage));
    }

    private void handleActionPacket(NetworkPacket packet){
        user.getRoom().notify(packet);
    }
}
