package ingsw.pietro.test.network.protocols;

import ingsw.pietro.test.network.common.*;
import ingsw.pietro.test.network.server.components.GameRoom;
import ingsw.pietro.test.network.server.components.RoomTable;
import ingsw.pietro.test.network.server.components.UserTable;

import java.io.IOException;

public class ConnectionSetupProtocol implements Runnable {

    private ExSocket socket;

    public ConnectionSetupProtocol(ExSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("New client connected: " + socket.getSocket().getInetAddress().getHostAddress());

        String[] clientMessageFields;
        String id;
        RemoteUser user;

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
                        RoomTable.getInstance().getRoom(roomID).addUser(id);
                        break;
                    } else {
                        socket.send("Room already exists, retry.");
                    }
                } else if (clientMessageFields[0].equals("JOIN")) {
                    if (!RoomTable.getInstance().exists(roomID)) {
                        socket.send("Room doesn't exist, retry.");
                    } else {
                        socket.send("Ok, you're now in room " + roomID);
                        RoomTable.getInstance().getRoom(roomID).addUser(id);
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
        }
    }

}
