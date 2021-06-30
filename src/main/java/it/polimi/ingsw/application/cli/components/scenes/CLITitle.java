package it.polimi.ingsw.application.cli.components.scenes;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;

/**
 * Title screen for the game
 */
public class CLITitle extends CLIScene {

    public CLITitle() {
        super();
    }

    @Override
    public void show() {
        clearConsole();
        println("========= Masters of Renaissance =========");
        println("             - CLI Version -");
        println("==========================================");
        println(" ");
        println("Type \"help\" at any point to visualize available commands.");
        println("Press [ENTER] to continue.");
    }

    @Override
    public void execute(String command, String[] arguments) {
        // Just wait for the user to perform any input to start
        GameApplication.getInstance().setApplicationState(GameApplicationState.STARTED);
    }
}
