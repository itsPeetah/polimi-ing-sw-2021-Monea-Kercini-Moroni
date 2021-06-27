package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.ExSocket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Class that handles the network client.
 */
public class GameClient {

    private String hostName;
    private int hostPortNumber;

    private ExSocket socket;

    private Object lock;
    private boolean running;

    /**
     * Class constructor.
     * @param hostName Server address or host name.
     * @param hostPortNumber Server port number.
     */
    public GameClient(String hostName, int hostPortNumber){
        this.hostName = hostName;
        this.hostPortNumber = hostPortNumber;
        this.lock = new Object();
        this.running = false;
    }

    public ExSocket getSocket(){
        return socket;
    }

    public boolean isRunning(){
        synchronized (lock) {
            return running;
        }
    }

    public boolean isConnected(){
        return !socket.getSocket().isClosed() && socket.getSocket().isConnected();
    }

    /**
     * Start the connection with the server.
     * @return True if the client is already connected/has succeeded, false otherwise.
     */
    public boolean start(){

        if(isRunning()) return true;

        boolean couldStart;
        try {
            socket = new ExSocket(new Socket(hostName, hostPortNumber));
            new Thread(new ClientConnectionHandler(this)).start();
            couldStart = true;
        } catch (UnknownHostException ex){
            System.out.println("UnknownHostException while connecting.");
            couldStart = false;
        } catch (IOException ex){
            System.out.println("IOException while connecting.");
            couldStart = false;
        } catch (SecurityException ex){
            System.out.println("SecurityException while connecting.");
            couldStart = false;
        } catch (IllegalArgumentException ex){
            System.out.println("IllegalArgumentException while connecting.");
            couldStart = false;
        }
        if(couldStart) setActive(true);
        return couldStart;
    }

    /**
     * Terminate the connection with the server.
     */
    public void terminateConnection() {
        socket.close();
        setActive(false);
    }

    /**
     * Mark the server as running or not running.
     */
    private void setActive(boolean state){
        synchronized (lock) {
            running = state;
        }
    }

    /**
     * Send a NP over the network.
     */
    public void send(NetworkPacket np){
        socket.send(np);
    }

    /**
     * Receive a NP from the server.
     */
    public NetworkPacket recv (){
        return socket.receive();
    }


}
