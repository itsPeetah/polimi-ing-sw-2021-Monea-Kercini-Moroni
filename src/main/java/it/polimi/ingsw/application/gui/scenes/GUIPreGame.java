package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.model.cards.LeadCard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Initial scene when game starts
 * Player will be offered 4 leaders and he should choose 2
 */

public class GUIPreGame implements Initializable {

    @FXML
    private ArrayList<ImageView> offeredLeaders = new ArrayList<ImageView>();
    private Image image1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //setImageTest();
        Image j = new Image("file:Binder1.pdf_Page_01.jpg");

        /**
         {
         try {
         j = new Image(new FileInputStream("../../../../resources/images/cards/Binder1.pdf_Page_01.jpg"));
         } catch (FileNotFoundException e) {
         e.printStackTrace();
         }
         }
         */

        ImageView image1 = new ImageView(j);


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

//Image image = new Image(new FileInputStream("url for the image));

        Image i = null;
        try {
            i = new Image(new FileInputStream("../../../../resources/images/cards/Binder1.pdf_Page_01.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        offeredLeaders.get(0).setImage(i);

        //image1 = offeredLeaders.get(0);
    }
}

