package it.polimi.ingsw.application.cli.components.scenes;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.NetworkPacketType;
import it.polimi.ingsw.network.common.SystemMessage;

public class CLILobby extends CLIScene {

    private String nickname;
    /*private String roomName;*/

    public CLILobby() {
        super();
        nickname = GameApplication.getInstance().getUserNickname();
        /*roomName = GameApplication.getInstance().getRoomName();*/
    }

    @Override
    public void show() {
        clearConsole();
        println("========= Game Lobby =========");

        String nickLabel = nickname == null
                ? "[" + ANSIColor.RED + "CHOOSE NICKNAME" + ANSIColor.RESET + "]"
                : "[" + ANSIColor.GREEN + nickname + ANSIColor.RESET + "]";
        /*String roomLabel = roomName == null
                ? "[" + ANSIColor.RED + "CHOOSE GAME ROOM" + ANSIColor.RESET + "]"
                : "[" + ANSIColor.GREEN + roomName + ANSIColor.RESET + "]";*/

        println("Nickname:\t" + nickLabel);
        /*println("Game room:\t" + roomLabel);*/

        println("==============================");
        help();
        println("==============================");
    }

    @Override
    public void help() {
        println("Use \"nick <nickname>\" to choose your nickname");
        /*println("Use \"room <room name>\" to choose the room you want to join");*/
        /*println("Use either \"join\" or \"create\" to join or create a room on the server.");*/
        println("User command \"create <room_name> <max_players>\" to create a new room on the server:");
        println("User command \"join <room_name>\" to join an existing room on the server.");
        println("Use quickstart [<nickname>] to join a random room or create one if none are available.");
        println("\t\tIf you haven't already chosen a nickname the argument is mandatory.");
        println("\t\tIf no available rooms are found on the server, the new one will have a 4 player capacity by default.");
        println("If you have disconnected mid-game, try using command \"rejoin\" to rejoin the previous game.");
        println("Use \"quit\" to quit the game.");
    }

    @Override
    public void execute(String command, String[] arguments) {
        switch (command) {
            case "h":
            case "help":
                help();
                break;
            case "n":
            case "nick":
                if (arguments.length < 1) println("Error: missing arguments. Retry.");
                else {
                    nickname = arguments[0];
                    show();
                    /*println("Set the nickname to " + nickname);*/
                }
                break;
            case "c":
            case "create":
                if (nickname == null)
                    error("Choose a nickname and a room name first. Retry.");
                else if(arguments == null || arguments.length < 2)
                    error("Missing arguments. Usage: create <room_name> <max_players>.");
                else if(!"1234".contains(arguments[1]))
                    error("Invalid max_players argument value.");
                else {
                    /*GameApplication.getInstance().setUserNickname(nickname);
                    GameApplication.getInstance().setRoomName(roomName);*/
                    makeChoice(SystemMessage.CREATE_ROOM.addBody(arguments[0] + " " + nickname + " " + Integer.parseInt(arguments[1])));
                }
                break;
            case "j":
            case "join":
                if (nickname == null)
                    error("Choose a nickname and a room name first. Retry.");
                else if(arguments == null || arguments.length < 1)
                    error("Missing arguments. Usage: join <room_name>");
                else {
                    /*GameApplication.getInstance().setUserNickname(nickname);
                    GameApplication.getInstance().setRoomName(roomName);*/
                    makeChoice(SystemMessage.JOIN_ROOM.addBody(arguments[0] + " " + nickname));
                }
                break;
            case "r":
            case "rejoin":
                makeChoice(SystemMessage.REJOIN_ROOM.addBody(GameApplication.getInstance().getUserId()));
                break;
            case "qs":
            case "quickstart":
                if(arguments != null && arguments.length > 0)
                    nickname = arguments[0];
                show();
                if(nickname == null) error("You need to choose a nickname first.");
                else makeChoice(SystemMessage.QUICK_START.addBody(nickname));
                break;
            case "quit":
                makeChoice(SystemMessage.QUIT.getCode());
                break;
            default:
                println("Error: invalid command.");
                break;
        }
    }

    private void makeChoice(String choice){
        println("Processing request, please wait.");
        NetworkPacket np = new NetworkPacket(NetworkPacketType.SYSTEM, choice);
        GameApplication.getInstance().setApplicationState(GameApplicationState.WAITING);
        GameApplication.getInstance().sendNetworkPacket(np);
    }
}
