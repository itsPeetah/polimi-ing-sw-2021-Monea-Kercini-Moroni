package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.common.ExSocket;

import java.io.IOException;

public class ClientSideServerListener implements Runnable {

    ExSocket socket;

    public ClientSideServerListener(ExSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        String serverMessage;

        while (true) {
            serverMessage = socket.receive();
            if (serverMessage == null || serverMessage.equals("#quit")) {
                System.out.println("Received quit instruction.");
                break;
            } else {
                System.out.println(serverMessage);
            }
        }

        System.out.println("Ending connection.");

        socket.close();
    }
}
