package it.polimi.ingsw.application.cli.components.scenes;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.components.scenes.game.*;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.controller.view.game.GameController;
import it.polimi.ingsw.controller.view.game.GameState;
import it.polimi.ingsw.controller.view.game.handlers.GameControllerIOHandler;

public class CLIGame extends CLIScene {

    private ICLIGameSubScene board = new CLIBoard();
    /*private CLIDevCardMarket devCardMarket = new CLIDevCardMarket();
    private CLIFaithTrack faithTrack = new CLIFaithTrack();*/
    private ICLIGameSubScene leadChoice = new CLILeadChoice();
    private ICLIGameSubScene resourceChoice = new CLIResourceChoice();
    /*private CLIResourceMarket resourceMarket = new CLIResourceMarket();*/
    private ICLIGameSubScene warehouseOrganizing = new CLIWarehouseOrganizing();

    private GameController gameController;
    private GameControllerIOHandler gameControllerIO;

    private ICLIGameSubScene currentView;

    private GameState currentGameState, previousGameState;

    public CLIGame() {
        super();
    }

    public void init(){
        this.gameController = GameApplication.getInstance().getGameController();
        this.gameControllerIO = GameApplication.getInstance().getGameControllerIO();
        currentGameState = gameController.getCurrentState();
        previousGameState = GameState.UNKNOWN;
    }

    @Override
    public void update() {

        currentGameState = gameController.getCurrentState();

        if(currentGameState != previousGameState){
            currentView = selectCurrentView(currentGameState);
            show();
        }

        previousGameState = currentGameState;
    }

    @Override
    public void show() {
        clearConsole();
        println("|         Masters of Renaissance - In Game         |");
        println("+--------------------------------------------------+");
        println("Current state: " + currentGameState.toString());

        if(currentView == null){
            println("");
            println("Please wait...");
            println("");
        } else {
            currentView.update(gameController.getGameData());
            currentView.show();
        }
        println("====================================================");
    }

    @Override
    public void help() {
        if(currentView != null) currentView.help();
        else super.help();
    }

    /*@Override
    public void getInput() {
        if(currentView != null) currentView.getInput();
        else {
            String cmd = input.nextLine();
            switch(cmd){
                case "help": help(); break;
                default: error("Unsupported command."); break;
            }
        }
    }*/

    @Override
    public void execute(String command, String[] arguments) {
        if(currentView != null) currentView.execute(command, arguments);
        else {
            switch(command){
                case "help": help(); break;
                default: error("Unsupported command."); break;
            }
        }
    }

    private ICLIGameSubScene selectCurrentView(GameState currentState){
        switch (currentState){
            case CHOOSE_LEADERS:
                return leadChoice;
            case PICK_RESOURCES:
                return resourceChoice;
            case ORGANIZE_WAREHOUSE:
                return warehouseOrganizing;
            default:
                return board;
        }
    }
}
