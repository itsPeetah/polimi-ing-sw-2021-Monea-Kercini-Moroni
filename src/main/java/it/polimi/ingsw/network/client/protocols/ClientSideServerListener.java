package it.polimi.ingsw.network.client.protocols;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationIOHandler;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;
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
            switch (np.getPacketType()) {
                case SYSTEM:
                    executorService.submit(()-> handleSystemMessage(np));
                    break;
                case DEBUG:
                    executorService.submit(() -> handleDebugMessage(np));
                    break;
                case MESSAGE:
                    executorService.submit(() -> GameApplicationIOHandler.getInstance().notifyMessage(np));
                    break;
                case UPDATE:
                    executorService.submit(() -> GameApplicationIOHandler.getInstance().notifyUpdate(np));
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



