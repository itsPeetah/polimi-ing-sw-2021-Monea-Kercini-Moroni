package it.polimi.ingsw.application.cli.components.scenes;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.NetworkPacketType;
import it.polimi.ingsw.network.common.sysmsg.GameLobbyMessage;

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
        stdout("========= Game Lobby =========");
        stdout("Use \"nick <nickname>\" to choose your nickname");
        stdout("Use \"room <room name>\" to choose the room you want to join");
        stdout("Use either \"join\" or \"create\" to join or create a room on the server.");
        stdout("==============================");
    }

    @Override
    public void getInput() {
        Scanner in = new Scanner(System.in);
        String[] fields;
        String output = "";
        boolean done = false;
        while(!done) {
            fields = in.nextLine().split(" ");
            switch (fields[0]) {
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
                case "join":
                    if (nickname == null || roomName == null)
                        stdout("Error: choose a nickname and a room name first. Retry.");
                    else {
                        output = GameLobbyMessage.JOIN_ROOM.addBody(roomName + " " + nickname);
                        done = true;
                    }
                    break;
                case "create":
                    if (nickname == null || roomName == null)
                        stdout("Error: choose a nickname and a room name first. Retry.");
                    else{
                        output = GameLobbyMessage.CREATE_ROOM.addBody(roomName + " " + nickname);
                        done = true;
                    }
                    break;
            }
        }
        GameApplication.getInstance().setApplicationState(GameApplicationState.CONNECTING_TO_ROOM);
        GameApplication.getInstance().sendNetworkPacket(new NetworkPacket(NetworkPacketType.SYSTEM, output));
    }
}
