package it.polimi.ingsw.application.common;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.messages.MessagePacket;
import it.polimi.ingsw.controller.model.updates.UpdatePacket;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class GameApplicationIOHandler {
    private final static Gson gson = new Gson();

    // TODO make class abstract
    // TODO make subclasses CLIApplicationIOHandler and GUIApplicationIOHandler
    // TODO Add IOHandler to GameApplication constructor

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

        if (serverMessage == null || ConnectionMessage.QUIT.check(messageFields[0])) {
           return -1;
        }

        return 0;
    }

    public void handleDebugMessage(NetworkPacket debugMessageNetworkPacket){

    }

}
