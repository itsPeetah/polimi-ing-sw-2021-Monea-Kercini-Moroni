package ingsw.pietro.test.network.client;

import ingsw.pietro.test.network.common.ExSocket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) throws IOException{

        String hostName = "localhost";
        int portNumber = 42069;

        try {
            ExSocket socket = new ExSocket(new Socket(hostName, portNumber));
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
