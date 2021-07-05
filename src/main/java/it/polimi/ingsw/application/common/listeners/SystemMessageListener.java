package it.polimi.ingsw.application.common.listeners;

import it.polimi.ingsw.network.common.SystemMessage;
import org.jetbrains.annotations.Nullable;

public interface SystemMessageListener {

    /**
     * On system message.
     * @param type type of the message.
     * @param additionalContent additional content to be notified to the user.
     */
    void onSystemMessage(SystemMessage type, @Nullable String additionalContent);

}
