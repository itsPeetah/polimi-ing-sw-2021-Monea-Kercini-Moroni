package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.listeners.PacketListener;
import it.polimi.ingsw.application.gui.GUIScene;
import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.actions.data.Choose2LeadersActionData;
import it.polimi.ingsw.controller.model.messages.Message;
import it.polimi.ingsw.controller.view.game.GameState;
import it.polimi.ingsw.util.JSONUtility;
import it.polimi.ingsw.view.data.GameData;
import it.polimi.ingsw.view.data.common.DevCardMarket;
import it.polimi.ingsw.view.data.common.MarketTray;
import it.polimi.ingsw.view.observer.CommonDataObserver;
import it.polimi.ingsw.application.gui.Materials;
import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.view.observer.momentary.LeadersToChooseFromObserver;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Sphere;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static it.polimi.ingsw.application.gui.GUIApplication.ICON_PATH;
import static it.polimi.ingsw.application.gui.GUIScene.MAIN_GAME;
import static it.polimi.ingsw.application.gui.Materials.getMaterial;
import static it.polimi.ingsw.model.cards.CardManager.getImage;

/**
 * Initial scene when game starts
 * Player will be offered 4 leaders and he should choose 2
 */

public class GUIPreGame implements Initializable, CommonDataObserver, LeadersToChooseFromObserver, PacketListener {

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set listeners

        setListeners();

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
    }



    private void setListeners() {
        GameData gameData = GameApplication.getInstance().getGameController().getGameData();
        gameData.getCommon().getMarketTray().setObserver(this);
        gameData.getCommon().getDevCardMarket().setObserver(this);
        gameData.getPlayerData(GameApplication.getInstance().getUserNickname()).getLeadersToChooseFrom().setObserver(this);
    }

    Materials materials = new Materials();

    @Override
    public void onMarketTrayChange() {
        new Thread(() -> {
            MarketTray marketTray = GameApplication.getInstance().getGameController().getGameData().getCommon().getMarketTray();
            Platform.runLater(() -> {
                marble.setMaterial(getMaterial(marketTray.getWaiting()[0].getMarbleColor()));

                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 3; j++) {
                        marbles[j][i].setMaterial(getMaterial(marketTray.getAvailable()[j][i].getMarbleColor()));
                    }
                }
            });
        }).start();

    }


    @Override
    public void onDevCardMarketChange() {
        new Thread(() -> {
            DevCardMarket devCardMarket = GameApplication.getInstance().getGameController().getGameData().getCommon().getDevCardMarket();
            Platform.runLater(() -> {
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 3; j++) {
                        devCards[i][j].setImage(getImage(devCardMarket.getAvailableCards()[i][j].getCardId()));
                    }
                }
            });
        }).start();
    }


    @Override
    public void onLeadersToChooseFromChange() {
        new Thread(() -> {
            List<LeadCard> leadCardList = GameApplication.getInstance().getGameController().getGameData().getPlayerData(GameApplication.getInstance().getUserNickname()).getLeadersToChooseFrom().getLeaders();
            Platform.runLater(() -> {
                lead1.setImage(getImage(leadCardList.get(0).getCardId()));
                lead2.setImage(getImage(leadCardList.get(1).getCardId()));
                lead3.setImage(getImage(leadCardList.get(2).getCardId()));
                lead4.setImage(getImage(leadCardList.get(3).getCardId()));
            });
        }).start();
    }

    boolean[] leadersSelected = new boolean[4];

    private boolean isReadyClickable(){
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

        } else if (!isReadyClickable()){
            leadersSelected[0] = true;
            lead1.setEffect(glow);
        }
        isReadyClickable();
    }

    @FXML
    public void lead2Click(){
        if(leadersSelected[1]){
            leadersSelected[1] = false;
            lead2.setEffect(null);

        } else if (!isReadyClickable()){
            leadersSelected[1] = true;
            lead2.setEffect(glow);
        }
        isReadyClickable();
    }

    @FXML
    public void lead3Click(){
        if(leadersSelected[2]){
            leadersSelected[2] = false;
            lead3.setEffect(null);

        } else if (!isReadyClickable()){
            leadersSelected[2] = true;
            lead3.setEffect(glow);
        }
        isReadyClickable();
    }

    @FXML
    public void lead4Click(){
        if(leadersSelected[3]){
            leadersSelected[3] = false;
            lead4.setEffect(null);

        } else if (!isReadyClickable()){
            leadersSelected[3] = true;
            lead4.setEffect(glow);
        }
        isReadyClickable();
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

    @Override
    public void onMessage(Message message) {
        Platform.runLater(() -> {
            switch (message) {
                case CHOOSE_LEADERS:
                    setChooseLeadersUI();
                    break;
                case CHOOSE_RESOURCE:
                    setChooseResourceUI();
                    break;
                case GAME_HAS_STARTED:
                    setGameScene();
                    break;
                case WAREHOUSE_UNORGANIZED:
                    setOrganizeWarehouseUI();
                    break;
            }
        });
    }

    private void setOrganizeWarehouseUI() {
        GUIUtility.launchOrganizeWarehouseWindow(button.getScene().getWindow());
    }

    private void setChooseResourceUI() {
        GUIUtility.launchPickResourceWindow(button.getScene().getWindow());
    }

    private void setChooseLeadersUI() {
        isReadyClickable();
    }

    private void setGameScene() {
        GUIScene.showLoadingScene();
        new Thread(() -> {
            GUIUtility.runSceneWithDelay(GUIScene.MAIN_GAME, 2000);
        }).start();
    }

    @Override
    public void onSystemMessage(String message) {
        // TODO do we need to handle system messages here?
    }
}

