package it.polimi.ingsw.application.cli.components.scenes;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.NetworkPacketType;
import it.polimi.ingsw.network.common.SystemMessage;

public class CLIRoom extends CLIScene {

    public CLIRoom() {
        super();
    }

    @Override
    public void show() {
        println("========= Game Room =========");
        println("You're into room: " + ANSIColor.GREEN + GameApplication.getInstance().getRoomName() + ANSIColor.RESET);
        println("Your nickname is: " + ANSIColor.GREEN + GameApplication.getInstance().getUserNickname() +ANSIColor.RESET);
        println("=============================");
    }

    @Override
    public void help() {
        println("Use command \"leave\" to leave the room before the game starts.");
        println("Use command \"start\" to start the game.");
    }



    @Override
    public void execute(String command, String[] arguments) {

        // Skip command if the game has started.
        if(GameApplication.getInstance().gameExists())
            return;

        switch (command){
            case "help":
                help();
                break;
            case "start":
                startGame();
                break;
            case "leave":
                leaveRoom();
                break;
            default:
                println("The command is not supported or has not been implemented yet");
                break;
        }
    }

    private void startGame(){
        GameApplication.getInstance().setApplicationState(GameApplicationState.WAITING);
        String messageContent = SystemMessage.START_ROOM.addBody(GameApplication.getInstance().getRoomName() + " " + GameApplication.getInstance().getUserNickname());
        NetworkPacket np = new NetworkPacket(NetworkPacketType.SYSTEM, messageContent);
        GameApplication.getInstance().sendNetworkPacket(np);
    }

    private void leaveRoom(){
        GameApplication.getInstance().setApplicationState(GameApplicationState.WAITING);
        NetworkPacket np = NetworkPacket.buildSystemMessagePacket(SystemMessage.LEAVE_ROOM.getCode());
        GameApplication.getInstance().sendNetworkPacket(np);
    }

}
