package it.polimi.ingsw.network.server.components;

import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;
import it.polimi.ingsw.network.common.ExSocket;
import it.polimi.ingsw.network.server.GameServer;
import it.polimi.ingsw.network.server.protocols.*;

/**
 * Runnable class to handle the connection with one remote user.
 */
public class RemoteConnectionHandler implements Runnable {

    private ExSocket socket;    // user's socket
    private RemoteUser user;    // user reference

    /**
     * Class constructor.
     * @param socket The socket the connection is coming from.
     */
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

        // Loop handles the player possibly leaving a room before the game has started
        while(true) {
            // Game Lobby protocol
            boolean roomJoined = new GameLobbyProtocol(user).joinOrCreateRoom();

            if (!roomJoined || !user.isInRoom()) break;

            // Listen for player
            ServerSideClientListener listener = new ServerSideClientListener(user);
            boolean backToLobby = listener.run();

            if(!backToLobby) break;
        }

        // When the listener is done close the connection.
        closeConnection();
    }

    /**
     * Handle the closing of the connection.
     */
    private void closeConnection(){

        if(user == null) {
            socket.sendSystemMessage(ConnectionMessage.QUIT.addBody("Communication closed."));
            socket.close();
        }
        else{
            user.terminateConnection();
        }
    }
}
