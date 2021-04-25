package it.polimi.ingsw.net.server.threads;

import it.polimi.ingsw.net.messages.ConnectionSetupMessage;
import it.polimi.ingsw.net.server.ServerSideClient;

public class LobbyConnectionHandler implements Runnable {

    ServerSideClient ssc;

    public LobbyConnectionHandler(ServerSideClient ssc){
        this.ssc = ssc;
    }

    @Override
    public void run() {

        ssc.send(ConnectionSetupMessage.READY.getMessageCode());

        String clientMessage = ssc.receive();
        String[] fields;

        while(true){

            clientMessage = ssc.receive();
            fields = clientMessage.split("\\s+");

            if(fields[0].equals(ConnectionSetupMessage.ROOM_CREATE.getMessageCode())){
                // try to create a new room
                // break;
            } else if (fields[0].equals(ConnectionSetupMessage.ROOM_JOIN.getMessageCode())){
                // try to join a room -> create if non-existent
                // break;
            } else if (fields[0].equals(ConnectionSetupMessage.STOP.getMessageCode())){
                // exit
                // return;
            } else {
                // send error
                // continue;
            }

        }

    }
}
