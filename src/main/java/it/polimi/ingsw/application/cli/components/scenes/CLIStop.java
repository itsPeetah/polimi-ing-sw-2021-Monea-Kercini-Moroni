package it.polimi.ingsw.application.cli.components.scenes;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.util.ANSIColor;

/**
 * CLIScene for when the application is terminated
 */
public class CLIStop extends CLIScene {

    public CLIStop() {
        super();
    }

    @Override
    public void show() {
        clearConsole();
        println("========= GAME OVER =========");
        println(ANSIColor.YELLOW + "Thank you for playing" + ANSIColor.RESET);
        println("Game by Cranio Creations");
        println("Programmed by Marvin Kercini, Giovanni Monea and Pietro Moroni");
        println("=============================");
    }
}
