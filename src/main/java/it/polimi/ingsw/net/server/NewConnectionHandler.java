package it.polimi.ingsw.net.server;

import it.polimi.ingsw.net.messages.ConnectionSetupMessage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class NewConnectionHandler implements Runnable {

    private Socket socket;
    private Scanner in;
    private PrintWriter out;

    public NewConnectionHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {


        try {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        try {

            // connection setup
            welcomeClient();

            String roomJoined = null;
            while(roomJoined == null){
                roomJoined = joinClientToRoom();
            }




        } catch (ConnectionSetupException ex){
            String errorMessage = String.format("Something went wrong while connecting (%s).", ex.getMessage());
            sendMessage(ConnectionSetupMessage.ERR.composeMessage(errorMessage));
            ex.printStackTrace();
            return;
        }

        out.println("OK");

        // Register client

        // Start listener

    }

    /**
     * Welcome the newly connected client
     * @throws ConnectionSetupException if the client does not reply as expected.
     */
    private void welcomeClient() throws ConnectionSetupException{
        // Send welcome message
        sendMessage(ConnectionSetupMessage.WELCOME.getMessageCode());
        // Wait for response
        String clientMessage = in.nextLine();
        if (!clientMessage.equals(ConnectionSetupMessage.HELLO.getMessageCode())){
            throw new ConnectionSetupException("Unexpected message from client.");
        }
        // TODO add timeout?
    }

    private String joinClientToRoom() throws ConnectionSetupException{
        // Ask to join or create a room
        sendMessage(ConnectionSetupMessage.ROOM_PROMPT.getMessageCode());
        // Wait for response
        String clientMessage = in.nextLine();
        // Elaborate response
        String roomJoined = null;
        String[] fields = clientMessage.split("\\s+");

        if(fields.length < 2) throw new ConnectionSetupException("Missing arguments to join a room.");

       if(fields[0].equals(ConnectionSetupMessage.ROOM_CREATE.getMessageCode())){
            // TODO Check if room exists
            // TODO if not create room
            // TODO if so send room error
       } else if (fields[0].equals(ConnectionSetupMessage.ROOM_JOIN.getMessageCode())){
            // TODO Check if room exists
            // TODO if not send room error
            // TODO if so ok
       } else {
           throw new ConnectionSetupException("Unexpected message from client.");
       }
        return roomJoined;
    }

    /**
     * Send a message to the client using the socket's output stream.
     */
    private void sendMessage(String msg){
        out.println(msg);
        out.flush();
    }
}
