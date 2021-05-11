package it.polimi.ingsw.application.cli.components.scenes;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.util.ANSIColor;

public class CLIStop extends CLIScene {

    public CLIStop(String title) {
        super(title);
    }

    @Override
    public void show() {
        print("========= GAME OVER =========");
        print(ANSIColor.YELLOW + "Thank you for playing" + ANSIColor.RESET);
        print("Game by Cranio Creations");
        print("Programmed by Marvin Kercini, Giovanni Monea and Pietro Moroni");
        print("=============================");
    }
}
