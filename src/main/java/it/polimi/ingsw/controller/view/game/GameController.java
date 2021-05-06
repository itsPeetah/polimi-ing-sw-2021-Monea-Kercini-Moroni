package it.polimi.ingsw.controller.view.game;

import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.actions.data.ChooseLeaderActionData;
import it.polimi.ingsw.controller.model.actions.data.PutResourcesActionData;
import it.polimi.ingsw.controller.model.messages.Message;
import it.polimi.ingsw.controller.model.updates.Update;
import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.view.common.GameData;
import it.polimi.ingsw.view.scenes.GameScene;

public class GameController {

    private final GameData gameData;
    private final CommunicationHandler communicationHandler;
    private GameState currentState;
    private GameScene currentScene;

    public GameController(GameData gameData) {
        this.gameData = gameData;
        this.communicationHandler = new CommunicationHandler(this);
        currentState = GameState.IDLE;
    }

    protected void reactToUpdate(Update update, UpdateData updateData) {
        // TODO code to apply the update

        // If the player is not in IDLE, it means that the action performed was accepted.
        if(currentState != GameState.IDLE) {
            // TODO what to do now?
            moveToState(GameState.ENDGAME);
        }

    }

    protected void reactToMessage(Message message) {

    }

    /**
     * React to an action performed by the player.
     * This class will push the action to the server if the check of the action is passed, or handle the problem if need be.
     */
    protected void reactToAction(ActionPacket actionPacket) {
        Action action = actionPacket.getAction();

        // Check action correctness if necessary
        switch(action) {
            case CHOOSE_2_LEADERS:
                if(!checkChooseLeaderAction(action.fromJsonToData(actionPacket.getData()))) return;
                break;
            case PUT_RESOURCES:
                if(!checkPutResourcesAction(action.fromJsonToData(actionPacket.getData()))) return;
                break;
        }

        // Push the action to the model controller
        communicationHandler.pushAction(actionPacket);
    }

    /* SPECIFIC ACTIONS CHECK METHODS */

    /**
     * Check correctness of this type of action.
     */
    private boolean checkChooseLeaderAction(ChooseLeaderActionData data) {
        return true;
    }

    /**
     * Check correctness of this type of action.
     */
    private boolean checkPutResourcesAction(PutResourcesActionData data) {
        return true;
    }


    protected void moveToState(GameState nextState) {
        currentState = nextState;
    }

    public CommunicationHandler getCommunicationHandler() {
        return communicationHandler;
    }


}
