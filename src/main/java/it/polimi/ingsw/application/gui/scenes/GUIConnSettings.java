package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.gui.GUIScene;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class GUIConnSettings {
    public TextField addressTextField;
    public TextField portTextField;

    public void onBackButton(ActionEvent actionEvent) {
        GUIScene.SETTINGS.load();
    }

    public void onConnectClick(ActionEvent actionEvent) {
    }
}
