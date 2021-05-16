package it.polimi.ingsw.application.cli.components.scenes;

import it.polimi.ingsw.application.cli.components.CLICommand;
import it.polimi.ingsw.application.cli.components.CLICommandException;
import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.util.ANSIColor;

public class CLIHome extends CLIScene {

    public CLIHome() {
        super();
        allowedCommands.add("sp");
        allowedCommands.add("mp");
    }

    @Override
    public void show() {
        print("========= Masters of Renaissance =========");
        print(" ");
        print("Game modes:");
        print("[ " + ANSIColor.CYAN + "SINGLE PLAYER" + ANSIColor.RESET + " ]");
        print("[ " + ANSIColor.CYAN + "MULTIPLAYER" + ANSIColor.RESET + " ]");
        print("More options:");
        print("[ "+ ANSIColor.CYAN +"SETTINGS"+ ANSIColor.RESET +" ]");
        print("[ "+ ANSIColor.CYAN +"CREDITS"+ ANSIColor.RESET +" ]");
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
        String[] commandFields = input.nextLine().split(" ");

        if(!allowedCommands.contains(commandFields[0]))
            error("Command not supported or implemented yet.");
        else{
            try {
                switch (commandFields[0]) {
                    case "sp":
                        CLICommand.selectGameMode.execute(new String[]{"singleplayer"});
                        break;
                    case "mp":
                        CLICommand.selectGameMode.execute(new String[]{"multiplayer"});
                        break;
                    case "help":
                        help();
                        break;
                }
            } catch (CLICommandException ex){
                error(ex.getMessage());
            }
        }

    }
}
