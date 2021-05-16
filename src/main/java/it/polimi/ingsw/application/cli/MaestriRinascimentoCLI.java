package it.polimi.ingsw.application.cli;

import it.polimi.ingsw.application.cli.components.CLIScenes;
import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.components.scenes.*;
import it.polimi.ingsw.application.common.*;

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

        CLIScene currentScene = CLIScenes.TITLE.getScene();
        GameApplicationState currentState = gameApplication.getApplicationState();
        GameApplicationState previousState = currentState;

        currentScene.show();

        boolean done = false;
        while(!done){
            currentState = gameApplication.getApplicationState();

            if(currentState != previousState) {
                currentScene = CLIScenes.getCurrent();
                currentScene.show();
            }

            if(currentState != GameApplicationState.STOPPED){
                currentScene.getInput();
            } else
                done = true;

            /*switch (currentState){
                case STARTED:
                    titleScene.show();
                    titleScene.getInput();
                    break;
                case LOBBY:
                    lobbyScene.show();
                    lobbyScene.getInput();
                    break;
                case PREGAME:
                    roomScene.show();
                    roomScene.getInput();
                    break;
                case INGAME:
                    gameScene.show();
                    gameScene.getInput();
                    break;
                case STOPPED:
                    stoppedScene.show();
                    done = true;
                    break;
                default:
                    // Do nothing...
                    break;
            }
*/
            previousState = currentState;
        }
    }
}
