package it.polimi.ingsw.application.gui;

import it.polimi.ingsw.application.common.GameApplicationIOHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GUIChat {
    public static ObservableList<String> observableChatList = FXCollections.observableArrayList();

    public static void sendMessage(String message) {
        if(message != null && message.length() > 0) GameApplicationIOHandler.getInstance().pushChatMessage(message);
    }
}
