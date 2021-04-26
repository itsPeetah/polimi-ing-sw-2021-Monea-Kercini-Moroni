package it.polimi.ingsw.net.client;

import it.polimi.ingsw.model.game.Game;

public class ClientSideServerListener implements Runnable{

    private GameClient client;
    public  ClientSideServerListener(){
        client = GameClient.getInstance();
    }
    public ClientSideServerListener(GameClient client){
        this.client = client;
    }

    @Override
    public void run() {

        String serverMessage;
        while((serverMessage = client.receive())!=null){

            // Todo process messages

        }
    }
}
