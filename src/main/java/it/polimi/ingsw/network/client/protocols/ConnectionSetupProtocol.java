package it.polimi.ingsw.network.client.protocols;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;
import it.polimi.ingsw.network.common.ExSocket;

public class ConnectionSetupProtocol {

    private ExSocket socket;
    private String serverMessage;

    public  ConnectionSetupProtocol(ExSocket socket){
        this.socket = socket;
    }

    public String getUserId() {

        // Receive WELCOME
        serverMessage = socket.receiveSystemMessage();
        /*System.out.println("[SERVER] " + serverMessage);*/

        // Send HELLO
        socket.sendSystemMessage("HELLO");

        // Receive ID
        String userId;
        serverMessage = socket.receiveSystemMessage();
        /*System.out.println("[SERVER] " + serverMessage);*/

        String[] messageFields = serverMessage.split(" ", 2);

        if(messageFields.length < 2 || !ConnectionMessage.ASSIGNID.check(messageFields[0]))
            return null;
        else {
            userId = messageFields[1];
            socket.sendSystemMessage(ConnectionMessage.OK.addBody(userId));
        }

        // Receive ready
        serverMessage = socket.receiveSystemMessage();
        /*System.out.println("[SERVER] " + serverMessage);*/
        messageFields = serverMessage.split(" ", 2);

        if(!ConnectionMessage.READY.check(messageFields[0]))
            return null;
        else {
            GameApplication.getInstance().setUserId(userId);
            return userId;
        }
    }
}
