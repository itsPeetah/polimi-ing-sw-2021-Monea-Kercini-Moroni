package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.listeners.PacketListener;
import it.polimi.ingsw.application.gui.Materials;
import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.actions.data.ChooseLeaderActionData;
import it.polimi.ingsw.controller.model.actions.data.ResourceMarketActionData;
import it.polimi.ingsw.controller.model.messages.Message;
import it.polimi.ingsw.model.playerleaders.CardState;
import it.polimi.ingsw.util.JSONUtility;
import it.polimi.ingsw.view.data.GameData;
import it.polimi.ingsw.view.observer.CommonDataObserver;
import it.polimi.ingsw.view.observer.PlayerDataObserver;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Sphere;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static it.polimi.ingsw.application.gui.Materials.getMaterial;
import static it.polimi.ingsw.model.cards.CardManager.getImage;

public class GUIMainGame implements Initializable, CommonDataObserver, PacketListener, PlayerDataObserver {

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

    //faith track

    public ImageView c0;
    public ImageView c01;
    public ImageView c02;
    public ImageView c03;
    public ImageView c04;
    public ImageView c05;
    public ImageView c06;
    public ImageView c07;
    public ImageView c08;
    public ImageView c09;
    public ImageView c10;
    public ImageView c11;
    public ImageView c12;
    public ImageView c13;
    public ImageView c14;
    public ImageView c15;
    public ImageView c16;
    public ImageView c17;
    public ImageView c18;
    public ImageView c19;
    public ImageView c20;
    public ImageView c21;
    public ImageView c22;
    public ImageView c23;
    public ImageView c24;

    private ImageView[] faithTrack = new ImageView[25];

    private ImageView[][] devCards = new ImageView[4][3];

    private Sphere[][] marbles = new Sphere[3][4];

    private Action choice;

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

    //generating materials needed for the marble spheres
    Materials materials = new Materials();
    Image cross;

    @Override
    public void onMessage(Message message) {

    }

    @Override
    public void onSystemMessage(String message) {

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
    public void initialize(URL url, ResourceBundle resourceBundle) {



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

        //Instantiating the ColorAdjust class
        ColorAdjust colorAdjust = new ColorAdjust();
        //Setting the saturation value
        colorAdjust.setSaturation(-0.7);

        //Applying coloradjust effect to the leader nodes
        lead1.setEffect(colorAdjust);
        lead2.setEffect(colorAdjust);


        //Connecting faith images to list
        faithTrack[0] =  c0;
        faithTrack[1] =  c01;
        faithTrack[2] =  c02;
        faithTrack[3] =  c03;
        faithTrack[4] =  c04;
        faithTrack[5] =  c05;
        faithTrack[6] =  c06;
        faithTrack[7] =  c07;
        faithTrack[8] =  c08;
        faithTrack[9] =  c09;
        faithTrack[10] =  c10;
        faithTrack[11] =  c11;
        faithTrack[12] =  c12;
        faithTrack[13] =  c13;
        faithTrack[14] =  c14;
        faithTrack[15] =  c15;
        faithTrack[16] =  c16;
        faithTrack[17] =  c17;
        faithTrack[18] =  c18;
        faithTrack[19] =  c19;
        faithTrack[20] =  c20;
        faithTrack[21] =  c21;
        faithTrack[22] =  c22;
        faithTrack[23] =  c23;
        faithTrack[24] =  c24;

        File file = new File("src/main/resources/images/resources/cross.png");
        cross = new Image(file.toURI().toString());

        //NOTE: setListeners must be set at the end so all other initializers are already executed
        setListeners();

    }

    private void setListeners() {
        GameData gameData = GameApplication.getInstance().getGameController().getGameData();
        gameData.getCommon().getMarketTray().setObserver(this);
        gameData.getCommon().getDevCardMarket().setObserver(this);
        gameData.getPlayerData(GameApplication.getInstance().getUserNickname()).getPlayerLeaders().setObserver(this);
        gameData.getPlayerData(GameApplication.getInstance().getUserNickname()).getFaithTrack().setObserver(this);
    }

    @Override
    public void onFaithChange() {

        for (int i = 0; i < faithTrack.length; i++) {
            //System.out.println("Lopi loop");
            faithTrack[i].setImage(null);
        }
        //System.out.println(GameApplication.getInstance().getGameController().getGameData().getPlayerData(GameApplication.getInstance().getUserNickname()).getFaithTrack().getFaith());
        faithTrack[GameApplication.getInstance().getGameController().getGameData().getPlayerData(GameApplication.getInstance().getUserNickname()).getFaithTrack().getFaith()].setImage(cross);

    }

    @Override
    public void onReportsAttendedChange() {

    }

    @Override
    public void onLeadersChange() {
        lead1.setImage(getImage(GameApplication.getInstance().getGameController().getGameData().getPlayerData(GameApplication.getInstance().getUserNickname()).getPlayerLeaders().getLeaders()[0].getCardId()));
        lead2.setImage(getImage(GameApplication.getInstance().getGameController().getGameData().getPlayerData(GameApplication.getInstance().getUserNickname()).getPlayerLeaders().getLeaders()[1].getCardId()));
    }

    @Override
    public void onLeadersStatesChange() {

        System.out.println(GameApplication.getInstance().getGameController().getGameData().getPlayerData(GameApplication.getInstance().getUserNickname()).getPlayerLeaders().getStates()[0]);

        if(GameApplication.getInstance().getGameController().getGameData().getPlayerData(GameApplication.getInstance().getUserNickname()).getPlayerLeaders().getStates()[0]== CardState.DISCARDED){
            lead1.setImage(null);
        }
        if(GameApplication.getInstance().getGameController().getGameData().getPlayerData(GameApplication.getInstance().getUserNickname()).getPlayerLeaders().getStates()[1]== CardState.DISCARDED){
            lead2.setImage(null);
        }

    }

    @Override
    public void onStrongboxChange() {

    }

    @Override
    public void onWarehouseContentChange() {

    }

    @Override
    public void onWarehouseExtraChange() {

    }

    @FXML
    public void discardLeader(){
        choice = Action.DISCARD_LEADER;
    }

    @FXML
    public void lead1Click(){
        if(choice == Action.DISCARD_LEADER){
            System.out.println("Player a deciso di scartare 1");
            discardLeader(0);

        }
    }

    @FXML
    public void lead2Click(){
        if(choice == Action.DISCARD_LEADER){
            System.out.println("Player a deciso di scartare 2");
            discardLeader(1);

        }
    }

    /**
     * Method for discarding leader
     * @param i 0 lead 1, 1 lead2
     */

    public void discardLeader(int i){

        ChooseLeaderActionData chooseLeaderActionData = new ChooseLeaderActionData(GameApplication.getInstance().getGameController().getGameData().getPlayerData(GameApplication.getInstance().getUserNickname()).getPlayerLeaders().getLeaders()[i]);
        chooseLeaderActionData.setPlayer(GameApplication.getInstance().getUserNickname());

        ActionPacket actionPacket = new ActionPacket(Action.DISCARD_LEADER, JSONUtility.toJson(chooseLeaderActionData, ChooseLeaderActionData.class));
        GameApplication.getInstance().getGameController().getGameControllerIOHandler().notifyAction(actionPacket);

    }


    public void playLeader(ActionEvent actionEvent) {
    }

    public void reorganizeWarehouse(ActionEvent actionEvent) {
    }

    public void acquireResources(ActionEvent actionEvent) {
        choice = Action.RESOURCE_MARKET;
    }

    public void acquireX0(ActionEvent actionEvent) {
        acquireResPos(false, 0);
    }



    public void acquireResPos(boolean row, int index){
        ResourceMarketActionData resourceMarketActionData = new ResourceMarketActionData(row, index);
        resourceMarketActionData.setPlayer(GameApplication.getInstance().getUserNickname());

        ActionPacket actionPacket = new ActionPacket(Action.RESOURCE_MARKET, JSONUtility.toJson(resourceMarketActionData, ResourceMarketActionData.class));
        GameApplication.getInstance().getGameController().getGameControllerIOHandler().notifyAction(actionPacket);

    }
}
