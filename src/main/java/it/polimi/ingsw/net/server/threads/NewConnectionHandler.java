package it.polimi.ingsw.net.server.threads;

import it.polimi.ingsw.net.messages.ConnectionSetupMessage;
import it.polimi.ingsw.net.server.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Runnable class to manage the setup of a new connection.
 */
public class NewConnectionHandler implements Runnable {

    private Socket socket;

    public NewConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        Scanner in;
        PrintWriter out;

        // Try to open the socket.
        try {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
            // Todo close socket
            return;
        }

        // Send welcome message
        sendMessage(ConnectionSetupMessage.WELCOME.getMessageCode(), out);

        // Expect "hello" response
        if(!expectClientMessage(ConnectionSetupMessage.HELLO.getMessageCode(), in)){
            sendMessage(ConnectionSetupMessage.ERR.composeMessage("Unexpected response from client."), out);
            // Todo close socket
            return;
        }

        // Communicate id to client
        String newClientId = GameServer.getInstance().getNewClientId();
        sendMessage(ConnectionSetupMessage.ID.composeMessage(newClientId), out);

        // Wait confirmation
        if(!expectClientMessage(ConnectionSetupMessage.OK.composeMessage(newClientId),in)){
            sendMessage(ConnectionSetupMessage.ERR.composeMessage("Unexpected response from client."), out);
            // Todo close socket
            return;
        }

        ServerSideClient ssc = new ServerSideClient(newClientId, socket, in, out);
        GameServer.getInstance().registerClient(newClientId, ssc);

        new Thread(new LobbyConnectionHandler(ssc)).start();
    }

    /**
     * Send a message to the client via its output stream.
     */
    private void sendMessage(String message, PrintWriter out){
        out.println(message);
        out.flush();
    }

    /**
     * Receive a message from the client's input stream and compare it with the expected one.
     */
    private boolean expectClientMessage(String message, Scanner in){
        String clientResponse = in.nextLine();
        return clientResponse.equals(message);
    }
}
