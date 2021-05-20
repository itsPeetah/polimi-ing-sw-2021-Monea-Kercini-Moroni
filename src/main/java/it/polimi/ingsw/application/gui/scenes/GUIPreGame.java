package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionData;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.actions.data.Choose2LeadersActionData;
import it.polimi.ingsw.controller.view.game.GameState;
import it.polimi.ingsw.util.JSONUtility;
import it.polimi.ingsw.view.observer.CommonDataObserver;
import it.polimi.ingsw.application.gui.Materials;
import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.view.observer.momentary.LeadersToChooseFromObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Sphere;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import static it.polimi.ingsw.application.gui.Materials.getMaterial;
import static it.polimi.ingsw.model.cards.CardManager.getImage;

/**
 * Initial scene when game starts
 * Player will be offered 4 leaders and he should choose 2
 */

public class GUIPreGame implements Initializable, CommonDataObserver, LeadersToChooseFromObserver {

    public ImageView dev01;
    public ImageView dev02;
    public ImageView dev12;
    public ImageView dev11;
    public ImageView dev32;
    public ImageView dev22;
    public ImageView dev21;
    public ImageView dev31;
    public ImageView dev00;
    public ImageView dev10;
    public ImageView dev20;
    public ImageView dev30;

    public ImageView lead1;
    public ImageView lead2;
    public ImageView lead3;
    public ImageView lead4;
    public Button button;

    private ImageView[][] devCards = new ImageView[4][3];

    private ArrayList<ImageView> offeredLeaders = new ArrayList<ImageView>();

    private Sphere[][] marbles = new Sphere[3][4];

    private ImageView image1 = new ImageView();

    @FXML
    //The marble waiting
    private Sphere marble = new Sphere(29);

    @FXML
    //the other marbles
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


    //Instantiating the Glow class
    Glow glow = new Glow();



    //generating materials needed for the marble spheres
    Materials materials = new Materials();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //setting observer
        GameApplication.getInstance().getGameController().getGameData().getCommon().getMarketTray().setObserver(this);
        GameApplication.getInstance().getGameController().getGameData().getCommon().getDevCardMarket().setObserver(this);
        System.out.println(GameApplication.getInstance().getUserNickname());
        GameApplication.getInstance().getGameController().getGameData().getPlayerData(GameApplication.getInstance().getUserNickname()).getLeadersToChooseFrom().setObserver(this);

        //setting level of the glow effect
        glow.setLevel(0.5);

        //Connecting all marbles to matrix for simplicity
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

        //Connecting all dev cards to matrix

        devCards[0][0] = dev00;
        devCards[0][1] = dev01;
        devCards[0][2] = dev02;
        devCards[1][0] = dev10;
        devCards[1][1] = dev11;
        devCards[1][2] = dev12;
        devCards[2][0] = dev20;
        devCards[2][1] = dev21;
        devCards[2][2] = dev22;
        devCards[3][0] = dev30;
        devCards[3][1] = dev31;
        devCards[3][2] = dev32;

        // Make button inactive
        button.setDisable(true);

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
        Platform.runLater(() -> {

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    devCards[i][j].setImage(getImage(GameApplication.getInstance().getGameController().getGameData().getCommon().getDevCardMarket().getAvailableCards()[i][j].getCardId()));
                }
            }
        });
    }


    @Override
    public void onLeadersToChooseFromChange() {

        lead1.setImage(getImage(GameApplication.getInstance().getGameController().getGameData().getPlayerData(GameApplication.getInstance().getUserNickname()).getLeadersToChooseFrom().getLeaders().get(0).getCardId()));
        lead2.setImage(getImage(GameApplication.getInstance().getGameController().getGameData().getPlayerData(GameApplication.getInstance().getUserNickname()).getLeadersToChooseFrom().getLeaders().get(1).getCardId()));
        lead3.setImage(getImage(GameApplication.getInstance().getGameController().getGameData().getPlayerData(GameApplication.getInstance().getUserNickname()).getLeadersToChooseFrom().getLeaders().get(2).getCardId()));
        lead4.setImage(getImage(GameApplication.getInstance().getGameController().getGameData().getPlayerData(GameApplication.getInstance().getUserNickname()).getLeadersToChooseFrom().getLeaders().get(3).getCardId()));
    }

    boolean[] leadersSelected = new boolean[4];

    private boolean twoSelected(){
        System.out.println(GameApplication.getInstance().getGameController().getCurrentState());
        if(GameApplication.getInstance().getGameController().getCurrentState() == GameState.IDLE) return false;
        int selected = 0;
        for (int i = 0; i < 4; i++) {
            if(leadersSelected[i]){
                selected++;
            }
        }
        if(selected==2){
            button.setDisable(false);
            return true;
        }else{
            button.setDisable(true);
            return false;
        }
    }


    @FXML
    public void lead1Click(){
        if(leadersSelected[0]){
            leadersSelected[0] = false;
            lead1.setEffect(null);

        }else if (!twoSelected()){
            leadersSelected[0] = true;
            lead1.setEffect(glow);
        }
    }

    @FXML
    public void lead2Click(){
        if(leadersSelected[1]){
            leadersSelected[1] = false;
            lead2.setEffect(null);

        }else if (!twoSelected()){
            leadersSelected[1] = true;
            lead2.setEffect(glow);
        }
        twoSelected();
    }

    @FXML
    public void lead3Click(){
        if(leadersSelected[2]){
            leadersSelected[2] = false;
            lead3.setEffect(null);

        }else if (!twoSelected()){
            leadersSelected[2] = true;
            lead3.setEffect(glow);
        }
        twoSelected();
    }

    @FXML
    public void lead4Click(){
        if(leadersSelected[3]){
            leadersSelected[3] = false;
            lead4.setEffect(null);

        }else if (!twoSelected()){
            leadersSelected[3] = true;
            lead4.setEffect(glow);
        }
        twoSelected();
    }

    @FXML
    public void ready(){
        button.setDisable(true);
        int cont = 0;
        LeadCard[] actionLeaders = new LeadCard[2];
        for(int i = 0; i < 4; i++) {
            if(leadersSelected[i]) {
                LeadCard addedLeader = GameApplication.getInstance().getGameController().getGameData().getPlayerData(GameApplication.getInstance().getUserNickname()).getLeadersToChooseFrom().getLeaders().get(i);
                actionLeaders[cont] = addedLeader;
                cont++;
            }
        }
        Choose2LeadersActionData choose2LeadersActionData = new Choose2LeadersActionData();
        choose2LeadersActionData.setPlayer(GameApplication.getInstance().getUserNickname());
        choose2LeadersActionData.setLeaders(actionLeaders);

        ActionPacket actionPacket = new ActionPacket(Action.CHOOSE_2_LEADERS, JSONUtility.toJson(choose2LeadersActionData, Choose2LeadersActionData.class));
        GameApplication.getInstance().getGameController().getGameControllerIOHandler().notifyAction(actionPacket);
    }

}

