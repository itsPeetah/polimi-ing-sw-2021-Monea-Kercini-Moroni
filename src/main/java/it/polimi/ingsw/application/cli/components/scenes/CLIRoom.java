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
        stdout("========= Game Room =========");
        stdout("You're into room: " + ANSIColor.GREEN + GameApplication.getInstance().getRoomName() + ANSIColor.RESET);
        stdout("Your nickname is: " + ANSIColor.GREEN + GameApplication.getInstance().getUserNickname() +ANSIColor.RESET);
        stdout("=============================");
    }

    @Override
    public void help() {
        stdout("Use command \"leave\" to leave the room before the game starts.");
        stdout("Use command \"start\" to start the game (must be room owner). "+ ANSIColor.RED + "(NOT YET IMPLEMENTED )" + ANSIColor.RESET);
    }

    @Override
    public void getInput() {
        String[] fields = input.nextLine().split(" ");
        switch (fields[0]){
            case "help":
                help();
                break;
            default:
                stdout("The command is not supported or has not been implemented yet");
                break;
        }
    }
}
