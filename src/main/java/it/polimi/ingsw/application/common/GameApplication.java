package it.polimi.ingsw.application.common;

import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.application.gui.GUIChat;
import it.polimi.ingsw.application.gui.GUIScene;
import it.polimi.ingsw.application.gui.scenes.GUIMPRoom;
import it.polimi.ingsw.controller.view.game.GameController;
import it.polimi.ingsw.controller.view.game.handlers.GameControllerIOHandler;
import it.polimi.ingsw.network.client.GameClient;
import it.polimi.ingsw.network.common.NetworkPacket;
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
    private GameController gameController;

    // Lobby
    private final List<String> roomPlayers;

    /**
     * Class constructor
     */
    public GameApplication(){
        this.isRunning = new AtomicBoolean(false);
        this.applicationState = new AtomicReference<>(GameApplicationState.STARTUP);
        this.networkClient = null;
        this.gameController = null;

        this.userId = null;
        this.userNickname = new AtomicReference<>();
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
    public boolean gameExists(){return gameController != null; }

    /**
     * Game controller IO Handler getter.
     * @throws NullPointerException if the game has not been initialized.
     */
    public GameControllerIOHandler getGameControllerIO() throws NullPointerException{
        if(gameController == null) throw new NullPointerException();
        return gameController.getGameControllerIOHandler();
    }

    public GameController getGameController() {
        return gameController;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNickname() {
        return userNickname.get();
    }

    public void setUserNickname(String userNickname) {
        this.userNickname.set(userNickname);
    }

    public String getRoomName() {
        return roomName.get();
    }

    public void setRoomName(String roomName) {
        String currentRoom = this.roomName.get();
        if(!this.roomName.compareAndSet(currentRoom, roomName)) setRoomName(roomName);
    }

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

    public synchronized List<String> getRoomPlayers() {
        return roomPlayers;
    }

    // Output
    public void out(String output){
        if(outputMode == GameApplicationMode.CLI){
            // TODO move to its own class
            System.out.println(output);
        }
    }

    public void outChat(String from, String body) {
        if(outputMode == GameApplicationMode.CLI){
            // TODO move to its own class
            System.out.println(ANSIColor.CYAN + from + ANSIColor.RESET + " said: " + body);
        } else {
            GUIChat.notifyMessage(from, body);
        }
    }

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
        if(!networkClient.start())
            networkClient = null;
        else {
            isRunning.set(true);
        }
    }

    public void sendNetworkPacket(NetworkPacket packet) {
        if(!isOnNetwork()) return;
        networkClient.send(packet);
    }

    /**
     * Start a SP game.
     */
    public void startSPGame() {
        if(userNickname.get() == null) setUserNickname(DEFAULT_SP_NICKNAME);
        gameController = new GameController(new GameData(), userNickname.get());

        setApplicationState(GameApplicationState.INGAME);
    }

    /**
     * Start a MP game.
     */
    public void startMPGame() {
        gameController = new GameController(new GameData());
        getRoomPlayers().forEach(x -> gameController.getGameData().addPlayer(x));
        setApplicationState(GameApplicationState.INGAME);
    }

    public void closeConnectionWithServer() {
        networkClient.terminateConnection();
        networkClient = null;
    }
}
