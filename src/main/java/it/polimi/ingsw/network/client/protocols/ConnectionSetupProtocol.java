package it.polimi.ingsw.network.client.protocols;

import it.polimi.ingsw.network.client.ClientSideServerListener;
import it.polimi.ingsw.network.common.ConnectionMessage;
import it.polimi.ingsw.network.common.ExSocket;

public class ConnectionSetupProtocol implements Runnable{

    private ExSocket socket;
    public  ConnectionSetupProtocol(ExSocket socket){
        this.socket = socket;
    }


    @Override
    public void run() {

        System.out.println("[SERVER] " + socket.receive());
        socket.send("HELLO");
        String message = socket.receive();
        System.out.println("[SERVER] " + message);
        String[] messageFields = message.split("\\s+");

        if(messageFields.length < 2 || !ConnectionMessage.ASSIGNID.check(messageFields[0])) {
            socket.close();
        } else {
            socket.send(ConnectionMessage.OK.addBody(messageFields[1]));
            message = socket.receive();
            System.out.println("[SERVER] " + message);
            messageFields = message.split("\\s+");
            if(!ConnectionMessage.READY.check(messageFields[0])){
                socket.close();
            } else {
                new Thread(new ClientSideServerListener(socket)).start();
            }
        }
    }
}
