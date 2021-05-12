package it.polimi.ingsw.application.common;

import it.polimi.ingsw.controller.view.game.GameController;
import it.polimi.ingsw.controller.view.game.handlers.GameControllerIOHandler;
import it.polimi.ingsw.network.client.GameClient;
import it.polimi.ingsw.network.common.NetworkPacket;

public class GameApplication {

    // Output mode (CLI/GUI)
    private static GameApplicationMode outputMode = GameApplicationMode.CLI;
    public static void setOutputMode(GameApplicationMode mode) { outputMode = mode; }

    // Singleton
    private static GameApplication instance;
    public static GameApplication getInstance() {
        if(instance == null) instance = new GameApplication();
        return instance;
    }

    // State
    private GameApplicationState applicationState;
    private String userId, userNickname, roomName;
    private boolean isRunning;

    // Components
    private GameClient networkClient;
    private GameController gameController;

    // Remove?
    /*protected GameApplicationIOHandler ioHandler;*/

    // Concurrency
    private Object lock;


    /**
     * Class constructor
     */
    public GameApplication(){
        this.isRunning = false;
        this.applicationState = GameApplicationState.STARTUP;
        this.networkClient = null;
        this.gameController = null;
        this.lock = new Object();

        this.userId = null;
        this.userNickname = null;
        this.roomName = null;

        instance = this;
    }

    // Application state

    /**
     * Application state getter.
     */
    public GameApplicationState getApplicationState(){
        synchronized (lock){
            return applicationState;
        }
    }

    /**
     * Application state setter.
     */
    public void setApplicationState(GameApplicationState state){
        synchronized (lock){
            this.applicationState = state;
        }
    }

    /**
     * Is running getter.
     */
    public boolean isRunning(){ return isRunning; }

    /**
     * Has the networking been initialized?
     */
    public boolean isOnNetwork() {return networkClient != null; }

    /**
     * Has the game been set up?
     */
    public boolean gameExists(){return gameController != null; }

    /**
     * Game controller IO Handler getter.
     * @throws NullPointerException if the game has not been initialized.
     */
    public GameControllerIOHandler getGameControllerIO() throws NullPointerException{
        if(gameController == null) throw new NullPointerException();
        return gameController.getGameControllerIOHandler();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    // Output
    public void out(String output){

        if(outputMode == GameApplicationMode.CLI){
            // TODO move to its own class
            System.out.println(output);
        } else {
            // TODO Add gui class to do this
        }

    }


    // Networking stuff

    /**
     * Start the network client
     * @param hostName Server address.
     * @param portNumber Server port number.
     * @throws GameApplicationException if trying
     */
    public void connect(String hostName, int portNumber){
        synchronized (lock) {
            if (isOnNetwork())
                return;

            networkClient = new GameClient(hostName, portNumber);
            if(!networkClient.start())
                networkClient = null;
            else
                isRunning = true;
        }
    }

    public void sendNetworkPacket(NetworkPacket packet) {
        if(!isOnNetwork()) return;
        networkClient.send(packet);
    }


    // TODO Setup game (SP/MP)
    // TODO Others


}
