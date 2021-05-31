package it.polimi.ingsw.network.server.protocols;

import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;
import it.polimi.ingsw.network.common.sysmsg.GameLobbyMessage;
import it.polimi.ingsw.network.server.GameServer;
import it.polimi.ingsw.network.server.components.GameRoomException;
import it.polimi.ingsw.network.server.components.RemoteUser;

public class GameLobbyProtocol {

    private RemoteUser user;
    private String clientMessage;
    private String[] clientMessageFields;

    public GameLobbyProtocol(RemoteUser user){
        this.user = user;
    }

    public boolean joinOrCreateRoom() {
        System.out.println("User " + user.getId() + " is now in the lobby.");

        user.sendSystemMessage(GameLobbyMessage.IN_LOBBY.getCode());

        while (true) {
            clientMessage = user.receiveSystemMessage();
            clientMessageFields = clientMessage.split(" ", 3);
            System.out.println("[USER " + user.getId() + "] " + clientMessage);

            // Client asks to close the connection.
            if (clientMessage == null || ConnectionMessage.QUIT.check(clientMessageFields[0])) {
                return false;
            }

            // Not enough arguments were provided.
            if (clientMessageFields.length < 3) {
                user.sendSystemMessage(ConnectionMessage.missingArgumentsError);
                continue;
            }

            // The user wants to create a room.
            // Usage: ROOMCREATE <room name> <nickname>
            if (GameLobbyMessage.CREATE_ROOM.check(clientMessageFields[0])) {
                // Success
                try {
                    roomCreate(clientMessageFields[1], clientMessageFields[2]);
                    System.out.println("User " + user.getId() + " created and joined room " + clientMessageFields[1] + " as " + clientMessageFields[2]);
                    user.sendSystemMessage(
                            ConnectionMessage.OK.addBody("Successfully created and joined room " + clientMessageFields[1] + " as " + clientMessageFields[2])
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
                // Success
                try {
                    roomJoin(clientMessageFields[1], clientMessageFields[2]);
                    System.out.println("User " + user.getId() + " joined room " + clientMessageFields[1] + " as " + clientMessageFields[2]);
                    user.sendSystemMessage(
                            ConnectionMessage.OK.addBody("Successfully joined room " + clientMessageFields[1] + " as " + clientMessageFields[2])
                    );
                    return true;
                }
                // Failure (either room doesn't exist or nickname already taken)
                catch (GameRoomException ex) {
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
}
