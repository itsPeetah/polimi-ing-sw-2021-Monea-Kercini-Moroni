package it.polimi.ingsw.network.client.protocols;

import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;
import it.polimi.ingsw.network.common.ExSocket;

public class ClientSideServerListener {

    private ExSocket socket;
    private boolean done;

    public ClientSideServerListener(ExSocket socket) {
        this.socket = socket;
        this.done = false;
    }

    public void run() {

        this.done = false;

        while (true) {
            if(done) break;

            NetworkPacket np = socket.receive();
            switch (np.getPacketType()){
                case SYSTEM:
                    handleSystemMessage(np);
                    break;
                case DEBUG:
                    handleDebugMessage(np);
                    break;
                case MESSAGE:
                    // TODO Add message handle
                    break;
                case UPDATE:
                    // TODO Add Update handle
                    break;
            }
        }
    }

    public void handleSystemMessage(NetworkPacket packet){

        String serverMessage = packet.getPayload();
        String[] messageFields = serverMessage.split(" ", 2);
        if (serverMessage == null || ConnectionMessage.QUIT.check(messageFields[0])) {
            done = true;
            return;
        }
    }

    private void handleDebugMessage(NetworkPacket packet){
        String clientMessage = packet.getPayload();
        System.out.println(clientMessage);
    }
}


