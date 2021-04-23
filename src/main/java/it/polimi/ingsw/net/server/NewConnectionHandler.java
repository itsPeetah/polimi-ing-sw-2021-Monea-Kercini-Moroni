package it.polimi.ingsw.net.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class NewConnectionHandler implements Runnable {

    private Socket socket;

    public NewConnectionHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {

        Scanner in; PrintWriter out;
        try{
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
        }catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        // Setup connection

        out.println("OK");

    }
}
