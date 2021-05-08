package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.client.protocols.ClientSideServerListener;
import it.polimi.ingsw.network.client.protocols.ConnectionSetupProtocol;
import it.polimi.ingsw.network.common.ExSocket;

public class ClientConnectionHandler implements Runnable {

    private ExSocket socket;
    private String serverMessage;
    private String[] serverMessageFields;

    public ClientConnectionHandler(ExSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        String userId = new ConnectionSetupProtocol(socket).getUserId();

        ClientSideServerListener listener = new ClientSideServerListener(socket);
        listener.run();

        closeConnection();

    }

    private void closeConnection(){
        System.out.println("Connection with the server closed.");
        socket.close();
    }
}
