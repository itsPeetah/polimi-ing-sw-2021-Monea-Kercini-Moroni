package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.application.gui.GUIScene;
import javafx.application.Platform;
import javafx.fxml.FXML;


import java.io.IOException;

public class GUIGameModeSelection {

    @FXML
    private void onMPClick() {
        GUIScene.MP_SELECTION.load();
    }

    @FXML
    private void onSPClick() {
        new Thread(() -> {
            GameApplication.getInstance().startSPGame();
            while (GameApplication.getInstance().getApplicationState() == GameApplicationState.PREGAME) {}
            GameApplicationState newState = GameApplication.getInstance().getApplicationState();
            System.out.println(newState);
            System.out.println("SP thread ended");
            Platform.runLater(GUIScene.PRE_GAME::load);
        }).start();
    }

    @FXML
    private void onBackClick() {
        GUIScene.MAIN_MENU.load();
    }
}
