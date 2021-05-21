package it.polimi.ingsw.application.common.listeners;

import it.polimi.ingsw.controller.model.messages.Message;

public interface GameMessageListener {

    void onMessage(Message message);

}
