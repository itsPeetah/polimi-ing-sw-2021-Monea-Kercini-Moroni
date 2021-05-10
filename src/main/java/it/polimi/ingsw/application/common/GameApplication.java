package it.polimi.ingsw.application.common;

import it.polimi.ingsw.application.common.iohandlers.CLIApplicationIOHandler;
import it.polimi.ingsw.application.common.iohandlers.GUIApplicationIOHandler;
import it.polimi.ingsw.controller.view.game.GameController;
import it.polimi.ingsw.controller.view.game.handlers.GameControllerIOHandler;
import it.polimi.ingsw.network.client.GameClient;
import it.polimi.ingsw.network.common.NetworkPacket;

public class GameApplication {

    protected static GameApplication instance;

    protected GameClient networkClient;
    protected GameApplicationState applicationState;
    protected GameApplicationIOHandler ioHandler;
    protected GameController gameController;

    protected boolean gameExists;

    protected Object lock;

    public GameApplication(GameApplicationMode applicationMode){
        this.applicationState = GameApplicationState.STARTUP;
        this.ioHandler = applicationMode == GameApplicationMode.CLI ? new CLIApplicationIOHandler() : new GUIApplicationIOHandler();

        this.networkClient = null;
        this.gameController = null;

        this.lock = new Object();

        instance = this;
    }

    public static GameApplication getInstance() throws NullPointerException{
        if(instance == null) throw new NullPointerException();
        return instance;
    }

    public GameApplicationState getApplicationState(){
        synchronized (lock){
            return applicationState;
        }
    }

    public void setApplicationState(GameApplicationState state){
        synchronized (lock){
            this.applicationState = state;
        }
    }

    public GameControllerIOHandler getGameControllerIO() throws NullPointerException{
        if(gameController == null) throw new NullPointerException();
        return gameController.getGameControllerIOHandler();
    }

    public GameApplicationIOHandler getIoHandler(){
        return ioHandler;
    }

    public boolean isOnNetwork() {return networkClient != null; }
    public boolean gameExists(){return gameController != null; }

    public void connect(String hostName, int portNumber) throws GameApplicationException{
        synchronized (lock) {
            if (isOnNetwork())
                throw new GameApplicationException("The application is already connected.");

            networkClient = new GameClient(hostName, portNumber);
            if(!networkClient.start())
                networkClient = null;
        }
    }

    public void sendNetworkPacket(NetworkPacket packet) {
        if(!isOnNetwork()) return;
        networkClient.send(packet);
    }

    // TODO Set state
    // TODO Setup game (SP/MP)
    // TODO Others


}
