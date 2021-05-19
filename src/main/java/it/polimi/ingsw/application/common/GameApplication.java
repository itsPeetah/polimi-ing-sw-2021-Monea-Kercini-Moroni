package it.polimi.ingsw.application.common;

import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.application.gui.GUIApplication;
import it.polimi.ingsw.application.gui.scenes.GUIMPRoom;
import it.polimi.ingsw.controller.view.game.GameController;
import it.polimi.ingsw.controller.view.game.handlers.GameControllerIOHandler;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.network.client.GameClient;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.view.data.GameData;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.util.*;
import java.util.stream.Collectors;

public class GameApplication {
    private static final String DEFAULT_SP_NICKNAME = "Player";

    // Output mode (CLI/GUI)
    private static GameApplicationMode outputMode = GameApplicationMode.CLI;
    public static void setOutputMode(GameApplicationMode mode) { outputMode = mode; }

    // Singleton
    private static GameApplication instance;
    public static GameApplication getInstance() {
        if(instance == null) instance = new GameApplication();
        return instance;
    }

    // State
    private GameApplicationState applicationState;
    private String userId, userNickname, roomName;
    private boolean isRunning;

    // Components
    private GameClient networkClient;
    private GameController gameController;

    // Lobby
    private final List<String> roomPlayers;

    // Remove?
    /*protected GameApplicationIOHandler ioHandler;*/

    // Concurrency
    private Object lock;


    /**
     * Class constructor
     */
    public GameApplication(){
        this.isRunning = false;
        this.applicationState = GameApplicationState.STARTUP;
        this.networkClient = null;
        this.gameController = null;
        this.lock = new Object();

        this.userId = null;
        this.userNickname = null;
        this.roomName = null;

        this.roomPlayers = new ArrayList<>();

        instance = this;
    }

    // Application state

    /**
     * Application state getter.
     */
    public GameApplicationState getApplicationState(){
        synchronized (lock){
            return applicationState;
        }
    }

    /**
     * Application state setter.
     */
    public void setApplicationState(GameApplicationState state){
        synchronized (lock){
            this.applicationState = state;
        }
    }

    /**
     * Is running getter.
     */
    public boolean isRunning(){ return isRunning; }

    /**
     * Has the networking been initialized?
     */
    public boolean isOnNetwork() {return networkClient != null; }

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
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public synchronized void setRoomPlayers(String stringOfPlayers) {
        List<String> newList = Arrays.asList(stringOfPlayers.split(" ").clone());
        List<String> newPlayers = newList.stream().filter(x -> !this.roomPlayers.contains(x)).collect(Collectors.toList());
        if(outputMode == GameApplicationMode.GUI) {
            Platform.runLater(() -> {
                GUIMPRoom.observablePlayersList.addAll(newPlayers);
                GUIMPRoom.observablePlayersList.sort((String::compareTo));
            });
        }
        this.roomPlayers.addAll(newPlayers);
        System.out.println(roomPlayers.toString());

    }

    public synchronized List<String> getRoomPlayers() {
        return roomPlayers;
    }

    // Output
    public void out(String output){
        if(outputMode == GameApplicationMode.CLI){
            // TODO move to its own class
            System.out.println(output);
        } else {
            GUIApplication.showDialog(output);
            System.out.println(output);
        }
    }

    public void outChat(String from, String body) {
        if(outputMode == GameApplicationMode.CLI){
            // TODO move to its own class
            System.out.println(ANSIColor.CYAN + from + ANSIColor.RESET + " said: " + body);
        } else {
            Platform.runLater(() -> GUIMPRoom.observableChatList.add(from + ": " + body));
        }
    }

    public void outWhisper(String from, String body) {
        if(outputMode == GameApplicationMode.CLI){
            System.out.println(ANSIColor.PURPLE + from + ANSIColor.RESET + " whispered: " + body);
        } else {
            Platform.runLater(() -> GUIMPRoom.observableChatList.add(from + " whispered: " + body));
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
        synchronized (lock) {
            if (isOnNetwork())
                return;
        }

        networkClient = new GameClient(hostName, portNumber);
        if(!networkClient.start())
            networkClient = null;
        else
            isRunning = true;
    }

    public void sendNetworkPacket(NetworkPacket packet) {
        if(!isOnNetwork()) return;
        networkClient.send(packet);
    }

    /**
     * Start a SP game.
     */
    public void startSPGame() {
        gameController = new GameController(new GameData(), userNickname == null ? DEFAULT_SP_NICKNAME : userNickname);
        setApplicationState(GameApplicationState.INGAME);
    }

    /**
     * Start a MP game.
     */
    public void startMPGame() {
        gameController = new GameController(new GameData());
        setApplicationState(GameApplicationState.INGAME);
    }
}
