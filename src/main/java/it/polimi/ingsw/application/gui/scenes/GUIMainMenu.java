package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.gui.GUIApplication;
import it.polimi.ingsw.application.gui.GUIScene;
import it.polimi.ingsw.application.gui.MaestriRinascimentoGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GUIMainMenu implements Initializable {

    @FXML
    private void onPlayClick(ActionEvent actionEvent) throws IOException {
        GUIScene.GAME_MODE_SELECTION.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
