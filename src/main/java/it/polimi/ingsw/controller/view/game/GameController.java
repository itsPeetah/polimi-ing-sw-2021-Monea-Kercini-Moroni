package it.polimi.ingsw.controller.view.game;

import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.actions.data.*;
import it.polimi.ingsw.controller.model.handlers.ModelControllerIOHandler;
import it.polimi.ingsw.controller.model.messages.Message;
import it.polimi.ingsw.controller.model.updates.Update;
import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.controller.model.updates.data.ResourceMarketUpdateData;
import it.polimi.ingsw.controller.view.game.handlers.GameControllerIOHandler;
import it.polimi.ingsw.controller.view.game.handlers.MPGameControllerIOHandler;
import it.polimi.ingsw.controller.view.game.handlers.SPGameControllerIOHandler;
import it.polimi.ingsw.view.data.GameData;
import it.polimi.ingsw.view.scenes.GameScene;

public class GameController {

    private final GameData gameData;
    private final GameControllerIOHandler gameControllerIOHandler;
    private GameState currentState;
    private GameScene currentScene;

    private int turn;
    private boolean mainActionUsed;

    /**
     * Constructor for MP game controller.
     * @param gameData
     */
    public GameController(GameData gameData) {
        this.gameData = gameData;
        this.gameControllerIOHandler = new MPGameControllerIOHandler(this);
        currentState = GameState.IDLE;
    }

    /**
     * Constructor for SP game controller.
     * @param gameData
     * @param modelControllerIOHandler IO handler of the model controller, necessary to communicate without network.
     */
    public GameController(GameData gameData, ModelControllerIOHandler modelControllerIOHandler) {
        this.gameData = gameData;
        this.gameControllerIOHandler = new SPGameControllerIOHandler(this, modelControllerIOHandler);
        currentState = GameState.IDLE;
    }

    public void reactToUpdate(Update update, UpdateData updateData) {

        //apply update

        switch (update){

            case RESOURCE_MARKET:




        }


    }

    public void reactToMessage(Message message) {
        String messageContent = message.toString();
        // Todo what to do with the message?

        // If the player is not in IDLE, it means that the action performed was accepted.
        if(currentState != GameState.IDLE) {
            // TODO what to do now?
            moveToState(GameState.ENDGAME);
            //moveToScene();

        }
    }

    /**
     * React to an action performed by the player.
     * This class will push the action to the server if the check of the action is passed, or handle the problem if need be.
     *
     * action.fromJsonToData(actionPacket.getData()
     */
    public void reactToAction(ActionPacket actionPacket) {
        Action action = actionPacket.getAction();

        // Push the action to the model controller
        gameControllerIOHandler.pushAction(actionPacket);
    }

    protected void moveToState(GameState nextState) {
        currentState = nextState;
    }
    protected void moveToScene(GameScene nextScene) { currentScene = nextScene;}

    public GameControllerIOHandler getGameControllerIOHandler() {
        return gameControllerIOHandler;
    }

}
