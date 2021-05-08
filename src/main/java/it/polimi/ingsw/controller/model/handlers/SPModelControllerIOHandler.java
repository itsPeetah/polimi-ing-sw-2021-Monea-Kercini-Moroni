package it.polimi.ingsw.controller.model.handlers;

import it.polimi.ingsw.controller.model.messages.Message;
import it.polimi.ingsw.controller.model.messages.MessagePacket;
import it.polimi.ingsw.controller.model.updates.Update;
import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.controller.model.updates.UpdatePacket;
import it.polimi.ingsw.controller.view.game.handlers.GameControllerIOHandler;
import it.polimi.ingsw.network.common.NetworkPacket;

/**
 * Helper class that handle the input and output of the controller for a multiplayer game.
 * It communicates directly with the view controller.
 */
public class SPModelControllerIOHandler extends ModelControllerIOHandler {
    private final GameControllerIOHandler gameControllerIOHandler;

    /**
     * Constructor
     * @param gameControllerIOHandler IO handler of the view controller, in order to enable communications between the two controllers.
     */
    public SPModelControllerIOHandler(GameControllerIOHandler gameControllerIOHandler) {
        super();
        this.gameControllerIOHandler = gameControllerIOHandler;
    }

    @Override
    public void sendMessage(String player, Message message) {
        MessagePacket messagePacket = new MessagePacket(player, message);
        NetworkPacket networkPacket = NetworkPacket.buildMessagePacket(messagePacket);
        gameControllerIOHandler.notifyMessage(networkPacket);
    }

    @Override
    public void pushUpdate(Update type, UpdateData data) {
        UpdatePacket updatePacket = new UpdatePacket(type, data);
        NetworkPacket networkPacket = NetworkPacket.buildUpdatePacket(updatePacket);
        gameControllerIOHandler.notifyUpdate(networkPacket);
    }
}
