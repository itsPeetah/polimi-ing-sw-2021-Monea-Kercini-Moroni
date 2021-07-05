package it.polimi.ingsw.application.common.listeners;

import it.polimi.ingsw.controller.model.messages.Message;

public interface GameMessageListener {

    /**
     * On game message.
     * @param message type of the message.
     */
    void onMessage(Message message);

}
