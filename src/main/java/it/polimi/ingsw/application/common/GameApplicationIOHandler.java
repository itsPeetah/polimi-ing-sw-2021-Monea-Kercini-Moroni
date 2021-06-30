package it.polimi.ingsw.application.common;

import it.polimi.ingsw.application.cli.util.ANSIColor;
import it.polimi.ingsw.application.gui.GUIScene;
import it.polimi.ingsw.application.gui.GUIUtility;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.messages.MessagePacket;
import it.polimi.ingsw.controller.model.updates.UpdatePacket;
import it.polimi.ingsw.network.client.persistence.ReconnectionInfo;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.social.SocialPacket;
import it.polimi.ingsw.network.common.SystemMessage;
import it.polimi.ingsw.util.JSONUtility;
import org.jetbrains.annotations.Nullable;

public class GameApplicationIOHandler {

    private static GameApplicationIOHandler instance;
    public static GameApplicationIOHandler getInstance(){
        if(instance == null) instance = new GameApplicationIOHandler();
        return instance;
    }

    public GameApplicationIOHandler() {
    }

    /**
     * Notify a new game message.
     * @param messageNetworkPacket network packet containing a game message.
     */
    public void notifyMessage(NetworkPacket messageNetworkPacket) {
        MessagePacket messagePacket = JSONUtility.fromJson(messageNetworkPacket.getPayload(), MessagePacket.class);
        GameApplication.getInstance().getGameControllerIO().notifyMessage(messagePacket);
    }

    /**
     * Notify a new game update.
     * @param updateNetworkPacket network packet containing a game update.
     */
    public void notifyUpdate(NetworkPacket updateNetworkPacket) {
        UpdatePacket updatePacket = JSONUtility.fromJson(updateNetworkPacket.getPayload(), UpdatePacket.class);
        /*System.out.println(ANSIColor.YELLOW + "RECEIVED UPDATE " + updatePacket.getData() + ANSIColor.RESET);*/
        GameApplication.getInstance().getGameControllerIO().notifyUpdate(updatePacket);
    }

    /**
     * Notify a new system message.
     * @param sysMsgType type of the message.
     * @param body content of the message.
     */
    public void notifySystemMessage(SystemMessage sysMsgType, @Nullable String body){
        if(GameApplication.getOutputMode() == GameApplicationMode.GUI && GUIScene.getActiveScene() != null) {
            GUIUtility.executorService.submit(() -> GUIScene.getActiveScene().onSystemMessage(sysMsgType , body));
        }
    }

    /**
     * Send an action to the server.
     * @param actionPacket packet of the action.
     */
    public void pushAction(ActionPacket actionPacket) {
        NetworkPacket networkPacket = NetworkPacket.buildActionPacket(actionPacket);
        GameApplication.getInstance().sendNetworkPacket(networkPacket);
    }

    /**
     * Send a chat message to the server.
     * @param content content of the message.
     */
    public void pushChatMessage(String content) {
        GameApplication gameApplication = GameApplication.getInstance();
        NetworkPacket networkPacket = NetworkPacket.buildChatPacket(content, gameApplication.getUserNickname());
        gameApplication.sendNetworkPacket(networkPacket);
    }

    /**
     * Send a whisper message to the server.
     * @param content ontent of the whisper.
     * @param to addressee of the whisper.
     */
    public void pushWhisperMessage(String content, String to) {
        GameApplication gameApplication = GameApplication.getInstance();
        NetworkPacket networkPacket = NetworkPacket.buildWhisperPacket(content, gameApplication.getUserNickname(), to);
        gameApplication.sendNetworkPacket(networkPacket);
    }

    /**
     * Handle a system message.
     * @param systemMessageNetworkPacket network packet containing the system message.
     * @return
     */
    public int handleSystemMessage(NetworkPacket systemMessageNetworkPacket){
        /*System.out.println("GameApplicationIOHandler.handleSystemMessage");*/
        String serverMessage = systemMessageNetworkPacket.getPayload();
        String[] messageFields = serverMessage.split(" ", 2);
        String[] messageArgs = messageFields.length > 1 ? messageFields[1].split(" ") : null;

        int exitCode = 0;
        // Perhaps change with a switch(SystemMessage.valueOf(messageFields[0]) ?
        if (SystemMessage.QUIT.check(messageFields[0])) {
            handleQuitMessage();
            exitCode = -1;
        }
        else if(SystemMessage.IN_LOBBY.check(messageFields[0])) {
            GameApplication.getInstance().setApplicationState(GameApplicationState.LOBBY);
            notifySystemMessage(SystemMessage.IN_LOBBY, null);
        }
        else if(SystemMessage.PING.check(messageFields[0])) {
            GameApplication.getInstance().sendNetworkPacket(NetworkPacket.buildSystemMessagePacket(SystemMessage.PING.getCode()));
        }
        else if(SystemMessage.START_ROOM.check(messageFields[0])) {
            ReconnectionInfo.saveID(GameApplication.getInstance().getUserId());
            if(messageArgs != null && messageArgs[0].equals("sp")) {
                GameApplication.getInstance().startServerGame(true);
                notifySystemMessage(SystemMessage.START_ROOM, "sp");
            }
            else {
                GameApplication.getInstance().startServerGame(false);
                notifySystemMessage(SystemMessage.START_ROOM, "mp");
            }
        } else if(SystemMessage.GAME_OVER.check(messageFields[0])){
            ReconnectionInfo.resetID();
            // Not leaving the room as long as the end game "scene" is displayed.
            /*GameApplication.getInstance().sendNetworkPacket(NetworkPacket.buildSystemMessagePacket(SystemMessage.LEAVE_ROOM.getCode()));*/
        }
        else if(SystemMessage.PLAYERS_IN_ROOM.check(messageFields[0])) {
            System.out.println(messageFields[1]);
            GameApplication.getInstance().setRoomPlayers(messageFields[1]);
        }
        else if(SystemMessage.IN_ROOM.check(messageFields[0])){
            /*System.out.println("GameApplicationIOHandler.handleSystemMessage: messageArgs[0] = " + messageArgs[0] + " messageArgs[1] = " + messageArgs[1]);*/
            GameApplication.getInstance().setRoomName(messageArgs[0]);
            GameApplication.getInstance().setUserNickname(messageArgs[1]);
            GameApplication.getInstance().setApplicationState(GameApplicationState.PREGAME);
            notifySystemMessage(SystemMessage.IN_ROOM, messageArgs[0] + " " + messageArgs[1]);
        } else if (SystemMessage.CANT_JOIN.check(messageFields[0])){
            if(messageArgs != null) GameApplication.getInstance().out(messageFields[1]);
            notifySystemMessage(SystemMessage.CANT_JOIN, null);
            GameApplication.getInstance().setApplicationState(GameApplicationState.LOBBY);
        } else if (SystemMessage.IN_GAME.check(messageFields[0])){
            GameApplication.getInstance().setApplicationState((GameApplicationState.INGAME));
            GameApplication.getInstance().startServerGame(messageArgs[0].equals("sp"));
            notifySystemMessage(SystemMessage.IN_GAME, messageArgs[0]);
        } else if (SystemMessage.ERR.check(messageFields[0])) {
            System.out.println(ANSIColor.RED+ "[ERR] " + messageFields[1] + ANSIColor.RESET);
            notifySystemMessage(SystemMessage.ERR, null);
        }

        return exitCode;
    }

    /**
     * Handle a social (chat/whisper) message.
     * @param networkPacket network packet containing a social message.
     */
    public void handleSocialMessage(NetworkPacket networkPacket) {
        SocialPacket socialPacket = JSONUtility.fromJson(networkPacket.getPayload(), SocialPacket.class);
        GameApplication gameApplication = GameApplication.getInstance();
        switch(socialPacket.getType()) {
            case CHAT:
                gameApplication.outChat(socialPacket.getFrom(), socialPacket.getBody());
                break;
            case WHISPER:
                gameApplication.outWhisper(socialPacket.getFrom(), socialPacket.getBody());
                break;
        }
    }

    /**
     * Handle a debug message.
     * @param debugMessageNetworkPacket network packet containing the debug message.
     */
    public void handleDebugMessage(NetworkPacket debugMessageNetworkPacket){
        GameApplication.getInstance().out(debugMessageNetworkPacket.getPayload());
    }

    /**
     * Handle a quit message.
     */
    private void handleQuitMessage() {
        System.out.println("Received quit instruction");
        GameApplication.getInstance().closeConnectionWithServer();
        GameApplication.getInstance().setApplicationState(GameApplicationState.STARTED);
    }



}
