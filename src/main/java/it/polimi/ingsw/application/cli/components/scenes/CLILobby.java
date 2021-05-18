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
        print("========= Game Lobby =========");

        String nickLabel = nickname == null
                ? "[" + ANSIColor.RED + "CHOOSE NICKNAME" + ANSIColor.RESET + "]"
                : "[" + ANSIColor.GREEN + nickname + ANSIColor.RESET + "]";
        String roomLabel = roomName == null
                ? "[" + ANSIColor.RED + "CHOOSE GAME ROOM" + ANSIColor.RESET + "]"
                : "[" + ANSIColor.GREEN + roomName + ANSIColor.RESET + "]";

        print("Nickname:\t" + nickLabel);
        print("Game room:\t" + roomLabel);

        print("==============================");
    }

    @Override
    public void help() {
        print("Use \"nick <nickname>\" to choose your nickname");
        print("Use \"room <room name>\" to choose the room you want to join");
        print("Use either \"join\" or \"create\" to join or create a room on the server.");
        print("Use \"quit\" to quit the game.");
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
                if (fields.length < 2) print("Error: missing arguments. Retry.");
                else {
                    nickname = fields[1];
                    show();
                    print("Set the nickname to " + nickname);
                }
                break;
            case "room":
                if (fields.length < 2) print("Error: missing arguments. Retry.");
                else {
                    roomName = fields[1];
                    show();
                    print("Set the room to " + roomName);
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
                print("Error: invalid command.");
                break;
        }
    }

    private void makeChoice(String choice){
        print("Processing request, please wait.");
        NetworkPacket np = new NetworkPacket(NetworkPacketType.SYSTEM, choice);
        GameApplication.getInstance().setApplicationState(GameApplicationState.CONNECTING_TO_ROOM);
        GameApplication.getInstance().sendNetworkPacket(np);
    }
}
