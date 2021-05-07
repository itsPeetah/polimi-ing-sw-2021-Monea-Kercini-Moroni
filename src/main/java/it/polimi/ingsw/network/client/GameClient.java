package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.common.messages.ConnectionMessage;
import it.polimi.ingsw.network.common.ExSocket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GameClient {

    private String hostName;
    private int hostPortNumber;

    private ExSocket socket;

    private Object lock;
    private boolean isReady;

    public GameClient(String hostName, int hostPortNumber){
        this.hostName = hostName;
        this.hostPortNumber = hostPortNumber;
        this.lock = new Object();
        this.isReady = true;
    }

    public void execute(){
        try {
            socket = new ExSocket(new Socket(hostName, hostPortNumber));
            new Thread(new ClientSideServerListener(socket)).start();

            Scanner stdin = new Scanner(System.in);

            String clientMessage;
            while(true){

                if (socket.getSocket().isClosed())
                    break;

                if(isReady) {
                    clientMessage = stdin.nextLine();
                    if(ConnectionMessage.QUIT.check(clientMessage)) {
                        isReady = false;
                    }
                    socket.send(clientMessage);
                }
            }


        } catch (UnknownHostException ex){
            System.out.println("UnknownHostException while connecting.");
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }


}
