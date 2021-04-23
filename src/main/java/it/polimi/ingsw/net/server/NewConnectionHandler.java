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

    public NewConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        // Try to open the socket.
        try {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        String roomJoined = null;
        String userNickname = null;

        // Try to setup the connection
        try {

            // connection setup
            welcomeClient();

            // joining room
            while (roomJoined == null) {
                roomJoined = joinClientToRoom();
            }

            // get nickname
            while (userNickname == null) {
                userNickname = getUserNickname();
            }

        } catch (ConnectionSetupException ex) {
            String errorMessage = String.format("Something went wrong while connecting (%s).", ex.getMessage());
            sendMessage(ConnectionSetupMessage.ERR.composeMessage(errorMessage));
            ex.printStackTrace();
            return;
        }

        String clientId = String.format("%s_%s", roomJoined, userNickname);

        // TODO add ssclient to room
        // ServerSideClient client = new ServerSideClient(clientId, socket, in, out);

        sendMessage(ConnectionSetupMessage.OK.composeMessage(clientId));
    }

    /**
     * Welcome the newly connected client
     *
     * @throws ConnectionSetupException if the client does not reply as expected.
     */
    private void welcomeClient() throws ConnectionSetupException {
        // Send welcome message
        sendMessage(ConnectionSetupMessage.WELCOME.getMessageCode());
        // Wait for response
        String clientMessage = in.nextLine();
        if (!clientMessage.equals(ConnectionSetupMessage.HELLO.getMessageCode())) {
            throw new ConnectionSetupException("Unexpected message from client.");
        }
        // TODO add timeout?
    }

    /**
     * Attempt joining or creating a new room.
     *
     * @return Joined (or created) room name.
     * @throws ConnectionSetupException If the client replies unexpectedly.
     */
    private String joinClientToRoom() throws ConnectionSetupException {
        // Ask to join or create a room
        sendMessage(ConnectionSetupMessage.ROOM_PROMPT.getMessageCode());
        // Wait for response
        String clientMessage = in.nextLine();
        // Elaborate response
        String roomJoined = null;
        String[] fields = clientMessage.split("\\s+");

        if (fields.length < 2) throw new ConnectionSetupException("Missing arguments to join or create a room.");

        if (fields[0].equals(ConnectionSetupMessage.ROOM_CREATE.getMessageCode())) {
            // TODO Check for valid arguments (customiz levels)
            // TODO Check if room exists
            // TODO if not create room + send ok
            // TODO if so send room error
        } else if (fields[0].equals(ConnectionSetupMessage.ROOM_JOIN.getMessageCode())) {
            // TODO Check if room exists
            // TODO if not send room error
            // TODO if so ok
        } else {
            throw new ConnectionSetupException("Unexpected message from client.");
        }
        return roomJoined;
    }

    /**
     * Attempt getting the user's nickname.
     * @return The user's nickname.
     * @throws ConnectionSetupException if client replies unexpectedly.
     */
    private String getUserNickname() throws ConnectionSetupException {
        sendMessage(ConnectionSetupMessage.NICK_PROMPT.getMessageCode());

        String clientMessage = in.nextLine();
        String clientNickName = null;
        String[] fields = clientMessage.split("\\s+");

        if (fields.length < 2) throw new ConnectionSetupException("Client did not provide a nickname.");

        // TODO Check if nickname is already in room
        // TODO if so send err
        // TODO if not return name + send ok

        return clientNickName;
    }

    /**
     * Send a message to the client using the socket's output stream.
     */
    private void sendMessage(String msg) {
        out.println(msg);
        out.flush();
    }
}
