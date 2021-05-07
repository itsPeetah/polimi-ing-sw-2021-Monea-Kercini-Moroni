package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.client.protocols.ConnectionSetupProtocol;
import it.polimi.ingsw.network.common.ConnectionMessage;
import it.polimi.ingsw.network.common.ExSocket;

public class ClientSideServerListener implements Runnable {

    private ExSocket socket;
    private String serverMessage;
    private String[] serverMessageFields;

    public ClientSideServerListener(ExSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        String userId = new ConnectionSetupProtocol(socket).getUserId();
        /*if(userId == null){
            closeConnection();
            return;
        }*/

        while (true) {
            serverMessage = socket.receive();
            serverMessageFields = serverMessage.split("\\s+");
            if (serverMessage == null || ConnectionMessage.QUIT.check(serverMessageFields[0])) {
                closeConnection();
                return;
            }

            System.out.println(serverMessage);
        }
    }

    private void closeConnection(){
        System.out.println("Received quit instruction.");
        socket.close();
    }
}


