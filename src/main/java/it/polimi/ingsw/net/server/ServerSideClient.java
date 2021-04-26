package it.polimi.ingsw.net.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Class representing a remote client on the server.
 */
public class ServerSideClient {

    private final String id;    // The client's id on the server

    private final Socket socket;    // the client's socket
    private final Scanner in;   // reads from the socket's input stream
    private final PrintWriter out;  // writes to the socket's output stream

    private boolean inGame; // is the client currently in a game?
    private GameRoom currentRoom;   // game room the client is currently playing in

    /**
     * Class constructor.
     * @param id remote client's id on the server
     * @param socket remote client's socket
     * @param in socket input stream reader
     * @param out socket output stream writer
     */
    public ServerSideClient(String id, Socket socket, Scanner in, PrintWriter out) {
        this.id = id;
        this.socket = socket;
        this.in = in;
        this.out = out;

        this.inGame = false;
        this.currentRoom = null;
    }

    /**
     * Client id getter.
     */
    public String getId() {
        return id;
    }

    /**
     * Receive the next incoming message.
     */
    public String receive() {
        return in.nextLine();
    }

    /**
     * Send a message to the client.
     */
    public void send(String message) {
        out.println(message);
        out.flush();
    }

    /**
     * Terminate the client.
     */
    public void terminate(){
        in.close();
        out.close();
        try {
            socket.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
