package it.polimi.ingsw.application.cli.components.scenes;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.application.common.GameApplication;

public class CLIRoom extends CLIScene {

    public CLIRoom(String title) {
        super(title);
    }

    @Override
    public void show() {
        print("========= Game Room =========");
        print("You're into room: " + ANSIColor.GREEN + GameApplication.getInstance().getRoomName() + ANSIColor.RESET);
        print("Your nickname is: " + ANSIColor.GREEN + GameApplication.getInstance().getUserNickname() +ANSIColor.RESET);
        print("=============================");
    }

    @Override
    public void help() {
        print("Use command \"leave\" to leave the room before the game starts.");
        print("Use command \"start\" to start the game (must be room owner). "+ ANSIColor.RED + "(NOT YET IMPLEMENTED)" + ANSIColor.RESET);
    }

    @Override
    public void getInput() {
        String[] fields = input.nextLine().split(" ");

        // Skip command if the game has started.
        if(GameApplication.getInstance().gameExists())
            return;

        switch (fields[0]){
            case "help":
                help();
                break;
            default:
                print("The command is not supported or has not been implemented yet");
                break;
        }
    }
}
