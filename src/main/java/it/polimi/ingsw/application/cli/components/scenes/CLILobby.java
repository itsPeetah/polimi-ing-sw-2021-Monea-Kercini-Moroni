package it.polimi.ingsw.application.cli.components.scenes;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.NetworkPacketType;
import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;
import it.polimi.ingsw.network.common.sysmsg.GameLobbyMessage;
import it.polimi.ingsw.network.common.sysmsg.ISystemMessage;

import java.util.Scanner;

public class CLILobby extends CLIScene {

    private String nickname, roomName;

    public CLILobby(String title) {
        super(title);
        nickname = GameApplication.getInstance().getUserNickname();
        roomName = GameApplication.getInstance().getRoomName();

        /*nickname = "Player";
        roomName = "Room_One";*/
    }

    @Override
    public void show() {
        clearConsole();
        stdout("========= Game Lobby =========");

        String nickLabel = nickname == null
                ? "[" + ANSIColor.RED + "CHOOSE NICKNAME" + ANSIColor.RESET + "]"
                : "[" + ANSIColor.GREEN + nickname + ANSIColor.RESET + "]";
        String roomLabel = roomName == null
                ? "[" + ANSIColor.RED + "CHOOSE GAME ROOM" + ANSIColor.RESET + "]"
                : "[" + ANSIColor.GREEN + roomName + ANSIColor.RESET + "]";

        stdout("Nickname:\t" + nickLabel);
        stdout("Game room:\t" + roomLabel);

        stdout("==============================");
    }

    @Override
    public void help() {
        stdout("Use \"nick <nickname>\" to choose your nickname");
        stdout("Use \"room <room name>\" to choose the room you want to join");
        stdout("Use either \"join\" or \"create\" to join or create a room on the server.");
        stdout("Use \"quit\" to quit the game.");
    }

    @Override
    public void getInput() {

        String in = input.nextLine();
        String[] fields = in.split(" ", 2);

        switch (fields[0]) {
            case "help":
                help();
                break;
            case "nick":
                if (fields.length < 2) stdout("Error: missing arguments. Retry.");
                else {
                    nickname = fields[1];
                    stdout("Set the nickname to " + nickname);
                }
                break;
            case "room":
                if (fields.length < 2) stdout("Error: missing arguments. Retry.");
                else {
                    roomName = fields[1];
                    stdout("Set the room to " + roomName);
                }
                break;
            case "create":
                if (nickname == null || roomName == null)
                    stdout("Error: choose a nickname and a room name first. Retry.");
                else {
                    GameApplication.getInstance().setUserNickname(nickname);
                    GameApplication.getInstance().setRoomName(roomName);
                    makeChoice(GameLobbyMessage.CREATE_ROOM.addBody(roomName + " " + nickname));
                }
                break;
            case "join":
                if (nickname == null || roomName == null)
                    stdout("Error: choose a nickname and a room name first. Retry.");
                else {
                    GameApplication.getInstance().setUserNickname(nickname);
                    GameApplication.getInstance().setRoomName(roomName);
                    makeChoice(GameLobbyMessage.JOIN_ROOM.addBody(roomName + " " + nickname));
                }
                break;
            case "quit":
                makeChoice(ConnectionMessage.QUIT.getCode());
                break;
            default:
                stdout("Error: invalid command.");
                break;
        }
    }

    private void makeChoice(String choice){
        stdout("Processing request, please wait.");
        NetworkPacket np = new NetworkPacket(NetworkPacketType.SYSTEM, choice);
        GameApplication.getInstance().setApplicationState(GameApplicationState.CONNECTING_TO_ROOM);
        GameApplication.getInstance().sendNetworkPacket(np);
    }
}
