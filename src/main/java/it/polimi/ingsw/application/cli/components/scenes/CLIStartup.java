package it.polimi.ingsw.application.cli.components.scenes;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;

public class CLIStartup extends CLIScene {

    public CLIStartup(String title) {
        super(title);
    }

    @Override
    public void show() {
        /*clearConsole();
        stdout("Maestri del Rinascimento");
        stdout("Loading...");*/
    }

    @Override
    public void getInput() {
        /*Scanner in = new Scanner(System.in);
        in.nextLine();*/
        while(GameApplication.getInstance().getApplicationState() == GameApplicationState.STARTUP){}
    }
}
