package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.gui.GUIApplication;
import it.polimi.ingsw.application.gui.GUIScene;
import it.polimi.ingsw.application.gui.MaestriRinascimentoGUI;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

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
