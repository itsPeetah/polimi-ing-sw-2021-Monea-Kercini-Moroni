package it.polimi.ingsw.controller.view.game.handlers;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.messages.MessagePacket;
import it.polimi.ingsw.controller.model.updates.UpdatePacket;
import it.polimi.ingsw.controller.view.game.GameController;
import it.polimi.ingsw.network.common.NetworkPacket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class GameControllerIOHandler {
    private final ExecutorService pool;
    private final GameController gameController;

    public GameControllerIOHandler(GameController gameController) {
        pool = Executors.newCachedThreadPool();
        this.gameController = gameController;

    }

    public void notifyMessage(NetworkPacket messageNetworkPacket) {
        Gson gson = new Gson();
        MessagePacket messagePacket = gson.fromJson(messageNetworkPacket.getPayload(), MessagePacket.class);
        pool.submit(() -> gameController.reactToMessage(messagePacket.getMessage()));
    }

    public void notifyAction(ActionPacket actionPacket) {
        pool.submit(() -> gameController.reactToAction(actionPacket));
    }

    public void notifyUpdate(NetworkPacket updateNetworkPacket) {
        Gson gson = new Gson();
        UpdatePacket updatePacket = gson.fromJson(updateNetworkPacket.getPayload(), UpdatePacket.class);
        pool.submit(() -> gameController.reactToUpdate(updatePacket.getUpdate(), updatePacket.getData()));
    }

    public abstract void pushAction(ActionPacket actionPacket);
}
