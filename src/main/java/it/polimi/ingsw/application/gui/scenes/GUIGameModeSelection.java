package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.gui.GUIScene;
import javafx.fxml.FXML;


import java.io.IOException;

public class GUIGameModeSelection {

    @FXML
    private void onMPClick() throws IOException {
        GUIScene.MP_SELECTION.load();
    }

    @FXML
    private void onBackClick() throws IOException {
        GUIScene.MAIN_MENU.load();
    }
}
