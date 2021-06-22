package it.polimi.ingsw.network.client.protocols;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.network.client.persistence.ReconnectionInfo;
import it.polimi.ingsw.network.common.SystemMessage;
import it.polimi.ingsw.network.common.ExSocket;

/**
 * Class that handles the Connection Setup Protocol (CSP)
 */
public class ConnectionSetupProtocol {

    private ExSocket socket;
    private String serverMessage;

    /**
     * Class constructor.
     * @param socket Client (ex)socket.
     */
    public  ConnectionSetupProtocol(ExSocket socket){
        this.socket = socket;
    }

    public String getUserId() {

        // Check if an id from a previous connection has been stored.
        boolean hasOldId = ReconnectionInfo.existsID();

        // Receive WELCOME
        serverMessage = socket.receiveSystemMessage();

        // If an older ID is available, communicate it to the server.
        String helloMsg = hasOldId ? SystemMessage.HELLO.addBody(ReconnectionInfo.loadID()) : SystemMessage.HELLO.getCode();

        // Send HELLO
        socket.sendSystemMessage(helloMsg);

        // Receive ID
        String userId;
        serverMessage = socket.receiveSystemMessage();

        String[] messageFields = serverMessage.split(" ", 2);

        if(messageFields.length < 2 || !SystemMessage.ASSIGNID.check(messageFields[0]))
            return null;
        else {
            userId = messageFields[1];
            socket.sendSystemMessage(SystemMessage.OK.addBody(userId));
        }

        // Receive ready
        serverMessage = socket.receiveSystemMessage();
        messageFields = serverMessage.split(" ", 2);

        if(!SystemMessage.READY.check(messageFields[0]))
            return null;
        else {
            GameApplication.getInstance().setUserId(userId);
            return userId;
        }
    }
}
