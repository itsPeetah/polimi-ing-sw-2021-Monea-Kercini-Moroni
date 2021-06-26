package it.polimi.ingsw.network.server.protocols;

import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.network.common.SystemMessage;
import it.polimi.ingsw.network.common.ExSocket;
import it.polimi.ingsw.network.server.GameServer;

/**
 * Class handling the server side of the Connection Setup Protocol.
 */
public class ConnectionSetupProtocol {

    private ExSocket socket;
    private String[] clientMessageFields;

    /**
     * Class constructor
     * @param socket the remote user's (ex)socket.
     */
    public ConnectionSetupProtocol(ExSocket socket) {
        this.socket = socket;
    }

    /**
     * Actual CSP procedure.
     * @return A connection setup result if validated, NULL if not.
     */
    public ConnectionSetupResult getUserId() {

        // New client has connected
        System.out.println("New client connecting: " + socket.getSocket().getInetAddress().getHostAddress());

        // WELCOME
        socket.sendSystemMessage(SystemMessage.welcomeMessage);

        // Expect HELLO
        clientMessageFields = socket.receiveSystemMessage().split(" ", 2);
        if (!SystemMessage.HELLO.check(clientMessageFields[0])) {
            System.out.println(ANSIColor.RED + "Client did not reply to welcome message" + ANSIColor.RESET);
            socket.sendSystemMessage(SystemMessage.unexpectedReplyError);
            return null;
        }

        // Check if the client has provided an old id
        String id;
        boolean checkCanReconnect;
        if(clientMessageFields.length > 1 && GameServer.getInstance().getUserTable().getUser(clientMessageFields[1]) == null){
            // Set client's id to the old one that has been provided
            id = clientMessageFields[1];
            checkCanReconnect = true;
        } else{
            // Generate id for client
            id = GameServer.getInstance().getUserTable().generateId();
            checkCanReconnect = false;
        }

        socket.sendSystemMessage(SystemMessage.ASSIGNID.addBody(id));

        // EXPECT OK <id>
        clientMessageFields = socket.receiveSystemMessage().split(" ", 2);
        if(clientMessageFields.length < 2 || !SystemMessage.OK.check(id, clientMessageFields[0] + " " + clientMessageFields[1])){
            socket.sendSystemMessage(SystemMessage.unexpectedReplyError);
            System.out.println(ANSIColor.RED + "Client did not confirm the correct id" + ANSIColor.RESET);
            return null;
        }

        // Connection is ready
        socket.sendSystemMessage(SystemMessage.connectionReadyMessage);

        ConnectionSetupResult csr = new ConnectionSetupResult(id, checkCanReconnect);
        return csr;
    }
}
