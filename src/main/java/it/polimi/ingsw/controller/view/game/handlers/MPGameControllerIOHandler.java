package it.polimi.ingsw.controller.view.game.handlers;

import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.handlers.ModelControllerIOHandler;
import it.polimi.ingsw.controller.view.game.GameController;
import it.polimi.ingsw.network.common.NetworkPacket;

public class MPGameControllerIOHandler extends  GameControllerIOHandler{
    // TODO add network object (like game room for server controller)

    public MPGameControllerIOHandler(GameController gameController) {
        super(gameController);
        // TODO add initialization of network object (like game room for server controller)
    }

    @Override
    public void pushAction(ActionPacket actionPacket) {
        NetworkPacket networkPacket = NetworkPacket.buildActionPacket(actionPacket);
        // TODO send through network using the network object
    }
}
