package it.polimi.ingsw.network.server.components;

import it.polimi.ingsw.network.common.ConnectionMessage;
import it.polimi.ingsw.network.common.ExSocket;
import it.polimi.ingsw.network.server.GameServer;
import it.polimi.ingsw.network.server.protocols.ConnectionSetupProtocol;
import it.polimi.ingsw.network.server.protocols.RoomJoiningProtocol;

public class RemoteConnectionHandler implements Runnable {

    private ExSocket socket;
    private RemoteUser user;

    public RemoteConnectionHandler(ExSocket socket){
        this.socket = socket;
        this.user = null;
    }

    @Override
    public void run() {

        // Setup connection
        String userId = new ConnectionSetupProtocol(socket).getUserId();

        if(userId == null){
            closeConnection();
            return;
        }

        // Setup user
        user = new RemoteUser(userId, socket);
        GameServer.getInstance().getUserTable().add(user);

        // Game Lobby protocol
        boolean roomJoined = new RoomJoiningProtocol(user).joinOrCreateRoom();

        if(!roomJoined || !user.isInRoom()){
            closeConnection();
            return;
        }

        // Listen for player

    }

    private void closeConnection(){

        socket.send(ConnectionMessage.QUIT.addBody("Communication with server closed."));

        if(user == null) {
            socket.close();
        }
        else{
            user.terminateConnection();
        }
    }
}
