package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.view.observer.CommonDataObserver;
import it.polimi.ingsw.application.gui.Materials;
import it.polimi.ingsw.model.cards.LeadCard;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Sphere;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static it.polimi.ingsw.application.gui.Materials.getMaterial;

/**
 * Initial scene when game starts
 * Player will be offered 4 leaders and he should choose 2
 */

public class GUIPreGame implements Initializable, CommonDataObserver {

    private ArrayList<ImageView> offeredLeaders = new ArrayList<ImageView>();

    private Sphere[][] marbles = new Sphere[3][4];



    @FXML
    private ImageView image1 = new ImageView();

    //The marble waiting
    @FXML
    private Sphere marble = new Sphere(29);

    //the other marbles
    @FXML
    private Sphere marble00 = new Sphere(29);

    @FXML
    private Sphere marble01 = new Sphere(29);

    @FXML
    private Sphere marble02 = new Sphere(29);

    @FXML
    private Sphere marble03 = new Sphere(29);

    @FXML
    private Sphere marble10 = new Sphere(29);

    @FXML
    private Sphere marble11 = new Sphere(29);

    @FXML
    private Sphere marble12 = new Sphere(29);

    @FXML
    private Sphere marble13 = new Sphere(29);

    @FXML
    private Sphere marble20 = new Sphere(29);

    @FXML
    private Sphere marble21 = new Sphere(29);

    @FXML
    private Sphere marble22 = new Sphere(29);

    @FXML
    private Sphere marble23 = new Sphere(29);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //setting observer
        GameApplication.getInstance().getGameController().getGameData().getCommon().getMarketTray().setObserver(this);

        //generating materials needed for the marble spheres
        Materials materials = new Materials();

        marbles[0][0] = marble00;
        marbles[0][1] = marble01;
        marbles[0][2] = marble02;
        marbles[0][3] = marble03;
        marbles[1][0] = marble10;
        marbles[1][1] = marble11;
        marbles[1][2] = marble12;
        marbles[1][3] = marble13;
        marbles[2][0] = marble20;
        marbles[2][1] = marble21;
        marbles[2][2] = marble22;
        marbles[2][3] = marble23;

        //offeredLeaders.add(image1);
        //setImageTest();

        //marble.setMaterial(getMaterial(MaterialsEnum.PURPLE));
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


    @Override
    public void onMarketTrayChange() {
        Platform.runLater(() -> {

            marble.setMaterial(getMaterial(GameApplication.getInstance().getGameController().getGameData().getCommon().getMarketTray().getWaiting()[0].getMarbleColor()));

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    marbles[j][i].setMaterial(getMaterial(GameApplication.getInstance().getGameController().getGameData().getCommon().getMarketTray().getAvailable()[j][i].getMarbleColor()));
                }
            }

        });

    }


    @Override
    public void onDevCardMarketChange() {

    }


}

