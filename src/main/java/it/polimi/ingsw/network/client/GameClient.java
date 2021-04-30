package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.common.ExSocket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GameClient {

    private String hostName;
    private int hostPortNumber;

    public GameClient(String hostName, int hostPortNumber){
        this.hostName = hostName;
        this.hostPortNumber = hostPortNumber;
    }

    public void execute(){
        try {
            ExSocket socket = new ExSocket(new Socket(hostName, hostPortNumber));
            new Thread(new ClientSideListener(socket)).start();

            Scanner stdin = new Scanner(System.in);

            String clientMessage;
            while(true){

                if (socket.getSocket().isClosed())
                    break;

                clientMessage = stdin.nextLine();
                socket.send(clientMessage);

            }


        } catch (UnknownHostException ex){
            System.out.println("UnknownHostException while connecting.");
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    

}
