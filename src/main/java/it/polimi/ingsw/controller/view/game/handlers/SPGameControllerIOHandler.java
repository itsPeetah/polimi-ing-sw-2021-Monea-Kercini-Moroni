package it.polimi.ingsw.controller.view.game.handlers;

import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.handlers.ModelControllerIOHandler;
import it.polimi.ingsw.controller.view.game.GameController;
import it.polimi.ingsw.network.common.NetworkPacket;

public class SPGameControllerIOHandler extends GameControllerIOHandler {
    ModelControllerIOHandler modelControllerIOHandler;

    public SPGameControllerIOHandler(GameController gameController, ModelControllerIOHandler modelControllerIOHandler) {
        super(gameController);
        this.modelControllerIOHandler = modelControllerIOHandler;
    }

    @Override
    public void pushAction(ActionPacket actionPacket) {
        NetworkPacket networkPacket = NetworkPacket.buildActionPacket(actionPacket);
        modelControllerIOHandler.notify(networkPacket);
    }
}
