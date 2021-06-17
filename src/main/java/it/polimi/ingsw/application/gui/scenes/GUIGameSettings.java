package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.gui.GUIScene;
import it.polimi.ingsw.application.gui.GUIUtility;
import javafx.event.ActionEvent;

public class GUIGameSettings {

    public void onResumeClick(ActionEvent actionEvent) {
        GUIScene.getScene().setRoot(GUIScene.MAIN_GAME.getRoot());
    }

    public void onExitClick(ActionEvent actionEvent) {
        GUIScene.showLoadingScene();
        GUIScene.removeActiveScene();
        if(GameApplication.getInstance().getGameController().isSinglePlayer()) {
            GUIUtility.runSceneWithDelay(GUIScene.MAIN_MENU, 500);
        } else {
            // TODO implement mp leave
        }

    }
}
