package it.polimi.ingsw.network.server.protocols;

import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;
import it.polimi.ingsw.network.common.ExSocket;
import it.polimi.ingsw.network.server.GameServer;

public class ConnectionSetupProtocol {

    private ExSocket socket;
    private String[] clientMessageFields;

    public ConnectionSetupProtocol(ExSocket socket) {
        this.socket = socket;
    }

    public String getUserId() {

        // New client has connected
        System.out.println("New client connecting: " + socket.getSocket().getInetAddress().getHostAddress());

        // WELCOME
        socket.send(ConnectionMessage.welcomeMessage);

        // Expect HELLO
        clientMessageFields = socket.receiveFields();
        if (!ConnectionMessage.HELLO.check(clientMessageFields[0])) {
            System.out.println("Client did not reply to welcome message");
            socket.send(ConnectionMessage.unexpectedReplyError);
            return null;
        }

        // Generate id for client
        String id = GameServer.getInstance().getUserTable().generateId();
        socket.send(ConnectionMessage.ASSIGNID.addBody(id));

        // EXPECT OK <id>
        clientMessageFields = socket.receiveFields();
        if(clientMessageFields.length < 2 || !ConnectionMessage.OK.check(id, clientMessageFields[0] + " " + clientMessageFields[1])){
            socket.send(ConnectionMessage.unexpectedReplyError);
            System.out.println("Client did not confirm the correct id");
            return null;
        }

        // Connection is ready
        socket.send(ConnectionMessage.connectionReadyMessage);

        return id;
    }
}
