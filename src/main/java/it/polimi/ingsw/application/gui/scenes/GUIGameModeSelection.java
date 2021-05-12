package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.gui.GUIStage;
import it.polimi.ingsw.application.gui.MaestriRinascimentoGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class GUIGameModeSelection {

    /**
     * Scene constructor with customized size.
     */
    public static Scene getScene(int length, int height) {
        // Create menu buttons
        Button spBtn = new Button("Play alone");
        Button mpBtn = new Button("Play with your friends");
        Button backBtn = new Button("Back");

        // Add event handlers to buttons
        mpBtn.setOnAction(actionEvent -> GUIStage.setScene(GUIMPSelection.getScene()));

        backBtn.setOnAction(actionEvent -> GUIStage.setScene(GUIMainMenu.getScene()));

        // Create menu
        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(spBtn, mpBtn, backBtn);

        // Create root pane
        BorderPane root = new BorderPane();

        // Add menu
        root.setCenter(vbox);

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
