package it.polimi.ingsw.application.cli.components.scenes;

import it.polimi.ingsw.application.cli.components.CLICommand;
import it.polimi.ingsw.application.cli.components.CLICommandException;
import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.util.ANSIColor;

public class CLIHome extends CLIScene {

    public CLIHome() {
        super();
    }

    @Override
    public void show() {
        print("========= Masters of Renaissance =========");
        print(" ");
        print("Game modes:");
        print("[" + ANSIColor.CYAN + "SINGLE PLAYER" + ANSIColor.RESET + "]");
        print("[" + ANSIColor.CYAN + "MULTIPLAYER" + ANSIColor.RESET + "]");
        print("More options:");
        print("["+ ANSIColor.CYAN +"SETTINGS"+ ANSIColor.RESET +"]");
        print("["+ ANSIColor.CYAN +"CREDITS"+ ANSIColor.RESET +"]");
        print("==========================================");
    }

    @Override
    protected void help() {
        print("Use \"sp\" to start a single player game.");
        print("Use \"mp\" to enter the multiplayer lobby.");
        print("Use \"options\" to see the game options.");
        print("Use \"credits\" to visualize the credits.");
        print("Use \"quit\" to quit the game.");
    }

    @Override
    public void getInput() {
        String command = input.nextLine().split(" ")[0];
        try {
            switch (command) {
               /* case "sp":
                    CLICommand.selectGameMode.execute(new String[]{"singleplayer"});
                    break;*/
                case "mp":
                    CLICommand.selectGameMode.execute(new String[]{"multiplayer"});
                    break;
                case "help":
                    help();
                    break;
                default:
                    error("Command not supported or implemented yet.");
            }
        } catch (CLICommandException ex) {
            error(ex.getMessage());
        }


    }
}
