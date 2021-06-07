package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.gui.GUIChat;
import it.polimi.ingsw.application.gui.GUIObserverScene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GUIGameChat implements GUIObserverScene {

    public ListView<String> chatListView;
    public TextField textField;

    public void sendMessage(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)) {
            String message = textField.getText();
            textField.clear();
            GUIChat.sendMessage(message);
        }
    }

    @Override
    public void startObserver() {
        GUIChat.bindChat(chatListView);
    }
}
