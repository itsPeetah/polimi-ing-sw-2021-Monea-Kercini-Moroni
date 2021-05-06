package it.polimi.ingsw.network.common;

import kotlin.text.Regex;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

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

    /**
     * Send a message to the socket's output stream.
     */
    public void send(String message){
        out.println(message);
        out.flush();
    }

    /**
     * Receive a message from the socket's input stream.
     */
    public String receive(){
        return  in.nextLine();
    }

    public String[] receiveFields(){
        return receive().split("\\s+");
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
