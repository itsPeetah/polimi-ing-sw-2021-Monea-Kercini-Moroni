package it.polimi.ingsw.network.client.protocols;

import it.polimi.ingsw.application.common.GameApplicationIOHandler;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.ExSocket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class that handles the reception of messages from the server.
 */
public class ClientSideServerListener {

    private final ExSocket socket;
    private boolean done;

    /**
     * Class constructor.
     * @param socket Client (ex)socket.
     */
    public ClientSideServerListener(ExSocket socket) {
        this.socket = socket;
        this.done = false;
    }

    /**
     * Start the reception of messages from the server.
     */
    public void run() {

        this.done = false;

        // Allocate thread pool for the execution of certain operations
        ExecutorService executorService = Executors.newCachedThreadPool();

        while (!done) {

            NetworkPacket np = socket.receive();

            // If the received message is NULL then the connection has been interrupted
            if(np == null) break;

            // Handle the NP according to its type
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

    /**
     * Handle System Message NPs upon reception
     * @param packet
     */
    public void handleSystemMessage(NetworkPacket packet){
        // Delegate the processing of NPs to the GAIOHandler
        int returnCode = GameApplicationIOHandler.getInstance().handleSystemMessage(packet);
        // Quit message returns -1
        if(returnCode < 0) done = true;
    }

    /**
     * Handle Debug NPs upon reception
     * @param packet
     */
    private void handleDebugMessage(NetworkPacket packet){
        GameApplicationIOHandler.getInstance().handleDebugMessage(packet);
    }
}



