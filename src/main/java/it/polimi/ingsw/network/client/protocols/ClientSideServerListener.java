package it.polimi.ingsw.network.client.protocols;

import it.polimi.ingsw.application.common.GameApplication;
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
                    GameApplication.getInstance().getIoHandler().notifyMessage(np);
                    break;
                case UPDATE:
                    GameApplication.getInstance().getIoHandler().notifyUpdate(np);
                    break;
            }
        }
    }

    public void handleSystemMessage(NetworkPacket packet){

        int returnCode = GameApplication.getInstance().getIoHandler().handleSystemMessage(packet);
        if(returnCode < 0){
            done = true;
            return;
        }
    }

    private void handleDebugMessage(NetworkPacket packet){
        GameApplication.getInstance().getIoHandler().handleDebugMessage(packet);
    }
}



