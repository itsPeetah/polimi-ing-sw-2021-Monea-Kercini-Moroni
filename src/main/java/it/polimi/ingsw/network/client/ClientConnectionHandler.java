package it.polimi.ingsw.network.client;

import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationIOHandler;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.network.client.protocols.ClientSideServerListener;
import it.polimi.ingsw.network.client.protocols.ConnectionSetupProtocol;
import it.polimi.ingsw.network.common.ExSocket;
import it.polimi.ingsw.network.common.SystemMessage;

/**
 * Runnable class to handle the client side connection.
 */
public class ClientConnectionHandler implements Runnable {

    private final GameClient client;

    public ClientConnectionHandler(GameClient client) {
        this.client = client;
    }

    @Override
    public void run() {

        String userId = new ConnectionSetupProtocol(client.getSocket()).getUserId();
        // If the returned id is not NULL then the connection has been accepted.
        if(userId != null) {
            ClientSideServerListener listener = new ClientSideServerListener(client.getSocket());
            listener.run();
        }
        // If the listener.run() method returns then the connection is over.
        closeConnection();
    }

    private void closeConnection(){
        GameApplication.getInstance().out(ANSIColor.YELLOW + "Connection with the server closed." + ANSIColor.RESET);
        client.terminateConnection();
        // Go back to the initial state
        GameApplication.getInstance().setApplicationState(GameApplicationState.STARTED);
        GameApplicationIOHandler.getInstance().notifySystemMessage(SystemMessage.QUIT, null);
    }
}
