package it.polimi.ingsw.network.server.protocols;

import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;
import it.polimi.ingsw.network.common.sysmsg.GameLobbyMessage;
import it.polimi.ingsw.network.server.GameServer;
import it.polimi.ingsw.network.server.components.GameRoom;
import it.polimi.ingsw.network.server.components.GameRoomException;
import it.polimi.ingsw.network.server.components.RemoteUser;
import it.polimi.ingsw.network.server.components.RoomTable;

public class GameLobbyProtocol {

    private RemoteUser user;
    private String clientMessage;
    private String[] clientMessageFields;

    public GameLobbyProtocol(RemoteUser user, boolean checkCanReconnect){
        this.user = user;
    }

    public boolean joinOrCreateRoom() {
        System.out.println("User " + user.getId() + " is now in the lobby.");

        user.sendSystemMessage(GameLobbyMessage.IN_LOBBY.getCode());

        while (true) {
            clientMessage = user.receiveSystemMessage();
            clientMessageFields = clientMessage.split(" ", 3);

            // Client asks to close the connection.
            if (clientMessage == null || ConnectionMessage.QUIT.check(clientMessageFields[0])) {
                return false;
            }

            if(ConnectionMessage.PING.check(clientMessageFields[0]))
                user.respondedToPing();

            System.out.println("[Debug] User " + user.getId() +": "+ clientMessage);

            // The user wants to create a room.
            // Usage: ROOMCREATE <room name> <nickname>
            if (GameLobbyMessage.CREATE_ROOM.check(clientMessageFields[0])) {
                if (clientMessageFields.length < 3) {
                    user.sendSystemMessage(ConnectionMessage.missingArgumentsError);
                    continue;
                }
                // Success
                try {
                    roomCreate(clientMessageFields[1], clientMessageFields[2]);
                    System.out.println(ANSIColor.CYAN + "User " + user.getId() + " created and joined room " +
                            clientMessageFields[1] + " as " + clientMessageFields[2] + ANSIColor.RESET);
                    user.sendSystemMessage(
                            ConnectionMessage.OK.addBody(clientMessageFields[1] + " " + clientMessageFields[2])
                    );
                    return true;
                }
                // Failure (room already exists)
                catch (GameRoomException ex) {
                    user.sendSystemMessage(ConnectionMessage.ERR.addBody(ex.getMessage()));
                    continue;
                }
            }

            // The user wants to join a room.
            // Usage: ROOMJOIN <room name> <nickname>
            if (GameLobbyMessage.JOIN_ROOM.check(clientMessageFields[0])) {
                if (clientMessageFields.length < 3) {
                    user.sendSystemMessage(ConnectionMessage.missingArgumentsError);
                    continue;
                }
                // Success
                try {
                    roomJoin(clientMessageFields[1], clientMessageFields[2]);
                    System.out.println(ANSIColor.CYAN + "User " + user.getId() + " joined room " +
                            clientMessageFields[1] + " as " + clientMessageFields[2] + ANSIColor.RESET);
                    user.sendSystemMessage(
                            ConnectionMessage.OK.addBody(clientMessageFields[1] + " " + clientMessageFields[2])
                    );
                    return true;
                }
                // Failure (either room doesn't exist or nickname already taken)
                catch (GameRoomException ex) {
                    user.sendSystemMessage(ConnectionMessage.ERR.addBody(ex.getMessage()));
                    continue;
                }
            }

            // THE user wants to rejoin a game.
            // Usage: ROOM_R <id>
            if(GameLobbyMessage.REJOIN_ROOM.check(clientMessageFields[0])){
                // Success
                try{
                    String[] roomAndNickname = rejoinRoom(clientMessageFields[1]);
                    System.out.println(ANSIColor.GREEN +"User " + user.getId() + " rejoined room " +
                            roomAndNickname[0] + " as " + roomAndNickname[1] + ANSIColor.RESET);
                    user.sendSystemMessage(
                            ConnectionMessage.OK.addBody(roomAndNickname[0] + " " + roomAndNickname[1])
                    );
                    GameServer.getInstance().getRoomTable().getRoom(roomAndNickname[0]).rejoinUser(roomAndNickname[1], user);
                    return true;
                }
                // Failure: no game to rejoin available
                catch (GameRoomException ex){
                    user.sendSystemMessage(ConnectionMessage.ERR.addBody(ex.getMessage()));
                    continue;
                }

            }

            // If the code reaches here the request is not supported.
            user.sendSystemMessage(ConnectionMessage.invalidRequestError);
        }
    }

    private void roomCreate(String roomName, String nickname) throws GameRoomException{
        GameServer.getInstance().getRoomTable().createRoom(roomName, nickname, user);
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
