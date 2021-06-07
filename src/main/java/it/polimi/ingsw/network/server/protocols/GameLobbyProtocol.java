package it.polimi.ingsw.network.server.protocols;

import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.network.common.SystemMessage;
import it.polimi.ingsw.network.server.GameServer;
import it.polimi.ingsw.network.server.components.GameRoomException;
import it.polimi.ingsw.network.server.components.RemoteUser;

public class GameLobbyProtocol {

    private RemoteUser user;
    private String clientMessage;
    private String[] clientMessageFields;

    public GameLobbyProtocol(RemoteUser user, boolean checkCanReconnect){
        this.user = user;
    }

    public boolean joinOrCreateRoom() {
        System.out.println("User " + user.getId() + " is now in the lobby.");

        user.sendSystemMessage(SystemMessage.IN_LOBBY.getCode());

        while (true) {
            clientMessage = user.receiveSystemMessage();

            if(clientMessage == null) return false;

            clientMessageFields = clientMessage.split(" ");

            // Client asks to close the connection.
            if (clientMessage == null || SystemMessage.QUIT.check(clientMessageFields[0])) {
                return false;
            }

            if(SystemMessage.PING.check(clientMessageFields[0])) {
                user.respondedToPing();
                continue;
            }

            System.out.println("[Debug] User " + user.getId() +": "+ clientMessage);

            // The user wants to create a room.
            // Usage: ROOMCREATE <room name> <nickname>
            if (SystemMessage.CREATE_ROOM.check(clientMessageFields[0])) {

                if (clientMessageFields.length < 3) {
                    user.sendSystemMessage(SystemMessage.missingArgumentsWhileJoiningError);
                    continue;
                }
                // Success
                try {
                    int maxPlayers;
                    if(clientMessageFields.length < 4) maxPlayers = 4;
                    else maxPlayers = Integer.parseInt(clientMessageFields[3]);

                    roomCreate(clientMessageFields[1], clientMessageFields[2], maxPlayers);
                    System.out.println(ANSIColor.CYAN + "User " + user.getId() + " created and joined room " +
                            clientMessageFields[1] + " as " + clientMessageFields[2] + ANSIColor.RESET);
                    user.sendSystemMessage(
                            SystemMessage.IN_ROOM.addBody(clientMessageFields[1] + " " + clientMessageFields[2])
                    );
                    return true;
                }
                // Failure (room already exists)
                catch (GameRoomException ex) {
                    user.sendSystemMessage(SystemMessage.CANT_JOIN.addBody(ex.getMessage()));
                    continue;
                } catch (NumberFormatException ex){
                    user.sendSystemMessage(SystemMessage.CANT_JOIN.addBody("Invalid \"max_players\" value."));
                }
            }

            // The user wants to join a room.
            // Usage: ROOMJOIN <room name> <nickname>
            if (SystemMessage.JOIN_ROOM.check(clientMessageFields[0])) {
                if (clientMessageFields.length < 3) {
                    user.sendSystemMessage(SystemMessage.missingArgumentsWhileJoiningError);
                    continue;
                }
                // Success
                try {
                    roomJoin(clientMessageFields[1], clientMessageFields[2]);
                    System.out.println(ANSIColor.CYAN + "User " + user.getId() + " joined room " +
                            clientMessageFields[1] + " as " + clientMessageFields[2] + ANSIColor.RESET);
                    user.sendSystemMessage(
                            SystemMessage.IN_ROOM.addBody(clientMessageFields[1] + " " + clientMessageFields[2])
                    );
                    return true;
                }
                // Failure (either room doesn't exist or nickname already taken)
                catch (GameRoomException ex) {
                    user.sendSystemMessage(SystemMessage.CANT_JOIN.addBody(ex.getMessage()));
                    continue;
                }
            }

            // THE user wants to rejoin a game.
            // Usage: ROOM_R <id>
            if(SystemMessage.REJOIN_ROOM.check(clientMessageFields[0])){
                // Success
                try{
                    String[] roomAndNickname = rejoinRoom(clientMessageFields[1]);
                    user.sendSystemMessage(
                            SystemMessage.IN_ROOM.addBody(roomAndNickname[0] + " " + roomAndNickname[1])
                    );
                    GameServer.getInstance().getRoomTable().getRoom(roomAndNickname[0]).rejoinUser(roomAndNickname[1], user);
                    return true;
                }
                // Failure: no game to rejoin available
                catch (GameRoomException ex){
                    System.out.println(ex.getMessage());
                    user.sendSystemMessage(SystemMessage.CANT_JOIN.addBody(ex.getMessage()));
                    continue;
                }

            }
            // The user wants to quick start
            if(SystemMessage.QUICK_START.check(clientMessageFields[1])){
                user.sendSystemMessage(SystemMessage.invalidLobbyRequestError);
                // TODO Check if there are any available rooms (available = not started, current_players < max_players) => try join
                // TODO If not, create a new room -> Room name <- Room{amountOfRoomsOnTheServer}
            }

            // If the code reaches here the request is not supported.
            user.sendSystemMessage(SystemMessage.invalidLobbyRequestError);
        }
    }

    private void roomCreate(String roomName, String nickname, int maxPlayers) throws GameRoomException{
        GameServer.getInstance().getRoomTable().createRoom(roomName, nickname, user, maxPlayers);
    }

    private void roomJoin(String roomName, String nickname) throws GameRoomException{
        GameServer.getInstance().getRoomTable().joinRoom(roomName, nickname, user);
    }

    private String[] rejoinRoom(String userId) throws GameRoomException {
        // Check if there is a room with an ongoing game that can accept this id.
        String[] result = GameServer.getInstance().getRoomTable().findRoomToRejoin(userId);
        if(result == null) throw new GameRoomException("There is no game you can rejoin at the moment.");
        return result;
    }
}
