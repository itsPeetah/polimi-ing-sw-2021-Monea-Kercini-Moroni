package it.polimi.ingsw.controller.view.game.handlers;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationIOHandler;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.handlers.ModelControllerIOHandler;
import it.polimi.ingsw.controller.view.game.GameController;
import it.polimi.ingsw.network.common.NetworkPacket;

public class MPGameControllerIOHandler extends  GameControllerIOHandler{

    public MPGameControllerIOHandler(GameController gameController) {
        super(gameController);
    }

    @Override
    public void pushAction(ActionPacket actionPacket) {
        /*NetworkPacket networkPacket = NetworkPacket.buildActionPacket(actionPacket);*/
        GameApplicationIOHandler.getInstance().pushAction(actionPacket);
    }
}
