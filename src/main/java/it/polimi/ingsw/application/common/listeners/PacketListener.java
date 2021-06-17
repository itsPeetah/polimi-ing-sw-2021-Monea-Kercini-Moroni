package it.polimi.ingsw.application.common.listeners;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationMode;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.application.gui.GUIUtility;
import it.polimi.ingsw.controller.model.messages.Message;
import it.polimi.ingsw.network.common.SystemMessage;
import org.jetbrains.annotations.Nullable;

public interface PacketListener extends GameMessageListener, SystemMessageListener {


    @Override
    default void onMessage(Message message) {

    }

    @Override
    default void onSystemMessage(SystemMessage type, @Nullable String additionalContent) {
        if(type == SystemMessage.QUIT) {
            GameApplicationState gameApplicationState = GameApplication.getInstance().getApplicationState();
            GameApplication.getInstance().setApplicationState(GameApplicationState.PREGAME);
            if(GameApplication.getOutputMode() == GameApplicationMode.GUI && gameApplicationState == GameApplicationState.INGAME) {
                GUIUtility.handleLeaveGame();
            }
        }
    }
}
