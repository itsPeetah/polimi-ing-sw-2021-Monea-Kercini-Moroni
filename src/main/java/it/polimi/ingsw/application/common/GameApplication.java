package it.polimi.ingsw.application.common;

import it.polimi.ingsw.controller.view.game.GameController;
import it.polimi.ingsw.controller.view.game.handlers.GameControllerIOHandler;
import it.polimi.ingsw.network.client.GameClient;
import it.polimi.ingsw.view.common.GameData;

public class GameApplication {

    protected static GameApplication instance;
    protected GameClient networkClient;
    protected GameApplicationState applicationState;
    protected GameApplicationIOHandler ioHandler;
    protected GameController gameController;

    protected boolean gameExists;

    public GameApplication(GameClient networkClient){
        this.applicationState = GameApplicationState.STARTED;
        this.networkClient = networkClient;
        this.ioHandler = new GameApplicationIOHandler();
        this.gameController = null;
    }

    public void setAsInstance(){
        instance = this;
    }

    public static GameApplication getInstance() throws NullPointerException{
        if(instance == null) throw new NullPointerException();
        return instance;
    }

    public GameApplicationState getState(){
        return applicationState;
    }

    public GameControllerIOHandler getGameControllerIO() throws NullPointerException{
        if(gameController == null) throw new NullPointerException();
        return gameController.getGameControllerIOHandler();
    }

    public GameApplicationIOHandler getIoHandler(){
        return ioHandler;
    }

    // TODO Set state
    // TODO Setup game (SP/MP)
    // TODO Others


}
