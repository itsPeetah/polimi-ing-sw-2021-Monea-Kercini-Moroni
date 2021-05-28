package it.polimi.ingsw.application.cli.components.scenes;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.NetworkPacketType;
import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;
import it.polimi.ingsw.network.common.sysmsg.GameLobbyMessage;

public class CLILobby extends CLIScene {

    private String nickname, roomName;

    public CLILobby() {
        super();
        nickname = GameApplication.getInstance().getUserNickname();
        roomName = GameApplication.getInstance().getRoomName();

        /*nickname = "Player";
        roomName = "Room_One";*/
    }

    @Override
    public void show() {
        clearConsole();
        println("========= Game Lobby =========");

        String nickLabel = nickname == null
                ? "[" + ANSIColor.RED + "CHOOSE NICKNAME" + ANSIColor.RESET + "]"
                : "[" + ANSIColor.GREEN + nickname + ANSIColor.RESET + "]";
        String roomLabel = roomName == null
                ? "[" + ANSIColor.RED + "CHOOSE GAME ROOM" + ANSIColor.RESET + "]"
                : "[" + ANSIColor.GREEN + roomName + ANSIColor.RESET + "]";

        println("Nickname:\t" + nickLabel);
        println("Game room:\t" + roomLabel);

        println("==============================");
    }

    @Override
    public void help() {
        println("Use \"nick <nickname>\" to choose your nickname");
        println("Use \"room <room name>\" to choose the room you want to join");
        println("Use either \"join\" or \"create\" to join or create a room on the server.");
        println("Use \"quit\" to quit the game.");
    }

    @Override
    public void execute(String command, String[] arguments) {
        switch (command) {
            case "help":
                help();
                break;
            case "nick":
                if (arguments.length < 1) println("Error: missing arguments. Retry.");
                else {
                    nickname = arguments[0];
                    show();
                    /*println("Set the nickname to " + nickname);*/
                }
                break;
            case "room":
                if (arguments.length < 1) println("Error: missing arguments. Retry.");
                else {
                    roomName = arguments[0];
                    show();
                    /*println("Set the room to " + roomName);*/
                }
                break;
            case "create":
                if (nickname == null || roomName == null)
                    error("Choose a nickname and a room name first. Retry.");
                else {
                    GameApplication.getInstance().setUserNickname(nickname);
                    GameApplication.getInstance().setRoomName(roomName);
                    makeChoice(GameLobbyMessage.CREATE_ROOM.addBody(roomName + " " + nickname));
                }
                break;
            case "join":
                if (nickname == null || roomName == null)
                    error("Choose a nickname and a room name first. Retry.");
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
                println("Error: invalid command.");
                break;
        }
    }

    private void makeChoice(String choice){
        println("Processing request, please wait.");
        NetworkPacket np = new NetworkPacket(NetworkPacketType.SYSTEM, choice);
        GameApplication.getInstance().setApplicationState(GameApplicationState.CONNECTING_TO_ROOM);
        GameApplication.getInstance().sendNetworkPacket(np);
    }
}
