package it.polimi.ingsw.network.client.protocols;

import it.polimi.ingsw.application.common.GameApplicationIOHandler;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.ExSocket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientSideServerListener {

    private final ExSocket socket;
    private boolean done;

    public ClientSideServerListener(ExSocket socket) {
        this.socket = socket;
        this.done = false;
    }

    public void run() {

        this.done = false;

        ExecutorService executorService = Executors.newCachedThreadPool();

        while (!done) {

            NetworkPacket np = socket.receive();

            if(np == null){
                break;
            }

            switch (np.getPacketType()) {
                case SYSTEM:
                    handleSystemMessage(np);
                    break;
                case DEBUG:
                    handleDebugMessage(np);
                    break;
                case MESSAGE:
                    GameApplicationIOHandler.getInstance().notifyMessage(np);
                    break;
                case UPDATE:
                    GameApplicationIOHandler.getInstance().notifyUpdate(np);
                    break;
                case SOCIAL:
                    executorService.submit(() -> GameApplicationIOHandler.getInstance().handleSocialMessage(np));
                    break;
            }
        }

        executorService.shutdown();
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



