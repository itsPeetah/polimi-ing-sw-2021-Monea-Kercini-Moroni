package it.polimi.ingsw.application.cli.components.scenes;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.NetworkPacketType;
import it.polimi.ingsw.network.common.sysmsg.GameLobbyMessage;

public class CLIRoom extends CLIScene {

    public CLIRoom() {
        super();
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
        print("Use command \"start\" to start the game.");
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
            case "start":
                startGame();
                break;
            default:
                print("The command is not supported or has not been implemented yet");
                break;
        }
    }

    private void startGame(){
        GameApplication.getInstance().setApplicationState(GameApplicationState.WAITING);
        String messageContent = GameLobbyMessage.START_ROOM.addBody(GameApplication.getInstance().getRoomName() + " " + GameApplication.getInstance().getUserNickname());
        NetworkPacket np = new NetworkPacket(NetworkPacketType.SYSTEM, messageContent);
        GameApplication.getInstance().sendNetworkPacket(np);
    }

}
