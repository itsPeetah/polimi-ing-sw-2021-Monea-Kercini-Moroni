package it.polimi.ingsw.network.protocols;

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

        String[] clientMessageFields;
        boolean disconnect = false;
        while (true){

            clientMessageFields = user.receiveMessageFields();
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
                    roomCreate(clientMessageFields[1], clientMessageFields[1]);
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
                    roomJoin(clientMessageFields[1], clientMessageFields[1]);
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
            // TODO add
        }
        // Launch server side listener
        else{
            // TODO add
        }

    }

    private void roomCreate(String roomName, String nickname) throws GameRoomException{

        // TODO Implement
    }

    private void roomJoin(String roomName, String nickname) throws GameRoomException{

        // TODO Implement

    }
}
