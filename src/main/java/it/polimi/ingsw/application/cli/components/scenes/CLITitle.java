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
        print("========= Masters of Renaissance =========");
        print("             - CLI Version -");
        print("==========================================");
        print(" ");
        print("Type \"help\" at any point to visualize available commands.");
        print("Press [ENTER] to continue.");
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
