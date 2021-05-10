package it.polimi.ingsw.application.cli.components.scenes;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.util.ANSIColor;

public class CLIRoom extends CLIScene {

    public CLIRoom(String title) {
        super(title);
    }

    @Override
    public void show() {
        stdout("========= Game Room =========");
        stdout(ANSIColor.WHITE_BACKGROUND  + ANSIColor.BLACK + "Not implemented yet" + ANSIColor.RESET);
        stdout("=============================");
    }

    @Override
    public void getInput() {
        input.nextLine();
    }
}
