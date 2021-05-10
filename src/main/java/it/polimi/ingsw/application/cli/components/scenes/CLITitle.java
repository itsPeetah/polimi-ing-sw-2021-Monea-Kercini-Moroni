package it.polimi.ingsw.application.cli.components.scenes;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;

import java.util.Scanner;

public class CLITitle extends CLIScene {

    public CLITitle(String title) {
        super(title);
    }

    @Override
    public void show() {
        stdout("========= Masters of Renaissance =========");
        stdout("CLI Version");
        stdout("==========================================");
    }
    @Override
    public void getInput() {
        stdout("Press [ENTER] to continue.");
        input.nextLine();
        GameApplication.getInstance().setApplicationState(GameApplicationState.LOBBY);
    }
}
