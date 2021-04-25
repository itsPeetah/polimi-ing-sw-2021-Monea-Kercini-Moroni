package it.polimi.ingsw.net.server;

import it.polimi.ingsw.model.game.util.GameCustomizationSettings;
import it.polimi.ingsw.net.server.threads.NewConnectionHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {

    // Class singleton instance
    private static GameServer instance;

    private final String hostName;  // Server host-name
    private final int portNumber;   // Server port-number
    private ServerSocket serverSocket;  // Server socket

    private Object clientDataLock;
    private HashMap<String, ServerSideClient> connectedClients;

    private Object gameRoomsLock;
    private HashMap<String, GameRoom> gameRooms;

    /**
     * Class constructor.
     * @param hostName Server address.
     * @param portNumber Server port.
     */
    public GameServer(String hostName, int portNumber){
        this.hostName = hostName;
        this.portNumber = portNumber;

        this.connectedClients = new HashMap<String, ServerSideClient>();
        this.clientDataLock = new Object();
    }

    /**
     * Mark the current instance as the singleton instance for the class.
     * @return self.
     */
    public GameServer setAsSingletonInstance(){
        instance = this;
        return this;
    }

    /**
     * Singleton instance getter.
     */
    public static GameServer getInstance() {
        return instance;
    }

    /**
     * Start the server.
     */
    public void startServer(){

        try{
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException ex){
            // Could not open the server socket (port not available)
            ex.printStackTrace();
            return;
        }

        // Move accepting connections to a different thread.
        new Thread(() -> acceptIncomingConnections()).start();
    }

    /**
     * Accept incoming connections.
     */
    private void acceptIncomingConnections(){

        // Handle connections with custom threads
        ExecutorService connectionHandlers = Executors.newCachedThreadPool();

        while(true){
            try {
                // Accept new connection and handle it
                Socket socket = serverSocket.accept();
                connectionHandlers.submit(new NewConnectionHandler(socket));
            } catch (IOException ex){
                ex.printStackTrace();
                break;
            }
        }
        // No need to keep this open anymore.
        connectionHandlers.shutdown();
    }

    public String getNewClientId(){
        String newId;
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        synchronized (clientDataLock) {
            do {
                newId = random.ints(leftLimit, rightLimit + 1)
                        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                        .limit(targetStringLength)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();
            } while (connectedClients.containsKey(newId));
        }
        return newId;
    }

    public void registerClient(String id, ServerSideClient client){
        synchronized (clientDataLock){
            connectedClients.put(id, client);
        }
    }

    public GameRoom createRoom(String id, GameCustomizationSettings gcs){
        GameRoom room = new GameRoom(gcs);
        synchronized (gameRoomsLock){
            gameRooms.put(id, room);
        }
        return room;
    }

}
