package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.gui.GUIScene;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    @FXML
    public void onExitClick(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void onSettingsClick(ActionEvent actionEvent) {
        GUIScene.SETTINGS.load();
    }
}
