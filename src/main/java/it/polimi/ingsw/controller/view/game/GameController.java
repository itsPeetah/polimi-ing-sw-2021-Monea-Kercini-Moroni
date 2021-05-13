package it.polimi.ingsw.controller.view.game;

import it.polimi.ingsw.controller.model.ModelController;
import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.actions.data.*;
import it.polimi.ingsw.controller.model.handlers.ModelControllerIOHandler;
import it.polimi.ingsw.controller.model.handlers.SPModelControllerIOHandler;
import it.polimi.ingsw.controller.model.messages.Message;
import it.polimi.ingsw.controller.model.updates.Update;
import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.controller.model.updates.data.*;
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
     * Constructor for a MP game controller.
     * @param gameData data of the game.
     */
    public GameController(GameData gameData) {
        this.gameData = gameData;
        this.gameControllerIOHandler = new MPGameControllerIOHandler(this);
        this.currentState = GameState.IDLE;
    }

    /**
     * Constructor for a SP game controller.
     * @param gameData data of the game.
     * @param playerNickname nickname of the player.
     */
    public GameController(GameData gameData, String playerNickname) {
        this.gameData = gameData;

        // Generate SP Model IO handler
        SPModelControllerIOHandler spModelControllerIOHandler = new SPModelControllerIOHandler(this);

        // Generate model controller
        ModelController modelController = new ModelController(spModelControllerIOHandler);
        modelController.addPlayer(playerNickname);
        modelController.setSinglePlayer(true);

        // Generate SP GC IO handler
        this.gameControllerIOHandler = new SPGameControllerIOHandler(this, spModelControllerIOHandler);

        // Now that the handlers are connected, start the game
        modelController.setupGame();

        this.currentState = GameState.IDLE;
    }

    /**
     * Method that reacts to an update by sending it to the view "model"
     * @param update
     * @param updateDataString
     */

    public void reactToUpdate(Update update, String updateDataString) {
        // TODO code to apply the update

        switch (update){

            case RESOURCE_MARKET:
                ResourceMarketUpdateData res = update.getUpdateData(updateDataString);
                gameData.getCommon().getMarketTray().setAvailable(res.getMT().getAvailable());
                gameData.getCommon().getMarketTray().setWaiting(res.getMT().getWaiting());
                break;

            case DEVCARD_MARKET:
                DevCardMarketUpdateData dcm = update.getUpdateData(updateDataString);
                gameData.getCommon().getDevCardMarket().setAvailableCards(dcm.getDevCardMarket().getVisibleCards());
                break;

            case PRODUCTION_POWERS:
                ProductionPowersUpdateData pp = update.getUpdateData(updateDataString);
                gameData.getPlayerData(pp.getPlayer()).getDevCards().setDevCards(pp.getProductionPowers().getVisibleDevCards());
                break;

            case LEADERS:
                PlayerLeadersUpdateData pl = update.getUpdateData(updateDataString);
                gameData.getPlayerData(pl.getP()).getPlayerLeaders().setLeaders(pl.getPlayerLeaders().getCards());
                gameData.getPlayerData(pl.getP()).getPlayerLeaders().setStates(pl.getPlayerLeaders().getCardStates());
                break;

            case WAREHOUSE:
                WarehouseUpdateData wh = update.getUpdateData(updateDataString);
                gameData.getPlayerData(wh.getPlayer()).getWarehouse().setContent(wh.getWarehouse().getContent());
                gameData.getPlayerData(wh.getPlayer()).getWarehouse().setExtra(wh.getWarehouse().getLeaderExtraUsed());
                break;

            case VP:

        }

        // If the player is not in IDLE, it means that the action performed was accepted.
        if(currentState != GameState.IDLE) {
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