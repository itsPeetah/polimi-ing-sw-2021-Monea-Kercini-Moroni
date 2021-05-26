package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.gui.GUIScene;
import javafx.event.Event;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class GUIUtility {

    public static void launchOrganizeWarehouseWindow(Window owner) {
        Stage stage = new Stage();
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setOnCloseRequest(Event::consume);
        stage.setTitle("Reorganize your warehouse");
        stage.setScene(GUIScene.WAREHOUSE.produceScene());
        stage.show();
    }

}
