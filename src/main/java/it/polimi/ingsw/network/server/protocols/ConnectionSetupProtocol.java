package it.polimi.ingsw.network.server.protocols;

import it.polimi.ingsw.network.common.ExSocket;
import it.polimi.ingsw.network.server.GameServer;
import it.polimi.ingsw.network.server.components.RemoteUser;

public class ConnectionSetupProtocol implements Runnable {

    private ExSocket socket;

    public ConnectionSetupProtocol(ExSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("New client connecting: " + socket.getSocket().getInetAddress().getHostAddress());

        String[] clientMessageFields;

        // WELCOME PROTOCOL
        socket.send("WELCOME Welcome to the server!");
        clientMessageFields = socket.receiveFields();
        // Close connection if client replies unexpectedly.
        if (!clientMessageFields[0].equals("HELLO")) {
            socket.send("ERR Connection refused: unexpected reply from client.");
            socket.close();
        }
        // Assign id to client
        String id = GameServer.getInstance().getUserTable().generateId();
        socket.send("ID " + id);
        clientMessageFields = socket.receiveFields();
        // Refuse connection if client replies unexpectedly.
        if(clientMessageFields.length < 2 || !clientMessageFields[0].equals("OK") || !clientMessageFields[1].equals(id)){
            socket.send("ERR Connection refused: unexpected reply from client.");
            socket.close();
        }
        // Successfully connected with the user.
        RemoteUser user = new RemoteUser(id, socket);
        GameServer.getInstance().getUserTable().add(user);
        socket.send("READY You are now connected to the server!");

        // Switch to ROOM JOINING PROTOCOL
        new Thread(new RoomJoiningProtocol(user)).start();
    }
}
