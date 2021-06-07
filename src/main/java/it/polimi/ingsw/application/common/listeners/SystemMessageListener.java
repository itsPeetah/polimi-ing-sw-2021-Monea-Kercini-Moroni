package it.polimi.ingsw.application.common.listeners;

import it.polimi.ingsw.network.common.SystemMessage;
import org.jetbrains.annotations.Nullable;

public interface SystemMessageListener {

    void onSystemMessage(SystemMessage type, @Nullable String additionalContent);

}
