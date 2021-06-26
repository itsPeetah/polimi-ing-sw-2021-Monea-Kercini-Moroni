package it.polimi.ingsw.controller.model.handlers;

import it.polimi.ingsw.controller.model.messages.Message;
import it.polimi.ingsw.controller.model.messages.MessagePacket;
import it.polimi.ingsw.controller.model.updates.Update;
import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.controller.model.updates.UpdatePacket;
import it.polimi.ingsw.controller.view.GameController;

/**
 * Helper class that handle the input and output of the controller for a multiplayer game.
 * It communicates directly with the view controller.
 */
public class SPModelControllerIOHandler extends ModelControllerIOHandler {
    private final GameController gameController;

    /**
     * Constructor
     * @param gameController view controller, in order to enable communications between the two controllers.
     */
    public SPModelControllerIOHandler(GameController gameController) {
        super();
        this.gameController = gameController;
    }

    @Override
    public void sendMessage(String player, Message message) {
        MessagePacket messagePacket = new MessagePacket(player, message);
        /*NetworkPacket networkPacket = NetworkPacket.buildMessagePacket(messagePacket);*/
        gameController.getGameControllerIOHandler().notifyMessage(messagePacket);
    }

    @Override
    public void pushUpdate(Update type, UpdateData data) {
        UpdatePacket updatePacket = new UpdatePacket(type, type.parseData(data));
        /*NetworkPacket networkPacket = NetworkPacket.buildUpdatePacket(updatePacket);*/
        gameController.getGameControllerIOHandler().notifyUpdate(updatePacket);
    }
}
