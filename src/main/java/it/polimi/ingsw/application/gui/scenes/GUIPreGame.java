package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.gui.MaestriRinascimentoGUI;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class GUIPreGame {
    /**
     * Scene constructor with customized size.
     */
    public static Scene getScene(int length, int height) {
        // Create root pane
        StackPane root = new StackPane();

        // Create text
        Text text = new Text("You successfully joined a room. Waiting for the game to start...");
        root.getChildren().add(text);

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
