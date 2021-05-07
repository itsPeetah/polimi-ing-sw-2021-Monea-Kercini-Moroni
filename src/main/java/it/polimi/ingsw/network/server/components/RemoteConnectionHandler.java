package it.polimi.ingsw.network.server.components;

import it.polimi.ingsw.network.common.ConnectionMessage;
import it.polimi.ingsw.network.common.ExSocket;
import it.polimi.ingsw.network.server.GameServer;
import it.polimi.ingsw.network.server.protocols.ConnectionSetupProtocol;
import it.polimi.ingsw.network.server.protocols.GameLobbyProtocol;
import it.polimi.ingsw.network.server.protocols.ServerSideClientListener;

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
        boolean roomJoined = new GameLobbyProtocol(user).joinOrCreateRoom();

        if(!roomJoined || !user.isInRoom()){
            closeConnection();
            return;
        }

        // Listen for player
        ServerSideClientListener listener = new ServerSideClientListener(user);
        listener.run();

        // When the listener is done close the connection.
        closeConnection();
    }

    private void closeConnection(){

        socket.send(ConnectionMessage.QUIT.addBody("Communication closed."));

        if(user == null) {
            socket.close();
        }
        else{
            user.terminateConnection();
        }
    }
}
