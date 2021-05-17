package it.polimi.ingsw.network.server.components;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.model.ModelController;
import it.polimi.ingsw.controller.model.handlers.ModelControllerIOHandler;
import it.polimi.ingsw.controller.model.handlers.MPModelControllerIOHandler;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.NetworkPacketType;
import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;
import it.polimi.ingsw.network.common.sysmsg.GameLobbyMessage;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringJoiner;

/**
 * Framework class for a game room.
 */
public class GameRoom {

    private String roomId;              // room id
    private Object lock;                // concurrency-safe table lock
    private Hashtable<String, RemoteUser> users;
    private final ModelControllerIOHandler modelControllerIOHandler;
    private ModelController modelController;

    // TODO Add PINGING functionalities

    /**
     * Class constructor.
     */
    public GameRoom(String roomId) {
        this.roomId = roomId;

        this.lock = new Object();
        this.users = new Hashtable<String, RemoteUser>();
        this.modelControllerIOHandler = new MPModelControllerIOHandler(this);
        this.modelController = null;
    }

    /**
     * Room id getter.
     */
    public String getId(){
        return roomId;
    }

    public boolean gameInProgress(){
        return modelController != null;
    }

    /**
     * Add a user to the room.
     * @param nickname User's local (room) nickname.
     * @param user User reference.
     * @throws GameRoomException if the nickname is already taken.
     */
    public void addUser(String nickname, RemoteUser user) throws GameRoomException{
        // TODO controllare che non ci sia una partita in corso (FAI ECCEZIONE RESILIENZA O RICONNESSIONE MAMMA TI CHIAMO TRA UN ATTIMO CIAO)
        synchronized (lock) {
            if(users.containsKey(nickname)) throw new GameRoomException("The nickname \"" + nickname +"\" is already taken in this room.");
            users.put(nickname, user);
            user.assignRoom(roomId, nickname);
        }
        StringJoiner stringJoiner = new StringJoiner(" ");
        for(String k: users.keySet())stringJoiner.add(k);
        String messageContent = GameLobbyMessage.PLAYERS_IN_ROOM.addBody(stringJoiner.toString());
        broadcast(new NetworkPacket(NetworkPacketType.SYSTEM, messageContent));
    }

    // TODO if in game prevent from succeeding
    // TODO if room is empty after a user has left delete it.
    public boolean removeUser(String nickname){
        boolean result = false;
        synchronized (lock){
            if(users.containsKey(nickname)){
                users.remove(nickname);
                result = true;
            }
        }
        return result;
    }

    // TODO error handling?
    public void sendTo(String player, NetworkPacket packet){
        users.get(player).send(packet);
    }

    public void broadcast(NetworkPacket packet){
        for (String player: users.keySet()){
            users.get(player).send(packet);
        }
    }

    /**
     * Notify an action packet to the room's IOHandler
     * @param packet (ActionPacket) Network Packet to handle.
     */
    public void notify(NetworkPacket packet) {
        modelControllerIOHandler.notify(packet);
    }

    /**
     * Start the game
     * @return
     */
    public boolean startGame() {
        // TODO (X) non fare partire con solo un giocatore!
        /*if(users.size() < 2)
            broadcast(new NetworkPacket(NetworkPacketType.SYSTEM, ConnectionMessage.ERR.addBody("Can't start a multiplayer game by yourself!")));*/

        // TODO (X) controllare che non ci sia gia una partita in corso
        // if(gameInProgress) return;

        // Instantiate controller
        modelController = new ModelController(modelControllerIOHandler);
        // add players
        for(String player: users.keySet()) {
            modelController.addPlayer(player);
        }
        // randomize order
        modelController.getGame().shufflePlayers();
        // Send start
        String startMessage = GameLobbyMessage.START_ROOM.addBody("CIAO PIE");
        broadcast(new NetworkPacket(NetworkPacketType.SYSTEM, startMessage));
        // Start the game
        modelController.setupGame();
        // todo check ownership

        return true;
    }
}