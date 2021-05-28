package it.polimi.ingsw.application.cli.components.scenes;

import it.polimi.ingsw.application.cli.MaestriRinascimentoCLI;
import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;

public class CLIHome extends CLIScene {

    public CLIHome() {
        super();
    }

    @Override
    public void show() {
        println("========= Masters of Renaissance =========");
        println(" ");
        println("Game modes:");
        println("[" + ANSIColor.CYAN + "SINGLE PLAYER" + ANSIColor.RESET + "]");
        println("[" + ANSIColor.CYAN + "MULTIPLAYER" + ANSIColor.RESET + "]");
        println("More options:");
        println("[" + ANSIColor.CYAN + "SETTINGS" + ANSIColor.RESET + "]");
        println("[" + ANSIColor.CYAN + "CREDITS" + ANSIColor.RESET + "]");
        println("==========================================");
    }

    @Override
    public void help() {
        println("Use \"sp\" to start a single player game.");
        println("Use \"mp\" to enter the multiplayer lobby.");
        println("Use \"options\" to see the game options.");
        println("Use \"credits\" to visualize the credits.");
        println("Use \"quit\" to quit the game.");
    }

    @Override
    public void execute(String command, String[] arguments) {
        switch (command) {
            case "sp":
                selectSinglePlayerMode();
                break;
            case "mp":
                selectMultiplayerMode();
                break;
            case "help":
                help();
                break;
            default:
                error("Command not supported or implemented yet.");
        }
    }

    private void selectSinglePlayerMode(){
        println(ANSIColor.YELLOW + "Selected Single Player Mode" + ANSIColor.RESET);
    }

    private void selectMultiplayerMode(){
        println(ANSIColor.YELLOW + "Selected Multi Player Mode" + ANSIColor.RESET);

        GameApplication.getInstance().connect(MaestriRinascimentoCLI.serverHostName, MaestriRinascimentoCLI.serverPortNumber);
        if(GameApplication.getInstance().isOnNetwork()){
            GameApplication.getInstance().setApplicationState(GameApplicationState.LOBBY);
        } else{
            show();
            error("Could not connect to the server...");
        }
    }
}
