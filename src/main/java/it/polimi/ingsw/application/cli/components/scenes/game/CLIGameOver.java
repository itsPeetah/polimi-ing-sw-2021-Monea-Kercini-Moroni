package it.polimi.ingsw.application.cli.components.scenes.game;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.controller.view.GameState;

public class CLIGameOver extends CLIScene {


    @Override
    public void show() {
            System.out.println("<=====> GAME OVER <======>\n");
        if (GameApplication.getInstance().getGameController().isSinglePlayer()) {
           // Nothing, we already have a message printed
        } else {
            for (String player : GameApplication.getInstance().getGameController().getGameData().getPlayersList()) {
                System.out.println(player + " scored " + GameApplication.getInstance().getGameController().getGameData().getPlayerData(player).getVP() + " points!");
            }
            System.out.println();
        }
            System.out.println("type \"quit\" to continue");
            System.out.println("<========================>");
    }

    @Override
    public void execute(String command, String[] arguments) {
        switch (command){
            case "quit":

                break;
            default:
                System.out.println(ANSIColor.RED + "unsupported command" + ANSIColor.RESET);
                break;
        }
    }
}
