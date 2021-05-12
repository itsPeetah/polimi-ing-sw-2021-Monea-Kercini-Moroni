package it.polimi.ingsw.application.common;

import com.google.gson.Gson;
import it.polimi.ingsw.application.common.iohandlers.GUIApplicationIOHandler;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.messages.MessagePacket;
import it.polimi.ingsw.controller.model.updates.UpdatePacket;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameApplicationIOHandler {
    protected final static Gson gson = new Gson();

    private static GameApplicationIOHandler instance;
    public static GameApplicationIOHandler getInstance(){
        if(instance == null) instance = new GameApplicationIOHandler();
        return instance;
    }

    // TODO make class abstract
    // TODO make subclasses CLIApplicationIOHandler and GUIApplicationIOHandler

    public GameApplicationIOHandler() {
    }

    // TODO Add others

    public void notifyMessage(NetworkPacket messageNetworkPacket) {
        MessagePacket messagePacket = gson.fromJson(messageNetworkPacket.getPayload(), MessagePacket.class);
        GameApplication.getInstance().getGameControllerIO().notifyMessage(messagePacket);
    }

    public void notifyUpdate(NetworkPacket updateNetworkPacket) {
        UpdatePacket updatePacket = gson.fromJson(updateNetworkPacket.getPayload(), UpdatePacket.class);
        GameApplication.getInstance().getGameControllerIO().notifyUpdate(updatePacket);
    }

    public void pushAction(ActionPacket actionPacket) {
        NetworkPacket networkPacket = NetworkPacket.buildActionPacket(actionPacket);
        GameApplication.getInstance().sendNetworkPacket(networkPacket);
    }

    public int handleSystemMessage(NetworkPacket systemMessageNetworkPacket){
        String serverMessage = systemMessageNetworkPacket.getPayload();
        String[] messageFields = serverMessage.split(" ", 2);

        if (ConnectionMessage.QUIT.check(messageFields[0])) {
            GameApplication.getInstance().setApplicationState(GameApplicationState.STOPPED);
            return -1;
        } else {
            if(ConnectionMessage.OK.check(messageFields[0])){
                if(messageFields.length > 1) handleSystemOK(messageFields[1]);
                else handleSystemOK(null);
            } else if (ConnectionMessage.ERR.check(messageFields[0])){
                if(messageFields.length > 1) handleSystemERR(messageFields[1]);
                else handleSystemERR(null);
            }

        }

        return 0;
    }

    public void handleDebugMessage(NetworkPacket debugMessageNetworkPacket){
        GameApplication.getInstance().out(debugMessageNetworkPacket.getPayload());
    }

    private void handleSystemOK(String args){

        GameApplicationState state = GameApplication.getInstance().getApplicationState();
        switch (state){
            case CONNECTING_TO_ROOM:
                if(args != null) GameApplication.getInstance().out(args);
                GameApplication.getInstance().setApplicationState(GameApplicationState.PREGAME);
                break;
        }

    }

    private void handleSystemERR(String args){
        GameApplicationState state = GameApplication.getInstance().getApplicationState();
        switch (state){
            case CONNECTING_TO_ROOM:
                if(args != null) GameApplication.getInstance().out(args);
                GameApplication.getInstance().setApplicationState(GameApplicationState.LOBBY);
                break;
        }
    }

}
