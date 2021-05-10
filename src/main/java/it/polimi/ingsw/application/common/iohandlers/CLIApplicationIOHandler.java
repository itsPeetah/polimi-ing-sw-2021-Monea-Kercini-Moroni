package it.polimi.ingsw.application.common.iohandlers;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationIOHandler;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.sysmsg.ConnectionMessage;
import it.polimi.ingsw.view.scenes.GameScene;

public class CLIApplicationIOHandler extends GameApplicationIOHandler {

    @Override
    public int handleSystemMessage(NetworkPacket systemMessageNetworkPacket) {

       /* String sysMsg = systemMessageNetworkPacket.getPayload();
        String[] fields = sysMsg.split(" ", 2);
        System.out.println(sysMsg);
        // TODO Move out of this method

        GameApplicationState state = GameApplication.getInstance().getApplicationState();

        if(state == GameApplicationState.CONNECTING_TO_ROOM){
            if(ConnectionMessage.ERR.check(fields[0])){
                GameApplication.getInstance().setApplicationState(GameApplicationState.LOBBY);
                return 0;
            } else if (ConnectionMessage.OK.check(fields[1])){
                GameApplication.
                return 0;
            }
        }
*/
        return super.handleSystemMessage(systemMessageNetworkPacket);
    }

    @Override
    public void handleDebugMessage(NetworkPacket debugMessageNetworkPacket) {
        String message = debugMessageNetworkPacket.getPayload();
        System.out.println(message);
    }
}
