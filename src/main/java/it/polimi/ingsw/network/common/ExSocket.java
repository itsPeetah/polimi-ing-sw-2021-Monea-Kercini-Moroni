package it.polimi.ingsw.network.common;

import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;

import java.io.IOException;
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

    /**
     * Socket getter.
     */
    public Socket getSocket() {return socket;}
    //public Scanner getIn() {return in;}
    //public PrintWriter getOut() {return out;}

    /**
     * Class constructor.
     * @param socket The socket to handle.
     * @throws IOException if the I/O streams cannot be retrieved.
     */
    public ExSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new Scanner(socket.getInputStream());
        this.out = new PrintWriter(socket.getOutputStream());
    }

    public void send(NetworkPacket packet){
        out.println(packet.toJson());
        out.flush();
    }

    public NetworkPacket receive(){
        String json = in.nextLine();
        return NetworkPacket.fromString(json);
    }

    /**
     * Send a message to the socket's output stream.
     */
    public void sendSystemMessage(String message){
        send(NetworkPacket.buildSystemMessagePacket(message));
        /*out.println(message);
        out.flush();*/
    }

    /**
     * Receive a message from the socket's input stream.
     */
    public String receiveSystemMessage(){
        NetworkPacket np = receive();
        String message = np.getPacketType() == NetworkPacketType.SYSTEM ? np.getPayload() : ConnectionMessage.ERR.getCode();
        return message;
    }

    /**
     * Close the socket and its streams.
     * @throws IOException if any issues occur.
     */
    public void close(){
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

}
