package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.gui.GUIApplication;
import it.polimi.ingsw.application.gui.MaestriRinascimentoGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class GUIMainMenu {

    /**
     * Scene constructor with customized size.
     */
    public static Scene getScene(int length, int height) {
        // Retrieve GameApplication
        GameApplication gameApplication = GameApplication.getInstance();

        // Create menu buttons
        Button startGameBtn = new Button("Play");
        Button optionsBtn = new Button("Options");
        Button creditsBtn = new Button("Credits");

        // Add event handlers to buttons
        startGameBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GUIApplication.setScene(GUIGameModeSelection.getScene());
            }
        });

        // Create menu
        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(startGameBtn, optionsBtn, creditsBtn);

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
