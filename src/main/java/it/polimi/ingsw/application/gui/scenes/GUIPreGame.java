package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.model.cards.LeadCard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Initial scene when game starts
 * Player will be offered 4 leaders and he should choose 2
 */

public class GUIPreGame implements Initializable {

    private ArrayList<ImageView> offeredLeaders = new ArrayList<ImageView>();



    @FXML
    private ImageView image1 = new ImageView();

    @FXML
    private Sphere marble = new Sphere(29);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        offeredLeaders.add(image1);
        setImageTest();
    }

    /**

    @FXML
    private void onPlayClick(ActionEvent actionEvent) {
        GUIScene.GAME_MODE_SELECTION.load();
    }
    */

    public void updateOfferedLeaders(ArrayList<LeadCard> leaders) {

        //this.offeredLeaders.get(0).setImage(leaders.get(0).getImage);
    }

    public void setImageTest(){

        File file = new File("src/main/resources/images/cards/Binder1.pdf_Page_01.jpg");
        Image i = new Image(file.toURI().toString());

        offeredLeaders.get(0).setImage(i);
    }
}

