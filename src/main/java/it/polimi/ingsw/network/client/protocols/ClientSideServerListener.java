package it.polimi.ingsw.network.client.protocols;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationIOHandler;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;
import it.polimi.ingsw.network.common.ExSocket;

public class ClientSideServerListener {

    private final ExSocket socket;
    private boolean done;

    public ClientSideServerListener(ExSocket socket) {
        this.socket = socket;
        this.done = false;
    }

    public void run() {

        this.done = false;

        while (!done) {

            NetworkPacket np = socket.receive();
            switch (np.getPacketType()) {
                case SYSTEM:
                    handleSystemMessage(np);
                    System.out.println("Received SYSTEM");
                    break;
                case DEBUG:
                    handleDebugMessage(np);
                    System.out.println("Received DEBUG");
                    break;
                case MESSAGE:
                    GameApplicationIOHandler.getInstance().notifyMessage(np);
                    System.out.println("Received MESSAGE");
                    break;
                case UPDATE:
                    GameApplicationIOHandler.getInstance().notifyUpdate(np);
                    System.out.println("Received UPDATE");
                    break;
            }
        }
    }

    public void handleSystemMessage(NetworkPacket packet){

        int returnCode = GameApplicationIOHandler.getInstance().handleSystemMessage(packet);
        if(returnCode < 0){
            done = true;
        }
    }

    private void handleDebugMessage(NetworkPacket packet){
        GameApplicationIOHandler.getInstance().handleDebugMessage(packet);
    }
}



