package it.polimi.ingsw.controller.view;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationMode;
import it.polimi.ingsw.controller.model.ModelController;
import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.handlers.SPModelControllerIOHandler;
import it.polimi.ingsw.controller.model.messages.Message;
import it.polimi.ingsw.controller.model.updates.Update;
import it.polimi.ingsw.controller.model.updates.data.*;
import it.polimi.ingsw.controller.view.handlers.GameControllerIOHandler;
import it.polimi.ingsw.controller.view.handlers.MPGameControllerIOHandler;
import it.polimi.ingsw.controller.view.handlers.SPGameControllerIOHandler;
import it.polimi.ingsw.view.data.GameData;

import java.util.concurrent.atomic.AtomicBoolean;

public class GameController {

    private final GameData gameData;
    private final GameControllerIOHandler gameControllerIOHandler;
    private GameState currentState;
    private boolean gameStarted = false;
    private final AtomicBoolean singlePlayer = new AtomicBoolean(false);


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

        this.singlePlayer.set(true);

        this.gameData = gameData;
        gameData.addPlayer(playerNickname);

        // Generate SP Model IO handler
        SPModelControllerIOHandler spModelControllerIOHandler = new SPModelControllerIOHandler(this);

        // Generate model controller
        ModelController modelController = new ModelController(spModelControllerIOHandler);
        modelController.addPlayer(playerNickname);
        modelController.setSinglePlayer(true);

        // Generate SP GC IO handler
        this.gameControllerIOHandler = new SPGameControllerIOHandler(this, spModelControllerIOHandler);

        // Now that the handlers are connected, start the game on a new thread
        new Thread(modelController::setupGame).start();

        this.currentState = GameState.IDLE;
    }

    public GameData getGameData() {
        return gameData;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public boolean isSinglePlayer() {
        return singlePlayer.get();
    }

    public void setSinglePlayer(boolean singlePlayer) {
        this.singlePlayer.set(singlePlayer);
    }

    /**
     * Method that reacts to an update by sending it to the view "model"
     * @param update
     * @param updateDataString
     */
    public void reactToUpdate(Update update, String updateDataString) {

        switch (update){

            case RESOURCE_MARKET:

                ResourceMarketUpdateData res = update.getUpdateData(updateDataString);
                gameData.getCommon().getMarketTray().setAvailable(res.getMT().getAvailable());
                try {
                    gameData.getCommon().getMarketTray().setWaiting(res.getMT().getWaiting());
                } catch(Exception e) {
                    e.printStackTrace();
                }
                break;

            case DEVCARD_MARKET:
                DevCardMarketUpdateData dcm = update.getUpdateData(updateDataString);
                gameData.getCommon().getDevCardMarket().setAvailableCards(dcm.getDevCardMarket().getVisibleCards());
                break;

            case PRODUCTION_POWERS:
                //System.out.println("PRODUCTION POWERS UPDATE CAME IN GAME CONTROLLER");
                ProductionPowersUpdateData pp = update.getUpdateData(updateDataString);
                System.out.println(pp.getProductionPowers().getOwnedDevCards().get(0).getCardId());
                if(!gameData.getPlayersList().contains(pp.getPlayer())) gameData.addPlayer(pp.getPlayer());
                gameData.getPlayerData(pp.getPlayer()).getDevCards().setDevCards(pp.getProductionPowers().getVisibleDevCards());
                break;

            case LEADERS:
                PlayerLeadersUpdateData pl = update.getUpdateData(updateDataString);
                if(!gameData.getPlayersList().contains(pl.getP())) gameData.addPlayer(pl.getP());
                gameData.getPlayerData(pl.getP()).getPlayerLeaders().setLeaders(pl.getPlayerLeaders().getCards());
                gameData.getPlayerData(pl.getP()).getPlayerLeaders().setStates(pl.getPlayerLeaders().getCardStates());
                break;

            case WAREHOUSE:
                WarehouseUpdateData wh = update.getUpdateData(updateDataString);
                if(!gameData.getPlayersList().contains(wh.getPlayer())) gameData.addPlayer(wh.getPlayer());
                gameData.getPlayerData(wh.getPlayer()).getWarehouse().setContent(wh.getWarehouse().getContent());
                gameData.getPlayerData(wh.getPlayer()).getWarehouse().setExtra(wh.getWarehouse().getLeaderExtraUsed());
                gameData.getPlayerData(wh.getPlayer()).getWarehouse().setActivatedLeaders(wh.getWarehouse().getLeadersExtra());
                gameData.getPlayerData(wh.getPlayer()).getStrongbox().setContent(wh.getStrongbox().getResourcesAvailable());
                break;

            case VP:
                VPUpdateData vp = update.getUpdateData(updateDataString);

                //Adding to each player their corresponding victory points
                for (int i = 0; i < vp.getPlayers().length; i++) {
                    if(!gameData.getPlayersList().contains(vp.getPlayers()[i])) gameData.addPlayer(vp.getPlayers()[i]);
                    gameData.getPlayerData(vp.getPlayers()[i]).setVP(vp.getVP()[i]);
                }
                break;

            case FAITH:

                FaithUpdateData fp = update.getUpdateData(updateDataString);

                for (int i = 0; i < fp.getPlayers().length; i++) {
                    if(!gameData.getPlayersList().contains(fp.getPlayers()[i])) gameData.addPlayer(fp.getPlayers()[i]);

                    //adding to each player the corresponding faith points
                    gameData.getPlayerData(fp.getPlayers()[i]).getFaithTrack().setFaith(fp.getFp()[i]);

                    //adding to each player the corresponding reports attended
                    gameData.getPlayerData(fp.getPlayers()[i]).getFaithTrack().setReportsAttended(fp.getReportsAttended()[i]);
                }
                break;

            case SOLO_ACTION:
                ActionTokenUpdateData at = update.getUpdateData(updateDataString);
                gameData.getCommon().getLorenzo().setBlackCross(at.getBlackCross());
                gameData.getCommon().getLorenzo().setLastToken(at.getActionToken());
                break;

            case LEADERS_TO_CHOOSE_FROM:
                DisposableLeadersUpdateData lUP = update.getUpdateData(updateDataString);
                if(!gameData.getPlayersList().contains(lUP.getP())) gameData.addPlayer(lUP.getP());
                gameData.getPlayerData(lUP.getP()).getLeadersToChooseFrom().setLeaders(lUP.getLeaders());
                break;

            case RESOURCES_TO_PUT:
                ResourcesToPutUpdateData rUP = update.getUpdateData(updateDataString);

                gameData.getMomentary().getResourcesToPut().setRes(rUP.getRes());
                break;

            case CURRENT_PLAYER:
                CurrentPlayerUpdateData cp = update.getUpdateData(updateDataString);
                gameData.getCommon().setCurrentPlayer(cp.getCurrentPlayer());
                break;
        }
    }

    /**
     * The message and the state will decide the next state of the player
     * @param message
     */

    public void reactToMessage(Message message) {
        String messageContent = message.toString();

        switch (message){

            case GAME_HAS_STARTED:
                GameApplication.getInstance().out(messageContent);
                gameStarted = true;
                //Player must wait for his turn
                moveToState(GameState.IDLE);
                break;

            case CHOOSE_LEADERS:
                GameApplication.getInstance().out(messageContent);
                moveToState(GameState.CHOOSE_LEADERS);
                break;

            case CHOOSE_RESOURCE:
            case CHOOSE_REPLACEMENT:
                GameApplication.getInstance().out(messageContent);
                moveToState(GameState.PICK_RESOURCES);
                break;

            case INCORRECT_REPLACEMENT:
                //Super rare case
                //Triggered when player has 2 leaders with replace white ability activated and yet chooses another
                // resource to replace white
                GameApplication.getInstance().out(messageContent);
                moveToState(GameState.PICK_RESOURCES);
                break;

            case START_TURN:
                GameApplication.getInstance().out(messageContent);
                moveToState(GameState.TURN_CHOICE);
                break;

            case NOT_ENOUGH_RESOURCES:
                //This is triggered whether by trying to produce without having the necessary input or not enough to
                if(GameApplication.getOutputMode() == GameApplicationMode.CLI){
                    moveToState(GameState.TURN_CHOICE);
                }
                GameApplication.getInstance().out(messageContent);
                break;
            case ILLEGAL_CARD_PLACE:
                // when buying a devCard
            case REQUIREMENTS_NOT_MET:
                // when activating a leader
            case ALREADY_USED_PRIMARY_ACTION:
                //game stays at the same state (turn choice)
                GameApplication.getInstance().out(messageContent);
                break;

            case WAREHOUSE_UNORGANIZED:
                GameApplication.getInstance().out(messageContent);
                //This might be triggered in different scenarios
                //Player MUST have his warehouse organized before going on

                moveToState(GameState.ORGANIZE_WAREHOUSE);
                break;

            case WINNER:
                //This player is the winner
            case LOSER:
                //This player is a loser
            case LOSER_MULTIPLAYER:
                //This player gets a personalized message for losing in multiplayer
                GameApplication.getInstance().out(messageContent);
                moveToState(GameState.ENDGAME);
                break;

            case OK:
                //This is the most tricky case
                //The next state that comes after the ok depends on which game phase we are


                GameApplication.getInstance().out(messageContent); //This will probably be removed but might help testing

                if(gameStarted) {
                    //This means that the player is doing an action at his turn so he is still playing his turn
                    moveToState(GameState.TURN_CHOICE);
                }else{
                    //Player was requested an action and he performed it correctly
                    moveToState(GameState.IDLE);
                }
                break;

            case SELECT_INPUT:
                if(GameApplication.getOutputMode() == GameApplicationMode.CLI){
                    moveToState(GameState.PICK_RESOURCES);
                }
            case SELECT_OUTPUT:
                if(GameApplication.getOutputMode() == GameApplicationMode.CLI){
                    moveToState(GameState.PICK_RESOURCES);
                }
                //No need for changing game state here
                //These messages are just for helping player understand what is he choosing for when he is producing
                GameApplication.getInstance().out(messageContent);
                break;

            case TURN_PASSED:
                gameData.turnIncrement();

        }
    }

    /**
     * React to an action performed by the player.
     * This class will push the action to the server, or handle the problem if need be.
     */
    public void reactToAction(ActionPacket actionPacket) {

        //If the action is end turn the state should be changed
        if(actionPacket.getAction()== Action.END_TURN){
            moveToState(GameState.IDLE);
        }
    }


    public void moveToState(GameState nextState) {
        currentState = nextState;
    }

    public GameControllerIOHandler getGameControllerIOHandler() {
        return gameControllerIOHandler;
    }

}