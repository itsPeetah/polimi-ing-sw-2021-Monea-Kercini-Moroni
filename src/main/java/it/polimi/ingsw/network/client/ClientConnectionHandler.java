package it.polimi.ingsw.network.client;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.network.client.protocols.ClientSideServerListener;
import it.polimi.ingsw.network.client.protocols.ConnectionSetupProtocol;
import it.polimi.ingsw.network.common.ExSocket;

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

        GameApplication.getInstance().setApplicationState(GameApplicationState.STARTED);

        ClientSideServerListener listener = new ClientSideServerListener(client.getSocket());
        listener.run();

        closeConnection();

    }

    private void closeConnection(){
        GameApplication.getInstance().out("Connection with the server closed.");
        client.stop();
    }
}
