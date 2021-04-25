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

        String clientMessage;
        while(true){

            clientMessage = ssc.receive();


            switch (clientMessage){
                default:
                    break;
            }






        }

    }
}
