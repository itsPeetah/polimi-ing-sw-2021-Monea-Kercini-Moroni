package ingsw.pietro.test.network.client;

import ingsw.pietro.test.network.common.ExSocket;

import java.io.IOException;

public class ClientSideListener implements Runnable {

    ExSocket socket;

    public ClientSideListener(ExSocket socket) {
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

        try {
            socket.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
