package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.gui.GUIScene;
import javafx.event.ActionEvent;

public class GUISettings {
    public void onBackClick(ActionEvent actionEvent) {
        GUIScene.MAIN_MENU.load();
    }

    public void onConnectionClick(ActionEvent actionEvent) {
        GUIScene.CONN_SETTINGS.load();
    }

    public void onLanguageClick(ActionEvent actionEvent) {
    }
}
