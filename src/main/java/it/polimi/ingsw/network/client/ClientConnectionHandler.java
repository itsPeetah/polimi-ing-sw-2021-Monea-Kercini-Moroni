package it.polimi.ingsw.network.client;

import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationIOHandler;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.network.client.protocols.ClientSideServerListener;
import it.polimi.ingsw.network.client.protocols.ConnectionSetupProtocol;
import it.polimi.ingsw.network.common.ExSocket;
import it.polimi.ingsw.network.common.SystemMessage;

public class ClientConnectionHandler implements Runnable {

    private GameClient client;
    private String serverMessage;
    private String[] serverMessageFields;

    public ClientConnectionHandler(GameClient client) {
        this.client = client;
    }

    @Override
    public void run() {

        String userId = new ConnectionSetupProtocol(client.getSocket()).getUserId();
        ClientSideServerListener listener = new ClientSideServerListener(client.getSocket());
        listener.run();
        closeConnection();
    }

    private void closeConnection(){
        GameApplication.getInstance().out(ANSIColor.YELLOW + "Connection with the server closed." + ANSIColor.RESET);
        client.stop();
        GameApplication.getInstance().setApplicationState(GameApplicationState.STARTED);
        GameApplicationIOHandler.getInstance().notifySystemMessage(SystemMessage.QUIT, null);
    }
}
