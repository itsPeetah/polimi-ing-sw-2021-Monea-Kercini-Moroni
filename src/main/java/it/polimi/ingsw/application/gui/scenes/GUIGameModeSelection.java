package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.gui.GUIScene;
import it.polimi.ingsw.application.gui.GUIUtility;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class GUIGameModeSelection {

    /**
     * On MP button click.
     */
    @FXML
    private void onMPClick() {
        if(!GameApplication.getInstance().isOnNetwork()) {
            Alert connectionAlert = new Alert(Alert.AlertType.CONFIRMATION);
            connectionAlert.setTitle("Connection is down");
            connectionAlert.setContentText("You are not connected to the server.\nTo start a MP game, you need to be connected.\nDo you want to move to the settings to connect to a server?");
            connectionAlert.setHeaderText(null);

            Optional<ButtonType> result = connectionAlert.showAndWait();
            if(result.get() == ButtonType.OK) {
                Platform.runLater(GUIScene.CONN_SETTINGS::load);
            }
        } else {
            GUIScene.MP_SELECTION.load();
        }
    }

    /**
     * On SP button click.
     */
    @FXML
    private void onSPClick() {
        GUIScene.showLoadingScene();
        new Thread(() -> {
            GameApplication.getInstance().startLocalGame();
            /*while (GameApplication.getInstance().getApplicationState() == GameApplicationState.PREGAME) {}
            GameApplicationState newState = GameApplication.getInstance().getApplicationState();
            System.out.println(newState);*/
            GUIUtility.runSceneWithDelay(GUIScene.PRE_GAME);
        }).start();
    }

    /**
     * On back button click.
     */
    @FXML
    private void onBackClick() {
        GUIScene.MAIN_MENU.load();
    }
}
