package it.polimi.ingsw.application.cli.components.commands;

import it.polimi.ingsw.application.cli.components.CLICommand;
import it.polimi.ingsw.application.cli.components.CLICommandException;

public class SelectGameModeCommand implements CLICommand {
    @Override
    public void execute(String[] args) throws CLICommandException {
        if(args == null || args.length < 1) throw new CLICommandException("Missing arguments.");
        switch (args[0]){
            case "singleplayer":
                System.out.println("SINGLE PLAYER MODE SELECTED");
                break;
            case "multiplayer":
                System.out.println("MULTI PLAYER MODE SELECTED");
                break;
            default:
                throw new CLICommandException("Invalid argument.");
        }
    }
}
