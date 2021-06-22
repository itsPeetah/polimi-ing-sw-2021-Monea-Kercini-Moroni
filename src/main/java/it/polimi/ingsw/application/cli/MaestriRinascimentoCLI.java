package it.polimi.ingsw.application.cli;

import it.polimi.ingsw.application.cli.components.CLIScenes;
import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.components.scenes.*;
import it.polimi.ingsw.application.cli.threads.CLIInputReader;
import it.polimi.ingsw.application.cli.threads.CLIOutputDisplay;
import it.polimi.ingsw.application.common.*;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.network.MaestriRinascimentoServer;

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

    public static String serverHostName;
    public static int serverPortNumber;

    public static void main(String[] args){

        // TODO MOVE somewhere else
        if(args.length < 2) {
            System.out.println("Please provide server address and port");
            return;
        }

        serverHostName = args[0];
        serverPortNumber = Integer.parseInt(args[1]);

        CLIInputReader input = new CLIInputReader();
        CLIOutputDisplay output = new CLIOutputDisplay();

        Thread inputThread = new Thread(input); inputThread.setDaemon(true);
        Thread outputThread = new Thread(output); outputThread.setDaemon(true);

        inputThread.start();
        outputThread.start();

        while(GameApplication.getInstance().getApplicationState() != GameApplicationState.STOPPED)
        {}
    }
}
