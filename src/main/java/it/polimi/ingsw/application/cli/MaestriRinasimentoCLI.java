package it.polimi.ingsw.application.cli;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.components.scenes.*;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationException;
import it.polimi.ingsw.application.common.GameApplicationMode;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.network.client.GameClient;
import it.polimi.ingsw.network.common.sysmsg.ISystemMessage;

public class MaestriRinasimentoCLI {

    public static boolean appRunning;

    public static final CLIStartup startupScene = new CLIStartup("Maestri del Rinascimento - Connessione in corso");
    public static final CLITitle titleScene = new CLITitle("Maestri del Rinascimento - Home");
    public static final CLILobby lobbyScene = new CLILobby("Maestri del Rinascimento - Lobby");
    public static final CLIRoom roomScene = new CLIRoom("Maestri del Rinascimento - Pre-Partita");
    public static final CLIGame gameScene = new CLIGame("Maestri del Rinascimento - In Gioco");

    public static CLIScene currentScene;

    public static void main(String[] args){

        currentScene = startupScene;
        /*GameClient networkClient = new GameClient("localhost", 42069);*/

        GameApplication gameApplication = new GameApplication(GameApplicationMode.CLI);
        currentScene.show();

        try {
            gameApplication.connect("localhost", 42069);
        } catch (GameApplicationException ex){
            System.out.println(ex.getMessage());
            return;
        }

        appRunning = true;
        while(appRunning){

            GameApplicationState state = gameApplication.getApplicationState();
            switch (state){
                case STARTED:
                    currentScene = titleScene;
                    break;
                case LOBBY:
                    currentScene = lobbyScene;
                    break;
                case PREGAME:
                    currentScene = roomScene;
                    break;
                case INGAME:
                    currentScene = gameScene;
                    break;
            }

            if(state != GameApplicationState.STARTUP) {
                currentScene.show();
                currentScene.getInput();
            }
        }

    }
}
