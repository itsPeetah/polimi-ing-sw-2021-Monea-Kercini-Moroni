package it.polimi.ingsw.application.common.listeners;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationMode;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.application.gui.GUIUtility;
import it.polimi.ingsw.controller.model.messages.Message;
import it.polimi.ingsw.network.common.SystemMessage;
import javafx.application.Platform;
import org.jetbrains.annotations.Nullable;

public interface PacketListener extends GameMessageListener, SystemMessageListener {


    @Override
    default void onMessage(Message message) {
        // Empty default behaviour, so that PacketListeners that make no use of game messages do not need to define this method.
    }

    @Override
    default void onSystemMessage(SystemMessage type, @Nullable String additionalContent) {
        // Default behaviour, takes care of quit messages on GUI.
        if(type == SystemMessage.QUIT && GameApplication.getOutputMode() == GameApplicationMode.GUI) {
            Platform.runLater(GUIUtility::handleServerQuit);
        }
    }
}
