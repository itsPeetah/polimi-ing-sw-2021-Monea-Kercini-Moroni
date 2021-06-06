package it.polimi.ingsw.network.server.components;

import it.polimi.ingsw.network.common.ExSocket;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.SystemMessage;
import it.polimi.ingsw.network.server.GameServer;

/**
 * Rep class for a remote user.
 */
public class RemoteUser {

    private static final int maxMissedPings = 1;

    private final String id;    // Server assigned user id
    private ExSocket socket;    // Socket

    private String roomId, nickname;    // User's game room references

    private int missedPings;
    private boolean pingResponse, wasPinged;

    /**
     * Class constructor.
     */
    public RemoteUser(String id, ExSocket socket){
        this.id = id;
        this.socket = socket;
        this.roomId = null;
        this.nickname = null;
        this.missedPings = 0;
        this.pingResponse = this.wasPinged = false;
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

    public void send(NetworkPacket packet){
        socket.send(packet);
    }

    public NetworkPacket receive(){
        return socket.receive();
    }

    /**
     * Send a system message via the socket.
     */
    public void sendSystemMessage(String message){
        socket.sendSystemMessage(message);
    }

    /**
     * Receive a system message from the socket.
     */
    public String receiveSystemMessage(){
        return  socket.receiveSystemMessage();
    }

    /**
     * Close the connection with the user.
     */
    public void terminateConnection() {
        if(isInRoom()) leaveCurrentRoom();
        socket.sendSystemMessage(SystemMessage.QUIT.addBody("Communication closed."));
        socket.close();
        GameServer.getInstance().getUserTable().removeUser(id);
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

    public void leaveCurrentRoom(){
        if(isInRoom()){
            GameServer.getInstance().getRoomTable().getRoom(roomId).removeUser(nickname);
            this.assignRoom(null, null);
        }
        sendSystemMessage(SystemMessage.IN_LOBBY.getCode());
    }

    public int getMissedPings() {
        return missedPings;
    }

    public void ping(){
        sendSystemMessage(SystemMessage.PING.getCode());
        this.wasPinged = true;
    }

    public void respondedToPing(){
        pingResponse = true;
    }

    public boolean checkPingResponse() {
        missedPings = pingResponse && wasPinged ? 0 : missedPings + 1;
        if(missedPings > maxMissedPings){
            return false;
        }
        wasPinged = false;
        pingResponse = false;
        return true;
    }
}
