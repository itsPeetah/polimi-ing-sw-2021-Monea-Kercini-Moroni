package it.polimi.ingsw.application.cli.components.scenes;

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
        print("========= Masters of Renaissance =========");
        print(" ");
        print("Game modes:");
        print("[" + ANSIColor.CYAN + "SINGLE PLAYER" + ANSIColor.RESET + "]");
        print("[" + ANSIColor.CYAN + "MULTIPLAYER" + ANSIColor.RESET + "]");
        print("More options:");
        print("[" + ANSIColor.CYAN + "SETTINGS" + ANSIColor.RESET + "]");
        print("[" + ANSIColor.CYAN + "CREDITS" + ANSIColor.RESET + "]");
        print("==========================================");
    }

    @Override
    public void help() {
        print("Use \"sp\" to start a single player game.");
        print("Use \"mp\" to enter the multiplayer lobby.");
        print("Use \"options\" to see the game options.");
        print("Use \"credits\" to visualize the credits.");
        print("Use \"quit\" to quit the game.");
    }

    @Override
    public void getInput() {
        String command = input.nextLine().split(" ")[0];
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
        print(ANSIColor.YELLOW + "Selected Single Player Mode" + ANSIColor.RESET);
    }

    private void selectMultiplayerMode(){
        print(ANSIColor.YELLOW + "Selected Multi Player Mode" + ANSIColor.RESET);

        // TODO Move data somewhere else
        GameApplication.getInstance().connect("localhost", 42069);
        if(GameApplication.getInstance().isOnNetwork()){
            GameApplication.getInstance().setApplicationState(GameApplicationState.LOBBY);
        } else{
            show();
            error("Could not connect to the server...");
        }
    }
}
