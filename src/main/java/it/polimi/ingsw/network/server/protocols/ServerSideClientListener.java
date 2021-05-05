package it.polimi.ingsw.network.server.protocols;

import it.polimi.ingsw.network.server.components.RemoteUser;

public class ServerSideClientListener implements Runnable {

    private RemoteUser user;

    public ServerSideClientListener(RemoteUser user){
        this.user = user;
    }

    @Override
    public void run() {

        String clientMessage;
        while (true){

            clientMessage = user.receiveMessage();
            user.sendMessage("[SERVER] Did you say \"" + clientMessage + "\"?");

        }
    }
}
