package it.polimi.ingsw.network.server.protocols;

import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.NetworkPacketType;
import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;
import it.polimi.ingsw.network.server.components.RemoteUser;

public class ServerSideClientListener {

    private RemoteUser user;
    private boolean done;

    public ServerSideClientListener(RemoteUser user){
        this.user = user;
        this.done = false;
    }

    public void run() {

        this.done = false;

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
    }

    private void handleSystemMessage(NetworkPacket packet){
        String clientMessage = packet.getPayload();

        if(ConnectionMessage.QUIT.check(clientMessage)){
            done = true;
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
