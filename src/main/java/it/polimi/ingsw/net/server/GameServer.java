package it.polimi.ingsw.net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {

    private final String hostName;
    private final int portNumber;
    private ServerSocket serverSocket;

    private HashMap<String, ServerSideClient> activeClients;

    public GameServer(String hostName, int portNumber){
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    public void startServer(){

        try{
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException ex){
            ex.printStackTrace();
            return;
        }

        new Thread(() -> acceptIncomingConnections()).start();
    }

    public void acceptIncomingConnections(){

        ExecutorService connectionHandlers = Executors.newCachedThreadPool();

        while(true){
            try {
                Socket socket = serverSocket.accept();
                connectionHandlers.submit(new NewConnectionHandler(socket));
            } catch (IOException ex){
                ex.printStackTrace();
                break;
            }
        }

        connectionHandlers.shutdown();
    }

}
