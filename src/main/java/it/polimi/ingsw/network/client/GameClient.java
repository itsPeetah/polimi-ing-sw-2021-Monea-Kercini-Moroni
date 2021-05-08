package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.client.protocols.ClientSideServerListener;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.NetworkPacketType;
import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;
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
            new Thread(new ClientConnectionHandler(socket)).start();

            // TODO FIX THIS MESS
            // TODO CLIENT DOES NOT HAVE TO HANDLE INPUT

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

                    if(clientMessage.charAt(0) == '/'){
                        socket.send(new NetworkPacket(NetworkPacketType.DEBUG, clientMessage));
                    } else {
                        socket.sendSystemMessage(clientMessage);
                    }
                }
            }


        } catch (UnknownHostException ex){
            System.out.println("UnknownHostException while connecting.");
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }




}
