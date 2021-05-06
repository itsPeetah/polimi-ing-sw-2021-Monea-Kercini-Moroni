package it.polimi.ingsw.controller.view.game;

import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.messages.MessagePacket;
import it.polimi.ingsw.controller.model.updates.UpdatePacket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommunicationHandler {
    private final ExecutorService pool;
    private final GameController gameController;

    public CommunicationHandler(GameController gameController) {
        pool = Executors.newCachedThreadPool();
        this.gameController = gameController;

    }

    public void notifyMessage(MessagePacket messagePacket) {
        pool.submit(() -> gameController.reactToMessage(messagePacket.getMessage()));
    }

    public void notifyAction(ActionPacket actionPacket) {
        pool.submit(() -> gameController.reactToAction(actionPacket));
    }

    public void notifyUpdate(UpdatePacket updatePacket) {
        pool.submit(() -> gameController.reactToUpdate(updatePacket.getUpdate(), updatePacket.getData()));
    }

    public void pushAction(ActionPacket actionPacket) {
        // Todo push the action using network methods
    }
}
