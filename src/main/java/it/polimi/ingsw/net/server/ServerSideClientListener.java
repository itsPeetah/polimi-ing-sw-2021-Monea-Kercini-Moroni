package it.polimi.ingsw.net.server;

/**
 * Runnable class to listen for messages coming from a remote connection.
 */
public class ServerSideClientListener implements Runnable{

    ServerSideClient client;

    public  ServerSideClientListener(ServerSideClient client){
        this.client = client;
    }

    @Override
    public void run() {


        String clientMessage;
        while(true){
            clientMessage = client.receive();

            // process message
            System.out.println(String.format("Received from %s: %s", client.getId(), clientMessage));

            if(clientMessage.equals("stop") || clientMessage == null) {
                break;
            }
        }

        client.terminate();

    }
}
