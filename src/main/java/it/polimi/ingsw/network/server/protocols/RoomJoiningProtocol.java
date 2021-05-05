package it.polimi.ingsw.network.server.protocols;

import it.polimi.ingsw.network.server.GameServer;
import it.polimi.ingsw.network.server.components.GameRoomException;
import it.polimi.ingsw.network.server.components.RemoteUser;

public class RoomJoiningProtocol implements Runnable {

    private RemoteUser user;

    public RoomJoiningProtocol(RemoteUser user){
        this.user = user;
    }

    @Override
    public void run() {
        System.out.println("User " + user.getId() + " is now connected.");

        String clientMessage;
        String[] clientMessageFields;
        boolean disconnect = false;
        while (true){
            clientMessage = user.receiveMessage();
            System.out.println("[USER "+user.getId()+"] " + clientMessage);
            clientMessageFields = clientMessage.split("\\s+");
            // Handle "client wants to quit"
            if(clientMessageFields[0].equals("QUIT")){
                disconnect = true;
                break;
            }
            // Handle "not enough arguments"
            else if (clientMessageFields.length < 3){
                user.sendMessage("ERR Invalid request: missing arguments.");
            }
            // Handle "client wants to create a room". Usage: ROOMCREATE <room name> <nickname>
            else if(clientMessageFields[0].equals("ROOMCREATE")){
                // Success
                try{
                    roomCreate(clientMessageFields[1], clientMessageFields[2]);
                    System.out.println("User " + user.getId() + " created and joined room " + clientMessageFields[1] + " as " + clientMessageFields[2]);
                    user.sendMessage("OK Successfully created and joined room " + clientMessageFields[1] + " as " + clientMessageFields[2]);
                    break;
                }
                // Failure (room already exists)
                catch(GameRoomException ex){
                    user.sendMessage("ERR " + ex.getMessage());
                }
            }
            // Handle "client wants to join a room". Usage: ROOMJOIN <room name> <nickname>
            else if(clientMessageFields[0].equals("ROOMJOIN")){
                // Success
                try{
                    roomJoin(clientMessageFields[1], clientMessageFields[2]);
                    System.out.println("User " + user.getId() + " joined room " + clientMessageFields[1] + " as " + clientMessageFields[2]);
                    user.sendMessage("OK Successfully joined room " + clientMessageFields[1] + " as " + clientMessageFields[2]);
                    break;
                }
                // Failure (either room doesn't exist or nickname already taken)
                catch(GameRoomException ex){
                    user.sendMessage("ERR " + ex.getMessage());
                }
            }
            // Handle invalid request
            else{
                user.sendMessage("ERR Invalid request: the request was not valid.");
            }
        }

        // Disconnect
        if(disconnect){
            user.terminateConnection();
        }
        // Launch server side client listener
        else{
            new Thread(new ServerSideClientListener(user)).start();
        }

    }

    private void roomCreate(String roomName, String nickname) throws GameRoomException{
        GameServer.getInstance().getRoomTable().createRoom(roomName, nickname, user);
    }

    private void roomJoin(String roomName, String nickname) throws GameRoomException{
        GameServer.getInstance().getRoomTable().joinRoom(roomName, nickname, user);
    }
}
