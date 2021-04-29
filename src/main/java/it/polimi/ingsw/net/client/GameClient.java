package it.polimi.ingsw.net.client;

import it.polimi.ingsw.net.client.threads.ConnectionSetupHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {

    private static GameClient instance;
    private String serverHostName;
    private int serverPortNumber;
    private Socket socket;

    private PrintWriter socketOut;
    private Scanner socketIn;

    public GameClient(String serverHostName, int serverPortNumber){
        this.serverHostName = serverHostName;
        this.serverPortNumber = serverPortNumber;
    }

    public GameClient setAsInstance() {instance = this; return this;}
    public static GameClient getInstance(){return instance;}

    public void connect() {
        // Create new socket
        try {
            socket = new Socket(serverHostName, serverPortNumber);
        } catch (IOException ex) {
            System.out.println("Could not connect to the server.");
            return;
        }
        // Get socket I/O streams
        try {
            socketIn = new Scanner(socket.getInputStream());
            socketOut = new PrintWriter(socket.getOutputStream());
        } catch (IOException ex) {
            System.out.println("Could not get I/O streams from socket.");
            return;
        }

        // Setup new connection
        System.out.println("Entering connection setup phase");
        new Thread(new ConnectionSetupHandler(this)).start();
    }

    public void sendMessage(String message){
        socketOut.println(message);
        socketOut.flush();
    }

    public String receive(){
        return socketIn.nextLine();
    }
}
