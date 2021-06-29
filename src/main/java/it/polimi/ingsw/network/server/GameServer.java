package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.common.ExSocket;
import it.polimi.ingsw.network.server.components.RemoteConnectionHandler;
import it.polimi.ingsw.network.server.components.RoomTable;
import it.polimi.ingsw.network.server.components.UserTable;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;

/**
 * Socket base server class.
 */
public class GameServer {

    private static final int serverBacklog = 50;

    private static GameServer instance;     // singleton instance

    private final String hostName;           // name of the server
    private final int port;                 // port of the server
    private ServerSocket serverSocket;      // server socket object

    private UserTable userTable;            // user table for the server
    private RoomTable roomTable;            // room table for the server

    private Object roomCreationLock;

    /**
     * Class constructor.
     * @param address The server's IP address.
     * @param port The server's port number.
     */
    public GameServer(String address, int port){
        this.hostName = address;
        this.port = port;

        this.userTable = new UserTable();
        this.roomTable = new RoomTable();
    }

    /**
     * Set the current instance as the singleton.
     * @return the instance
     */
    public GameServer setAsInstance(){
        instance = this;
        this.roomTable.setAsInstance();
        this.userTable.setAsInstance();
        return instance;
    }

    /**
     * Singleton instance getter.
     */
    public static GameServer getInstance(){
        return instance;
    }

    /**
     * User table getter.
     */
    public UserTable getUserTable(){return userTable;}

    /**
     * Room table getter.
     */
    public RoomTable getRoomTable(){return roomTable;}

    /**
     * Start the server - main loop
     */
    public void execute(){
        System.out.print("\033[H\033[2J");
        System.out.println("Starting server @ " + hostName + ":" + port);
        // try initializing the server socket
        try {
            serverSocket = new ServerSocket(port, serverBacklog, Inet4Address.getByName(hostName));
        } catch (IOException ex){
            System.out.println(ex.getMessage());
            return;
        }
        System.out.println("--- Server ready ---");

        // Acceptance loop
        while(true){

            try{
                // Pause until new connection
                // Handle new connections in a new thread
                ExSocket socket = new ExSocket(serverSocket.accept());
                new Thread(new RemoteConnectionHandler(socket)).start();
            }catch (IOException ex){
                System.out.println(ex.getMessage());
                break;
            }
        }

        // Close the server socket
        try {
            serverSocket.close();
        } catch (IOException ex){
            System.out.println("IOException in server start.");
        }

    }
}
