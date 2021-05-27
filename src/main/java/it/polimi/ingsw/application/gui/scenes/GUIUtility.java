package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.gui.GUIScene;
import javafx.event.Event;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;

import static it.polimi.ingsw.application.gui.GUIApplication.ICON_PATH;

public class GUIUtility {

    public static void launchOrganizeWarehouseWindow(Window owner) {
        Stage stage = new Stage();
        File file = new File(ICON_PATH);
        Image iconImage = new Image(file.toURI().toString());
        stage.getIcons().add(iconImage);
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setOnCloseRequest(Event::consume);
        stage.setTitle("Reorganize your warehouse");
        stage.setScene(GUIScene.WAREHOUSE.produceScene());
        stage.show();
    }

}
