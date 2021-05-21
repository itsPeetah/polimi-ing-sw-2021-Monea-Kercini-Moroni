package it.polimi.ingsw.application.common.listeners;

import it.polimi.ingsw.controller.model.messages.Message;

public interface MessageListener {

    void onMessage(Message message);

}
