package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.listeners.PacketListener;
import it.polimi.ingsw.application.gui.GUIObserverScene;
import it.polimi.ingsw.application.gui.GUIScene;
import it.polimi.ingsw.application.gui.GUIUtility;
import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.actions.data.Choose2LeadersActionData;
import it.polimi.ingsw.controller.model.messages.Message;
import it.polimi.ingsw.controller.view.GameState;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.game.ResourceMarble;
import it.polimi.ingsw.util.JSONUtility;
import it.polimi.ingsw.view.data.GameData;
import it.polimi.ingsw.view.data.common.DevCardMarket;
import it.polimi.ingsw.view.data.common.MarketTray;
import it.polimi.ingsw.view.observer.CommonDataObserver;
import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.view.observer.player.LeadersToChooseFromObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Sphere;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static it.polimi.ingsw.application.gui.Materials.getMaterial;
import static it.polimi.ingsw.model.cards.CardManager.getImage;

/**
 * Initial scene when game starts
 * Player will be offered 4 leaders and he should choose 2
 */

public class GUIPreGame implements Initializable, CommonDataObserver, LeadersToChooseFromObserver, PacketListener, GUIObserverScene {
    // Dev card market
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

    // Leaders
    public ImageView lead1;
    public ImageView lead2;
    public ImageView lead3;
    public ImageView lead4;

    // Confirm button
    public Button button;

    // Waiting marble
    @FXML
    private Sphere marble;

    // Other marbles
    @FXML
    private Sphere marble00;
    @FXML
    private Sphere marble01;
    @FXML
    private Sphere marble02;
    @FXML
    private Sphere marble03;
    @FXML
    private Sphere marble10;
    @FXML
    private Sphere marble11;
    @FXML
    private Sphere marble12;
    @FXML
    private Sphere marble13;
    @FXML
    private Sphere marble20;
    @FXML
    private Sphere marble21;
    @FXML
    private Sphere marble22;
    @FXML
    private Sphere marble23;

    // Utility lists
    private final List<List<ImageView>> marketDevCards = new ArrayList<>();
    private final List<List<Sphere>> marbles = new ArrayList<>();

    private final boolean[] leadersSelected = new boolean[4];
    private final List<ImageView> leaders = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Marbles
        marbles.addAll(Arrays.asList(Arrays.asList(marble00, marble01, marble02, marble03), Arrays.asList(marble10, marble11, marble12, marble13), Arrays.asList(marble20, marble21, marble22, marble23)));

        // Dev Card Market
        marketDevCards.addAll(Arrays.asList(Arrays.asList(dev00, dev01, dev02), Arrays.asList(dev10, dev11, dev12), Arrays.asList(dev20, dev21, dev22), Arrays.asList(dev30, dev31, dev32)));

        // Leaders
        leaders.addAll(Arrays.asList(lead1, lead2, lead3, lead4));
    }

    @Override
    public void startObserver() {
        // Make button inactive
        button.setDisable(true);

        GameData gameData = GameApplication.getInstance().getGameController().getGameData();
        String userNickname = GameApplication.getInstance().getUserNickname();
        gameData.getCommon().getMarketTray().setObserver(this);
        gameData.getCommon().getDevCardMarket().setObserver(this);
        gameData.getPlayerData(userNickname).getLeadersToChooseFrom().setObserver(this);

        // Clean view from old games
        Platform.runLater(() -> {
            Arrays.fill(leadersSelected, false);
            leaders.forEach(imageView -> imageView.setEffect(null));
        });
    }

    @Override
    public void onMarketTrayChange() {
        GUIUtility.executorService.submit(() -> {
            MarketTray marketTray = GameApplication.getInstance().getGameController().getGameData().getCommon().getMarketTray();
            ResourceMarble[] waiting = marketTray.getWaiting();
            ResourceMarble[][] available = marketTray.getAvailable();
            Platform.runLater(() -> {
                marble.setMaterial(getMaterial(waiting[0].getMarbleColor()));
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 3; j++) {
                        marbles.get(j).get(i).setMaterial(getMaterial(available[j][i].getMarbleColor()));
                    }
                }
            });
        });
    }


    @Override
    public void onDevCardMarketChange() {
        GUIUtility.executorService.submit(() -> {
            DevCardMarket devCardMarket = GameApplication.getInstance().getGameController().getGameData().getCommon().getDevCardMarket();
            DevCard[][] availableCards = devCardMarket.getAvailableCards();
            Platform.runLater(() -> {
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 3; j++) {
                        marketDevCards.get(i).get(j).setImage(getImage(availableCards[i][j].getCardId()));
                    }
                }
            });
        });
    }


    @Override
    public void onLeadersToChooseFromChange() {
        GUIUtility.executorService.submit(() -> {
            List<LeadCard> leadCardList = GameApplication.getInstance().getGameController().getGameData().getPlayerData(GameApplication.getInstance().getUserNickname()).getLeadersToChooseFrom().getLeaders();
            Platform.runLater(() -> {
                lead1.setImage(getImage(leadCardList.get(0).getCardId()));
                lead2.setImage(getImage(leadCardList.get(1).getCardId()));
                lead3.setImage(getImage(leadCardList.get(2).getCardId()));
                lead4.setImage(getImage(leadCardList.get(3).getCardId()));
            });
        });
    }

    /**
     * Check if the ready button is clickable.
     * If so, activate the button.
     * Otherwise, disable it.
     * @return
     */
    private boolean isReadyClickable(){
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


    /**
     * On first leader click.
     */
    @FXML
    public void lead1Click(){
        handleLeadClick(0);
    }

    /**
     * On second leader click.
     */
    @FXML
    public void lead2Click(){
        handleLeadClick(1);
    }

    /**
     * On third leader click.
     */
    @FXML
    public void lead3Click(){
        handleLeadClick(2);
    }

    /**
     * On fourth leader click.
     */
    @FXML
    public void lead4Click(){
        handleLeadClick(3);
    }

    /**
     * Handle the leader click action.
     * @param index
     */
    private void handleLeadClick(int index) {
        if(leadersSelected[index]){
            leadersSelected[index] = false;
            leaders.get(index).setEffect(null);

        } else if (!isReadyClickable()){
            leadersSelected[index] = true;
            leaders.get(index).setEffect(GUIUtility.getGlow());
        }
        isReadyClickable();
    }

    /**
     * On ready button click.
     */
    @FXML
    public void ready(){
        button.setDisable(true);
        GUIUtility.executorService.submit(() -> {
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
        });
    }

    @Override
    public void onMessage(Message message) {
        Platform.runLater(() -> {
            switch (message) {
                case CHOOSE_LEADERS:
                    isReadyClickable();
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

    /**
     * Show the set organize warehouse window.
     */
    private void setOrganizeWarehouseUI() {
        GUIUtility.launchOrganizeWarehouseWindow(button.getScene().getWindow());
    }

    /**
     * Show the choose resource window.
     */
    private void setChooseResourceUI() {
        GUIUtility.launchPickResourceWindow(button.getScene().getWindow());
    }

    /**
     * Set the main game scene.
     */
    private void setGameScene() {
        GUIScene.showLoadingScene();
        GUIUtility.runSceneWithDelay(GUIScene.MAIN_GAME);
    }
}

