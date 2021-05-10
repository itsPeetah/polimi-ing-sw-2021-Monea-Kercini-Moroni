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
        stdout("Maestri del Rinascimento (versione CLI)");
    }

    @Override
    public void getInput() {
        Scanner in = new Scanner(System.in);
        stdout("Premi invio per continuare.");
        in.nextLine();
        GameApplication.getInstance().setApplicationState(GameApplicationState.LOBBY);
    }
}
