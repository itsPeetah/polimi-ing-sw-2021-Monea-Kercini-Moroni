package it.polimi.ingsw.application.cli.components.scenes;

import it.polimi.ingsw.application.cli.components.CLIScene;
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
        nickname = null;
        roomName = null;
    }

    @Override
    public void show() {
        clearConsole();
        stdout("========= Game Lobby =========");

        String nickLabel = nickname == null ? "[\u001B[31mCHOOSE NICKNAME\u001B[0m]" : "[\u001B[32m" + nickname + "\u001B[0m]";
        String roomLabel = roomName == null ? "[\u001B[31mCHOOSE ROOM NAME\u001B[0m]" : "[\u001B[32m" + roomName + "\u001B[0m]";

        stdout("Nickname: " + nickLabel);
        stdout("Game room: " + roomLabel);

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

        switch (fields[0]){
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
                else makeChoice(GameLobbyMessage.CREATE_ROOM.addBody(roomName + " " + nickname));
                break;
            case "join":
                if (nickname == null || roomName == null)
                    stdout("Error: choose a nickname and a room name first. Retry.");
                else makeChoice(GameLobbyMessage.JOIN_ROOM.addBody(roomName + " " + nickname));
                break;
            case "quit":
                makeChoice(ConnectionMessage.QUIT.getCode());
                break;
        }
    }

    private void makeChoice(String choice){

        NetworkPacket np = new NetworkPacket(NetworkPacketType.SYSTEM, choice);
        GameApplication.getInstance().setApplicationState(GameApplicationState.CONNECTING_TO_ROOM);
        GameApplication.getInstance().sendNetworkPacket(np);
    }
}
