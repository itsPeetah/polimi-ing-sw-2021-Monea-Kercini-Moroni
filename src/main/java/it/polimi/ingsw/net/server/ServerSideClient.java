package it.polimi.ingsw.net.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerSideClient {

    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private String id;
    private GameRoom room;

    public ServerSideClient(String id, Socket socket, Scanner in, PrintWriter out, GameRoom room) {
        this.id = id;
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.room = room;
    }

    public String getId() {
        return id;
    }

    public String receive() {
        return in.nextLine();
    }

    public void send(String message) {
        out.println(message);
        out.flush();
    }

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
