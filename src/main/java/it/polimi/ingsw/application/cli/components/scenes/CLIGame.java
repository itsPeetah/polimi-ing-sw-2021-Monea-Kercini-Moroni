package it.polimi.ingsw.application.cli.components.scenes;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.components.scenes.game.*;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.controller.view.GameController;
import it.polimi.ingsw.controller.view.GameState;
import it.polimi.ingsw.controller.view.handlers.GameControllerIOHandler;
import it.polimi.ingsw.application.common.GameApplicationIOHandler;
import it.polimi.ingsw.controller.model.actions.ActionPacket;


/**
 * Parent CLIScene for the game phase
 */
public class CLIGame extends CLIScene {

    private CLIScene board = new CLIBoard();
    private CLIScene leadChoice = new CLILeadChoice();
    private CLIScene resourceChoice = new CLIResourceChoice();
    private CLIScene warehouseOrganizing = new CLIWarehouseOrganizing();
    private CLIScene gameOverScene = new CLIGameOver();

    private GameController gameController;
    private GameControllerIOHandler gameControllerIO;

    private CLIScene currentView;

    private GameState currentGameState, previousGameState;

    public CLIGame() {
        super();
    }

    public void init() {
        /*this.gameController = GameApplication.getInstance().getGameController();
        this.gameControllerIO = GameApplication.getInstance().getGameControllerIO();
        currentGameState = gameController.getCurrentState();*/
        previousGameState = GameState.UNKNOWN;
    }

    private static GameController getGameController(){
        return GameApplication.getInstance().getGameController();
    }
    private GameControllerIOHandler getGameControllerIO(){
        return GameApplication.getInstance().getGameControllerIO();
    }
    private GameState getCurrentGameState(){
        return getGameController().getCurrentState();
    }

    @Override
    public void update() {

        currentGameState = getCurrentGameState();

        if (getCurrentGameState() != previousGameState) {
            currentView = selectCurrentView(currentGameState);
            show();
        }

        previousGameState = currentGameState;
    }

    @Override
    public void show() {

        if (currentView == null) {
            println("");
            println("Please wait...");
            println("");
        } else {
            currentView.update();
            currentView.show();
        }
        println("====================================================");
    }

    @Override
    public void help() {
        if (currentView != null) currentView.help();
        else super.help();
    }

    @Override
    public void execute(String command, String[] arguments) {
        if (currentView != null) currentView.execute(command, arguments);
        else {
            switch (command) {
                case "help":
                    help();
                    break;
                default:
                    error("Unsupported command.");
                    break;
            }
        }
    }

    /**
     * Select the current game subscene depending on the current game state
     */
    private CLIScene selectCurrentView(GameState currentState) {
        switch (currentState) {
            case CHOOSE_LEADERS:
                return leadChoice;
            case PICK_RESOURCES:
                return resourceChoice;
            case ORGANIZE_WAREHOUSE:
                return warehouseOrganizing;
            case GAME_LOST:
            case GAME_WON:
                return gameOverScene;
            default:
                return board;
        }
    }

    /**
     * Push the action packet to the game controller (either local or remote)
     */
    public static void pushAction(ActionPacket ap) {

        getGameController().moveToState(GameState.IDLE);

        if (getGameController().isSinglePlayer() && !GameApplication.getInstance().isOnNetwork()) {
            GameApplication.getInstance().getGameControllerIO().notifyAction(ap);
        } else {
            GameApplicationIOHandler.getInstance().pushAction(ap);
        }
    }
}
