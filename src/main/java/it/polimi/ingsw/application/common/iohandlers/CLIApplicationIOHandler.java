package it.polimi.ingsw.application.common.iohandlers;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationIOHandler;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;

public class CLIApplicationIOHandler extends GameApplicationIOHandler {


    @Override
    public int handleSystemMessage(NetworkPacket systemMessageNetworkPacket) {

        String sysMsg = systemMessageNetworkPacket.getPayload();
        String[] fields = sysMsg.split(" ", 2);

        return super.handleSystemMessage(systemMessageNetworkPacket);
    }

    @Override
    public void handleDebugMessage(NetworkPacket debugMessageNetworkPacket) {
        String message = debugMessageNetworkPacket.getPayload();
        System.out.println(message);
    }
}
