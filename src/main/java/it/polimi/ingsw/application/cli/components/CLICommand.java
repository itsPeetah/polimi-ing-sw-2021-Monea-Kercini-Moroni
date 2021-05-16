package it.polimi.ingsw.application.cli.components;

import it.polimi.ingsw.application.cli.components.commands.SelectGameModeCommand;

public interface CLICommand {

    public static final CLICommand selectGameMode = new SelectGameModeCommand();

    void execute(String[] args) throws CLICommandException;

}
