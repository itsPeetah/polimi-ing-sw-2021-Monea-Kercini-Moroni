package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.gui.GUIScene;
import it.polimi.ingsw.application.gui.GUIUtility;
import javafx.event.ActionEvent;

public class GUIGameSettings {

    public void onResumeClick(ActionEvent actionEvent) {
        GUIScene.getScene().setRoot(GUIScene.MAIN_GAME.getRoot());
    }

    public void onExitClick(ActionEvent actionEvent) {
        GUIUtility.handleLeaveGame();
    }
}
