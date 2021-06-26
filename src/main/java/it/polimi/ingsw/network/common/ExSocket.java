package it.polimi.ingsw.network.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Socket extension class.
 */
public class ExSocket {

    private Socket socket;      // the actual socket
    private Scanner in;         // handles the socket's input stream
    private PrintWriter out;    // handles the socket's output stream

    private BufferedReader binr;

    /**
     * Socket getter.
     */
    public Socket getSocket() {return socket;}

    /**
     * Class constructor.
     * @param socket The socket to handle.
     * @throws IOException if the I/O streams cannot be retrieved.
     */
    public ExSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new Scanner(socket.getInputStream());
        this.out = new PrintWriter(socket.getOutputStream());
        this.binr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Send a NP over the network.
     */
    public void send(NetworkPacket packet){
        out.println(packet.toJson());
        out.flush();
    }

    /**
     * Receive a NP from the server.
     * @return
     */
    public NetworkPacket receive(){
        /*String json = in.nextLine();*/
        try {
            String json = binr.readLine();
            return NetworkPacket.fromString(json);
        } catch (IOException ex){
            return null;
        }
    }

    /**
     * Send a (system) message to the socket's output stream.
     */
    public void sendSystemMessage(String message){
        send(NetworkPacket.buildSystemMessagePacket(message));
        /*out.println(message);
        out.flush();*/
    }

    /**
     * Receive a (system) message from the socket's input stream.
     */
    public String receiveSystemMessage(){
        NetworkPacket np = receive();
        if(np == null) return null;
        String message = np.getPacketType() == NetworkPacketType.SYSTEM ? np.getPayload() : SystemMessage.ERR.getCode();
        return message;
    }

    /**
     * Close the socket and its streams.
     * @throws IOException if any issues occur.
     */
    public void close(){
        try {
            in.close();
            binr.close();
            out.close();
            socket.close();
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

}
