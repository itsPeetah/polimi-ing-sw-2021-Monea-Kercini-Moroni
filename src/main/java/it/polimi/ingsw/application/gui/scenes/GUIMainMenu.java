package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.gui.GUIScene;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GUIMainMenu {

    /**
     * On play button click.
     * @param actionEvent
     */
    @FXML
    private void onPlayClick(ActionEvent actionEvent) {
        GUIScene.GAME_MODE_SELECTION.load();
    }

    /**
     * On exit button click.
     * @param actionEvent
     */
    @FXML
    public void onExitClick(ActionEvent actionEvent) {
        Platform.exit();
    }


    public void onConnectionClick(ActionEvent actionEvent) {
        GUIScene.CONN_SETTINGS.load();
    }
}
