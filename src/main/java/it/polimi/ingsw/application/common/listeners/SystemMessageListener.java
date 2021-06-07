package it.polimi.ingsw.application.common.listeners;

import it.polimi.ingsw.network.common.SystemMessage;

public interface SystemMessageListener {

    void onSystemMessage(SystemMessage type, String additionalContent);

}
