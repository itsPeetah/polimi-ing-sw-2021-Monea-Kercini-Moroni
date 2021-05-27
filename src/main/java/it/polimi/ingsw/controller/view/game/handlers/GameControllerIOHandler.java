package it.polimi.ingsw.controller.view.game.handlers;

import com.google.gson.Gson;
import it.polimi.ingsw.application.gui.GUIScene;
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

    public void notifyMessage(MessagePacket messagePacket) {
        pool.submit(() -> gameController.reactToMessage(messagePacket.getMessage()));
        pool.submit(() -> {
            if(GUIScene.getActiveScene() != null) {
                GUIScene.getActiveScene().onMessage(messagePacket.getMessage());
            }
        });
    }

    public void notifyAction(ActionPacket actionPacket) {
        pool.submit(() -> gameController.reactToAction(actionPacket));
        pool.submit(() -> pushAction(actionPacket));
    }

    public void notifyUpdate(UpdatePacket updatePacket) {
        pool.submit(() -> gameController.reactToUpdate(updatePacket.getUpdate(), updatePacket.getData()));
    }

    public abstract void pushAction(ActionPacket actionPacket);
}
