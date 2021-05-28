package it.polimi.ingsw.application.cli.components.scenes;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;

public class CLITitle extends CLIScene {

    public CLITitle() {
        super();
    }

    @Override
    public void show() {
        println("========= Masters of Renaissance =========");
        println("             - CLI Version -");
        println("==========================================");
        println(" ");
        println("Type \"help\" at any point to visualize available commands.");
        println("Press [ENTER] to continue.");
    }
    /*
    @Override
    public void getInput() {
        input.nextLine();
        GameApplication.getInstance().setApplicationState(GameApplicationState.STARTED);
    }*/

    @Override
    public void execute(String command, String[] arguments) {

        GameApplication.getInstance().setApplicationState(GameApplicationState.STARTED);
    }
}
