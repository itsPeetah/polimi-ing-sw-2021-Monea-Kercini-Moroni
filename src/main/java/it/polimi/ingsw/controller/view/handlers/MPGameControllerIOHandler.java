package it.polimi.ingsw.controller.view.handlers;

import it.polimi.ingsw.application.common.GameApplicationIOHandler;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.view.GameController;

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
