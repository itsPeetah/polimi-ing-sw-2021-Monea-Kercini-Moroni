package it.polimi.ingsw.application.common;

import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.application.gui.GUIChat;
import it.polimi.ingsw.application.gui.GUIScene;
import it.polimi.ingsw.application.gui.GUIUtility;
import it.polimi.ingsw.application.gui.scenes.GUIMPRoom;
import it.polimi.ingsw.controller.view.GameController;
import it.polimi.ingsw.controller.view.handlers.GameControllerIOHandler;
import it.polimi.ingsw.network.client.GameClient;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.NetworkPacketType;
import it.polimi.ingsw.network.common.SystemMessage;
import it.polimi.ingsw.view.data.GameData;
import javafx.application.Platform;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class GameApplication {
    private static final String DEFAULT_SP_NICKNAME = "Player";

    // Output mode (CLI/GUI)
    private static GameApplicationMode outputMode = GameApplicationMode.CLI;
    public static void setOutputMode(GameApplicationMode mode) { outputMode = mode; }
    public static GameApplicationMode getOutputMode() { return outputMode; }

    // Singleton
    private static GameApplication instance;
    public static GameApplication getInstance() {
        if(instance == null) instance = new GameApplication();
        return instance;
    }

    // State
    private final AtomicReference<GameApplicationState> applicationState;
    private String userId;
    private final AtomicReference<String> userNickname, roomName;
    private final AtomicBoolean isRunning;

    // Components
    private GameClient networkClient;
    private final AtomicReference<GameController> gameController;

    // Lobby
    private final List<String> roomPlayers;

    /**
     * Class constructor
     */
    public GameApplication(){
        this.isRunning = new AtomicBoolean(false);
        this.applicationState = new AtomicReference<>(GameApplicationState.STARTUP);
        this.networkClient = null;
        this.gameController = new AtomicReference<>(null);

        this.userId = null;
        this.userNickname = new AtomicReference<>(DEFAULT_SP_NICKNAME);
        this.roomName = new AtomicReference<>();

        this.roomPlayers = Collections.synchronizedList(new ArrayList<>());

        instance = this;
    }

    // Application state

    /**
     * Application state getter.
     */
    public GameApplicationState getApplicationState(){
        return applicationState.get();
    }

    /**
     * Application state setter.
     */
    public void setApplicationState(GameApplicationState state){
        applicationState.set(state);
    }

    /**
     * Is running getter.
     */
    public boolean isRunning(){ return isRunning.get(); }

    /**
     * Has the networking been initialized?
     */
    public boolean isOnNetwork() {return networkClient != null && networkClient.isConnected(); }


    /**
     * Has the game been set up?
     */
    public boolean gameExists(){return gameController.get() != null; }

    /**
     * Game controller IO Handler getter.
     * @throws NullPointerException if the game has not been initialized.
     */
    public GameControllerIOHandler getGameControllerIO() throws NullPointerException{
        if(gameController.get() == null) throw new NullPointerException();
        return gameController.get().getGameControllerIOHandler();
    }

    /**
     * Get game controller.
     * @return game controller.
     */
    public GameController getGameController() {
        return gameController.get();
    }

    /**
     * Get the user's ID.
     * @return ID of the user.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Set the user's ID.
     * @param userId ID of the user.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Get the user's nickname.
     * @return nickname of the user.
     */
    public String getUserNickname() {
        return userNickname.get();
    }

    /**
     * Set the user's nickname.
     * @param userNickname nickname of the user.
     */
    public void setUserNickname(String userNickname) {
        this.userNickname.set(userNickname);
    }

    /**
     * Get the current room name.
     * @return name of the current room of the user.
     */
    public String getRoomName() {
        return roomName.get();
    }

    /**
     * Set the current room name.
     * @param roomName name of the current room of the user.
     */
    public void setRoomName(String roomName) {
        String currentRoom = this.roomName.get();
        if(!this.roomName.compareAndSet(currentRoom, roomName)) setRoomName(roomName);
    }

    /**
     * Set the players of the room.
     * @param stringOfPlayers <code>String</code> containing the players of the room.
     */
    public synchronized void setRoomPlayers(String stringOfPlayers) {
        List<String> newList = Arrays.asList(stringOfPlayers.split(" ").clone());
        if(outputMode == GameApplicationMode.GUI) {
            Platform.runLater(() -> {
                GUIMPRoom.observablePlayersList.clear();
                GUIMPRoom.observablePlayersList.addAll(newList);
                GUIMPRoom.observablePlayersList.sort((String::compareTo));
            });
        }
        this.roomPlayers.clear();
        this.roomPlayers.addAll(newList);
        System.out.println(roomPlayers);

    }

    /**
     * Get the players of the room.
     * @return list containing the players of the room.
     */
    public synchronized List<String> getRoomPlayers() {
        return roomPlayers;
    }

    /**
     * Show the message to the user.
     * @param output message to be shown.
     */
    public void out(String output){
        if(outputMode == GameApplicationMode.CLI){
            System.out.println(output);
        }
    }

    /**
     * Show a new message in the chat.
     * @param from user who sent the message.
     * @param body content of the message.
     */
    public void outChat(String from, String body) {
        if(outputMode == GameApplicationMode.CLI){
            System.out.println(ANSIColor.CYAN + from + ANSIColor.RESET + " said: " + body);
        } else {
            GUIChat.notifyMessage(from, body);
        }
    }

    /**
     * Show a new whisper in the chat.
     * @param from user who sent the whisper.
     * @param body content of the whisper.
     */
    public void outWhisper(String from, String body) {
        if(outputMode == GameApplicationMode.CLI){
            System.out.println(ANSIColor.PURPLE + from + ANSIColor.RESET + " whispered: " + body);
        } else {
            GUIChat.notifyWhisper(from, body);
        }
    }


    // Networking stuff

    /**
     * Start the network client
     * @param hostName Server address.
     * @param portNumber Server port number.
     * @throws GameApplicationException if trying
     */
    public void connect(String hostName, int portNumber){
        if (isOnNetwork())
            return;

        networkClient = new GameClient(hostName, portNumber);
        if(!networkClient.start()) {
            networkClient = null;

            // Notify the GUI active scene, if there is one
            if(GUIScene.getActiveScene() != null) {
                GUIUtility.executorService.submit(() -> GUIScene.getActiveScene().onSystemMessage(SystemMessage.ERR, "It is not possible to establish a connection to the server."));
            }
        }
        else {
            isRunning.set(true);
        }
    }

    /**
     * Send a network packet.
     * @param packet packet to be sent.
     */
    public void sendNetworkPacket(NetworkPacket packet) {
        if(!isOnNetwork()) return;
        networkClient.send(packet);
    }

    /**
     * Start a SP game.
     */
    public void startLocalGame() {
        setUserNickname(DEFAULT_SP_NICKNAME);
        gameController.set(new GameController(new GameData(), userNickname.get()));

        setApplicationState(GameApplicationState.INGAME);
    }

    /**
     * Start a MP game.
     */
    public void startServerGame(boolean singlePlayer) {
        gameController.set(new GameController(new GameData()));
        gameController.get().setSinglePlayer(singlePlayer);

        setApplicationState(GameApplicationState.INGAME);
    }

    /**
     * Leave the current game.
     */
    public void leaveGame() {
        if(isOnNetwork()) {
            String messageContent = SystemMessage.LEAVE_ROOM.getCode();
            NetworkPacket np = new NetworkPacket(NetworkPacketType.SYSTEM, messageContent);
            GameApplication.getInstance().sendNetworkPacket(np);
        }
    }

    /**
     * Close the connection with the server.
     */
    public void closeConnectionWithServer() {
        networkClient.terminateConnection();
        networkClient = null;
    }
}
