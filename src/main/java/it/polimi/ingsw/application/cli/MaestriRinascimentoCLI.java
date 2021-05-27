package it.polimi.ingsw.application.cli;

import it.polimi.ingsw.application.cli.components.CLIScenes;
import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.components.scenes.*;
import it.polimi.ingsw.application.cli.threads.CLIInputReader;
import it.polimi.ingsw.application.cli.threads.CLIOutputDisplay;
import it.polimi.ingsw.application.common.*;
import it.polimi.ingsw.model.game.Game;

public class MaestriRinascimentoCLI {

    public static final GameApplication gameApplication = new GameApplication();
    public static final GameApplicationIOHandler GAIOHandler = new GameApplicationIOHandler();

    public static boolean appRunning;

    public static final CLIHome startupScene = new CLIHome();
    public static final CLITitle titleScene = (CLITitle) CLIScenes.TITLE.getScene();
    public static final CLILobby lobbyScene = new CLILobby();
    public static final CLIRoom roomScene = new CLIRoom();
    public static final CLIGame gameScene = new CLIGame();
    public static final CLIStop stoppedScene = new CLIStop();

    public static void main(String[] args){

        /*startupScene.show();
        gameApplication.connect("localhost", 42069);*/

        /*CLIScene currentScene = CLIScenes.TITLE.getScene();
        GameApplicationState currentState = gameApplication.getApplicationState();
        GameApplicationState previousState = currentState;

        currentScene.show();*/

        CLIInputReader input = new CLIInputReader();
        CLIOutputDisplay output = new CLIOutputDisplay();

        Thread inputThread = new Thread(input); inputThread.setDaemon(true);
        Thread outputThread = new Thread(output); outputThread.setDaemon(true);

        inputThread.start();
        outputThread.start();

        while(GameApplication.getInstance().getApplicationState() != GameApplicationState.STOPPED)
        {}

        /*boolean done = false;
        while(!done){
            currentState = gameApplication.getApplicationState();

            // Change the scene
            if(currentState != previousState) {
                currentScene = CLIScenes.getCurrent();

                if(currentState == GameApplicationState.INGAME)
                    ((CLIGame)CLIScenes.GAME.getScene()).init();

                currentScene.show();
            }

            if(currentState == GameApplicationState.INGAME){
                currentScene.update();
            }

            if(currentState != GameApplicationState.STOPPED){
                currentScene.getInput();
            } else
                done = true;

            previousState = currentState;
        }*/
    }
}
