package it.polimi.ingsw.controller.model.handlers;

import it.polimi.ingsw.controller.model.messages.Message;
import it.polimi.ingsw.controller.model.messages.MessagePacket;
import it.polimi.ingsw.controller.model.updates.Update;
import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.controller.model.updates.UpdatePacket;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.server.components.GameRoom;

/**
 * Helper class that handle the input and output of the controller for a multiplayer game.
 * It uses the network to send the packages.
 */
public class MPModelControllerIOHandler extends ModelControllerIOHandler {
    private final GameRoom gameRoom;

    /**
     * Constructor
     *
     * @param gameRoom
     */
    public MPModelControllerIOHandler(GameRoom gameRoom) {
        super();
        this.gameRoom = gameRoom;
    }

    @Override
    public void sendMessage(String player, Message message) {
        MessagePacket newPacket = new MessagePacket(player, message);
        NetworkPacket networkPacket = NetworkPacket.buildMessagePacket(newPacket);
        try {
            gameRoom.sendTo(player, networkPacket);
        } catch (Exception e) {
            System.out.println("ModelControllerIOHandler: no packet was sent");
        }
    }

    @Override
    public void pushUpdate(Update type, UpdateData data) {
        UpdatePacket updatePacket = new UpdatePacket(type, type.parseData(data));
        NetworkPacket networkPacket = NetworkPacket.buildUpdatePacket(updatePacket);
        try {
            gameRoom.broadcast(networkPacket);
        } catch (Exception e) {
            System.out.println("ModelControllerIOHandler: no packet was sent");
        }
    }
}
