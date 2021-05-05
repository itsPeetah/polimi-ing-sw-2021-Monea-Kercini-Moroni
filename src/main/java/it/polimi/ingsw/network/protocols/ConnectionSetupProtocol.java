package it.polimi.ingsw.network.protocols;

import it.polimi.ingsw.network.common.ExSocket;
import it.polimi.ingsw.network.server.GameServer;
import it.polimi.ingsw.network.server.components.RemoteUser;
import it.polimi.ingsw.network.server.components.GameRoom;
import it.polimi.ingsw.network.server.components.RoomTable;
import it.polimi.ingsw.network.server.components.UserTable;

import java.io.IOException;

public class ConnectionSetupProtocol implements Runnable {

    private ExSocket socket;

    public ConnectionSetupProtocol(ExSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("New client connectinbg: " + socket.getSocket().getInetAddress().getHostAddress());

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
        socket.send("READY You are now connected to the server!");

        // Switch to ROOM JOINING PROTOCOL
        new Thread(new RoomJoiningProtocol(user)).start();

/*




        while (true) {
            socket.send("Provide nickname (NICK <nickname>)");
            clientMessageFields = socket.receive().split("\\s+");

            if (clientMessageFields.length < 2 || !clientMessageFields[0].equals("NICK")) {
                socket.send("Invalid message, retry.");
            } else {
                id = clientMessageFields[1];
                user = new RemoteUser(id, socket);

                if (UserTable.getInstance().add(user)) {
                    socket.send("Ok, your nickname is now " + id);
                    break;
                } else {
                    socket.send("ID is already taken, retry.");
                }
            }
        }

        String roomID;
        GameRoom room;

        while (true) {
            socket.send("do you want to CREATE <name> a room or JOIN <name> one?");
            clientMessageFields = socket.receive().split("\\s+");

            if (clientMessageFields.length < 2 || (!clientMessageFields[0].equals("CREATE") && !clientMessageFields[0].equals("JOIN"))) {
                socket.send("Invalid message try again");
            } else {

                roomID = clientMessageFields[1];

                if (clientMessageFields[0].equals("CREATE")) {
                    room = new GameRoom(roomID);
                    if (RoomTable.getInstance().add(room)) {
                        socket.send("Ok, you're now in room " + roomID);
                        RoomTable.getInstance().getRoom(roomID).addUser(id, "test");
                        break;
                    } else {
                        socket.send("Room already exists, retry.");
                    }
                } else if (clientMessageFields[0].equals("JOIN")) {
                    if (!RoomTable.getInstance().exists(roomID)) {
                        socket.send("Room doesn't exist, retry.");
                    } else {
                        socket.send("Ok, you're now in room " + roomID);
                        RoomTable.getInstance().getRoom(roomID).addUser(id, "test");
                        break;
                    }
                }
            }
        }

        socket.send("OK, ALL IS OK.");

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            socket.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }*/
    }

}
