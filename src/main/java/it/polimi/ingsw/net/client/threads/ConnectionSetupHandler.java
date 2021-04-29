package it.polimi.ingsw.net.client.threads;

import it.polimi.ingsw.net.client.ClientSideServerListener;
import it.polimi.ingsw.net.client.GameClient;
import it.polimi.ingsw.net.messages.ConnectionSetupMessage;

public class ConnectionSetupHandler implements Runnable{

    private GameClient client;

    public ConnectionSetupHandler(){
        this.client = GameClient.getInstance();
    }

    public ConnectionSetupHandler(GameClient client){
        this.client = client;
    }

    @Override
    public void run() {

        String serverMessage;
        serverMessage = client.receive();
        // todo Check for correct messages?

        client.sendMessage(ConnectionSetupMessage.HELLO.getMessageCode());

        serverMessage = client.receive();
        String[] fields = serverMessage.split("\\s+");
        client.sendMessage(ConnectionSetupMessage.OK.composeMessage(fields[1]));

        while(!client.receive().equals(ConnectionSetupMessage.READY)){}

        System.out.println("Connection established.");

        new Thread(new ClientSideServerListener(client)).start();

    }
}
