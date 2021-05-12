package it.polimi.ingsw.controller.view.game;

import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.actions.data.*;
import it.polimi.ingsw.controller.model.handlers.ModelControllerIOHandler;
import it.polimi.ingsw.controller.model.handlers.SPModelControllerIOHandler;
import it.polimi.ingsw.controller.model.messages.Message;
import it.polimi.ingsw.controller.model.updates.Update;
import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.controller.view.game.handlers.GameControllerIOHandler;
import it.polimi.ingsw.controller.view.game.handlers.MPGameControllerIOHandler;
import it.polimi.ingsw.controller.view.game.handlers.SPGameControllerIOHandler;
import it.polimi.ingsw.view.common.GameData;
import it.polimi.ingsw.view.scenes.GameScene;

public class GameController {

    private final GameData gameData;
    private final GameControllerIOHandler gameControllerIOHandler;
    private GameState currentState;
    private GameScene currentScene;

    private int turn;
    private boolean mainActionUsed;

    /**
     * Generate and return a game controller for a SP game.
     */
    public static GameController generateSPGameController() {
        return new GameController(new GameData(), false);
    }

    /**
     * Generate and return a game controller for MP game.
     */
    public static GameController generateMPGameController() {
        return new GameController(new GameData(), true);
    }

    /**
     * Constructor for a game controller.
     * @param gameData data of the game.
     * @param isMultiplayer boolean indicating if the new game is a multiplayer game or not.
     */
    private GameController(GameData gameData, boolean isMultiplayer) {
        this.gameData = gameData;

        // If MultiPlayer
        if(isMultiplayer) this.gameControllerIOHandler = new MPGameControllerIOHandler(this);
        // If SinglePlayer
        else {
            // Generate SP Model IO handler
            SPModelControllerIOHandler spModelControllerIOHandler = new SPModelControllerIOHandler(this);

            // Generate SP GC IO handler
            this.gameControllerIOHandler = new SPGameControllerIOHandler(this, spModelControllerIOHandler);

            // Now the handlers are connected
        }

        currentState = GameState.IDLE;
    }

    public void reactToUpdate(Update update, UpdateData updateData) {
        // TODO code to apply the update

        // If the player is not in IDLE, it means that the action performed was accepted.
        if(currentState != GameState.IDLE) {
            // TODO what to do now?
            moveToState(GameState.ENDGAME);
            //moveToScene();

        }
    }

    public void reactToMessage(Message message) {
        String messageContent = message.toString();
        // Todo what to do with the message?
    }

    /**
     * React to an action performed by the player.
     * This class will push the action to the server if the check of the action is passed, or handle the problem if need be.
     */
    public void reactToAction(ActionPacket actionPacket) {
        Action action = actionPacket.getAction();

        // Check action correctness if necessary
        switch(action) {
            case CHOOSE_2_LEADERS:
                if(!checkChoose2LeadersAction(action.fromJsonToData(actionPacket.getData()))) return;
                break;
            case PUT_RESOURCES:
                if(!checkPutResourcesAction(action.fromJsonToData(actionPacket.getData()))) return;
                // Todo what to do if the player put resources is not successful?
                break;
        }

        // Push the action to the model controller
        gameControllerIOHandler.pushAction(actionPacket);
    }

    /* SPECIFIC ACTIONS CHECK METHODS */

    /**
     * Check correctness of this type of action.
     */
    private boolean checkPlayLeaderAction(ChooseLeaderActionData data) {
        return true;
    }

    /**
     * Check correctness of this type of action.
     */
    private boolean checkDiscardLeaderAction(ChooseLeaderActionData data) {
        return true;
    }

    /**
     * Check correctness of this type of action.
     */
    private boolean checkChoose2LeadersAction(Choose2LeadersActionData data) {
        return true;
    }

    /**
     * Check correctness of this type of action.
     */
    private boolean checkPutResourcesAction(PutResourcesActionData data) {
        return true;
    }

    /**
     * Check correctness of this type of action.
     */
    private boolean checkBuyDevCardAction(DevCardActionData data) {
        return true;
    }

    /**
     * Check correctness of this type of action.
     */
    private boolean checkProduceAction(ProduceActionData data) {
        return true;
    }



    protected void moveToState(GameState nextState) {
        currentState = nextState;
    }
    protected void moveToScene(GameScene nextScene) { currentScene = nextScene;}

    public GameControllerIOHandler getGameControllerIOHandler() {
        return gameControllerIOHandler;
    }

}
