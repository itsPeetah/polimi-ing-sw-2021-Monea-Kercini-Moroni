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
        print("CLI Version");
        print("==========================================");
    }
    @Override
    public void getInput() {
        print("Press [ENTER] to continue.");
        input.nextLine();
        GameApplication.getInstance().setApplicationState(GameApplicationState.LOBBY);
    }
}
