package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.model.cards.LeadCard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Initial scene when game starts
 * Player will be offered 4 leaders and he should choose 2
 */

public class GUIPreGame implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**

    @FXML
    private void onPlayClick(ActionEvent actionEvent) {
        GUIScene.GAME_MODE_SELECTION.load();
    }
    */

    @FXML
    private ArrayList<ImageView> offeredLeaders = new ArrayList<ImageView>();

    public void updateOfferedLeaders(ArrayList<LeadCard> leaders) {

        //this.offeredLeaders.get(0).setImage(leaders.get(0).getImage);
    }
}

