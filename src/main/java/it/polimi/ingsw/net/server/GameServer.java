package it.polimi.ingsw.net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {

    // Class singleton instance
    private static GameServer instance;

    private final String hostName;  // Server host-name
    private final int portNumber;   // Server port-number
    private ServerSocket serverSocket;  // Server socket

    private Object clientMapLock;   // Lock for threads to access the activeClients map
    private HashMap<String, ServerSideClient> activeClients;    // map with the active clients

    /**
     * Class constructor.
     * @param hostName Server address.
     * @param portNumber Server port.
     */
    public GameServer(String hostName, int portNumber){
        this.hostName = hostName;
        this.portNumber = portNumber;
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

    /**
     * Check whether a client with such id already exists.
     */
    public boolean idTaken(String id){
        synchronized (clientMapLock){
            return activeClients.containsKey(id);
        }
    }

    /**
     * Register a client using its id.
     */
    public void registerClient(ServerSideClient client){
        synchronized (clientMapLock){
            activeClients.put(client.getId(), client);
        }
    }

    /**
     * Retrieve a client using its id.
     */
    public ServerSideClient getClientById(String id) {

        if(!activeClients.containsKey(id)) return null;

        synchronized (clientMapLock) {
            return activeClients.get(id);
        }
    }

}
