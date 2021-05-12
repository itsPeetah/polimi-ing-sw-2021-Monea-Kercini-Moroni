package it.polimi.ingsw.application.cli;

import it.polimi.ingsw.application.cli.components.scenes.*;
import it.polimi.ingsw.application.common.*;

public class MaestriRinascimentoCLI {

    public static final GameApplication gameApplication = new GameApplication();
    public static final GameApplicationIOHandler GAIOHandler = new GameApplicationIOHandler();

    public static boolean appRunning;

    public static final CLIStartup startupScene = new CLIStartup("Maestri del Rinascimento - Connessione in corso");
    public static final CLITitle titleScene = new CLITitle("Maestri del Rinascimento - Home");
    public static final CLILobby lobbyScene = new CLILobby("Maestri del Rinascimento - Lobby");
    public static final CLIRoom roomScene = new CLIRoom("Maestri del Rinascimento - Pre-Partita");
    public static final CLIGame gameScene = new CLIGame("Maestri del Rinascimento - In Gioco");
    public static final CLIStop stoppedScene = new CLIStop("Maestri del Rinascimento - Fine");

    public static void main(String[] args){

        startupScene.show();

        gameApplication.connect("localhost", 42069);

        boolean done = false;
        while(!done){

            GameApplicationState state = gameApplication.getApplicationState();
            switch (state){
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
        }

    }
}
