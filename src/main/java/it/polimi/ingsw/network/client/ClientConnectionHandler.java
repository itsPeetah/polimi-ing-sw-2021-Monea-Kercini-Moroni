package it.polimi.ingsw.network.client;

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

        ClientSideServerListener listener = new ClientSideServerListener(client.getSocket());
        listener.run();

        closeConnection();

    }

    private void closeConnection(){
        System.out.println("Connection with the server closed.");
        client.stop();
    }
}
