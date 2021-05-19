package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.observer.MarketTrayObserver;
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

public class GUIPreGame implements Initializable, MarketTrayObserver {

    private ArrayList<ImageView> offeredLeaders = new ArrayList<ImageView>();



    @FXML
    private ImageView image1 = new ImageView();

    @FXML
    private Sphere marble = new Sphere(29);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GameApplication.getInstance().getGameController().getGameData().getCommon().getMarketTray().setObserver(this);

        Materials materials = new Materials();

        //marble.setMaterial(getMaterial(MaterialsEnum.BLUE));

        offeredLeaders.add(image1);
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
            System.out.println("onMarketTrayChange triggered");
        });

    }
    /*
    @Override
    public void onDevCardMarketChange() {

    }*/


}

