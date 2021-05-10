package it.polimi.ingsw.application.cli;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.components.scenes.*;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.network.client.GameClient;

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
        GameClient networkClient = new GameClient("localhost", 42069);

        GameApplication gameApplication = new GameApplication(networkClient);
        gameApplication.setAsInstance();

        appRunning = true;
        while(appRunning){

            switch (GameApplication.getInstance().getState()){
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

            currentScene.update();
            currentScene.show();
            currentScene.getInput();
        }



    }
}
