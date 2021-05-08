package it.polimi.ingsw.network.server.components;

import it.polimi.ingsw.network.common.ExSocket;
import it.polimi.ingsw.network.server.GameServer;

/**
 * Rep class for a remote user.
 */
public class RemoteUser {

    private final String id;    // Server assigned user id
    private ExSocket socket;    // Socket

    private String roomId, nickname;    // User's game room references

    /**
     * Class constructor.
     */
    public RemoteUser(String id, ExSocket socket){
        this.id = id;
        this.socket = socket;
        this.roomId = null;
        this.nickname = null;
    }

    /**
     * User ID getter.
     */
    public String getId() { return id; }

    /**
     * Socket getter.
     */
    public ExSocket getSocket() { return socket; }

    /**
     * Is the player in a game room?
     */
    public boolean isInRoom(){ return roomId != null && nickname != null; }

    /**
     * User's current room getter.
     */
    public GameRoom getRoom(){ return GameServer.getInstance().getRoomTable().getRoom(roomId); }

    /**
     * Send a message via the socket.
     */
    public void sendMessage(String message){
        socket.sendSysMsg(message);
    }

    /**
     * Receive a message from the socket.
     */
    public String receiveMessage(){
        return  socket.receiveSysMsg();
    }

    /**
     * Receive a message from the socket already split at each white space.
     */
    public String[] receiveMessageFields(){
        return receiveMessage().split("\\s+");
    }

    /**
     * Close the connection with the user.
     */
    public void terminateConnection() {
        socket.close();

    }

    /**
     * Assign a game room to the user.
     * @param roomId Name of the room the user will be in.
     * @param nickname Room nickname of the user.
     */
    public void assignRoom(String roomId, String nickname){
        this.roomId = roomId;
        this.nickname = nickname;
    }


}
