package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.application.gui.GUIStage;
import it.polimi.ingsw.application.gui.MaestriRinascimentoGUI;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GUIStartup {

    /**
     * Scene constructor with customized size.
     */
    public static Scene getScene(int length, int height) {
        // Retrieve GameApplication
        GameApplication gameApplication = GameApplication.getInstance();

        // Create button
        Button showAppRunningBtn = new Button();
        showAppRunningBtn.setText("Connect");
        showAppRunningBtn.setOnAction(actionEvent -> {
            System.out.println("Is the app running? " + gameApplication.isRunning());
            gameApplication.setApplicationState(GameApplicationState.STARTED);
            GUIStage.setScene(GUIMainMenu.getScene());
        });

        // Create root pane
        StackPane root = new StackPane();
        root.getChildren().add(showAppRunningBtn);

        // Create scene
        return new Scene(root, length, height);
    }

    /**
     * Scene constructor with default size.
     */
    public static Scene getScene() {
        return getScene(MaestriRinascimentoGUI.LENGTH, MaestriRinascimentoGUI.HEIGHT);
    }
}
