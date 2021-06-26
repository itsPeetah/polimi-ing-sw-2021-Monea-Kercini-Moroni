package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.listeners.PacketListener;
import it.polimi.ingsw.application.gui.GUIChat;
import it.polimi.ingsw.application.gui.GUIObserverScene;
import it.polimi.ingsw.application.gui.GUIScene;
import it.polimi.ingsw.application.gui.GUIUtility;
import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.actions.data.*;
import it.polimi.ingsw.controller.model.messages.Message;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.model.game.ResourceMarble;
import it.polimi.ingsw.model.general.Production;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.model.playerleaders.CardState;
import it.polimi.ingsw.model.singleplayer.SoloActionTokens;
import it.polimi.ingsw.util.JSONUtility;
import it.polimi.ingsw.view.data.GameData;
import it.polimi.ingsw.view.observer.GameDataObserver;
import it.polimi.ingsw.view.observer.single.LorenzoObserver;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static it.polimi.ingsw.application.gui.Materials.getMaterial;
import static it.polimi.ingsw.model.cards.CardManager.getImage;
import static it.polimi.ingsw.model.playerboard.ProductionPowers.getBasicProduction;

public class GUIMainGame implements Initializable, GameDataObserver, PacketListener,  GUIObserverScene, LorenzoObserver {

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

    // Own productions
    public ImageView prod1;
    public ImageView prod2;
    public ImageView prod3;
    public ImageView basicProd;

    // Game state
    public Label gameStateLabel;

    // Strongbox
    public Label coins;
    public Label servants;
    public Label stones;
    public Label shields;

    // Board choice box
    public ChoiceBox<String> boardChoiceBox;

    // Reports
    public ImageView report2;
    public ImageView report3;
    public ImageView report4;

    // Top menu
    public Button resourcesButton;
    public Button buyButton;
    public Button produceButton;
    public Button playLeaderButton;
    public Button discardLeaderButton;
    public Button warehouseButton;
    public Button chat;
    public HBox lorenzoHBox;
    public HBox LCHbox;
    public ImageView lorenzoImageView;
    private static Image lorenzoImage;
    public HBox chatHBox;

    // Leaders
    public ImageView lead1;
    public ImageView lead2;

    // Warehouse
    public ImageView im00;
    public ImageView im10;
    public ImageView im11;
    public ImageView im20;
    public ImageView im21;
    public ImageView im22;

    // Leaders resources
    public ImageView lead1res1;
    public ImageView lead1res2;
    public ImageView lead2res1;
    public ImageView lead2res2;

    // Faith track
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

    // Black faith track
    public ImageView c001;
    public ImageView c011;
    public ImageView c021;
    public ImageView c031;
    public ImageView c041;
    public ImageView c051;
    public ImageView c061;
    public ImageView c071;
    public ImageView c081;
    public ImageView c091;
    public ImageView c101;
    public ImageView c111;
    public ImageView c121;
    public ImageView c131;
    public ImageView c141;
    public ImageView c151;
    public ImageView c161;
    public ImageView c171;
    public ImageView c181;
    public ImageView c191;
    public ImageView c201;
    public ImageView c211;
    public ImageView c221;
    public ImageView c231;
    public ImageView c241;

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

    // Lists for a simpler use of nodes
    private final List<ImageView> faithTrack = new ArrayList<>();
    private final List<ImageView> blackTrack = new ArrayList<>();
    private final List<List<ImageView>> marketDevCards = new ArrayList<>();
    private final List<List<Sphere>> marbles = new ArrayList<>();
    private final List<List<ImageView>> rows = new ArrayList<>();
    private final List<ImageView> ownDevs = new ArrayList<>();
    private final List<List<ImageView>> leadersResources = new ArrayList<>();
    private final List<ImageView> viewsWithEffect = new ArrayList<>();
    private final List<ImageView> reportImageViews = new ArrayList<>();
    private final List<Image> reportImages = new ArrayList<>();

    // Game attributes
    private Action choice; // This attribute is accessed only in the UI thread, so there are no concurrency problems
    private DevCard chosenDev;
    private final HashSet<Production> productionsSelected = new HashSet<>();
    private AtomicReference<String> nickname = new AtomicReference<>(null);

    // Images
    private static Image cross;
    private static Image blackCross;
    private static Image leaderBack;
    private static Image report2Image;
    private static Image report3Image;
    private static Image report4Image;

    /* INITIALIZATION METHODS */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Warehouse
        rows.addAll(Arrays.asList(Collections.singletonList(im00), Arrays.asList(im10, im11), Arrays.asList(im20, im21, im22)));
        leadersResources.addAll(Arrays.asList(Arrays.asList(lead1res1, lead1res2), Arrays.asList(lead2res1, lead2res2)));

        // Own devs
        ownDevs.addAll(Arrays.asList(prod1, prod2, prod3));

        // Marbles
        marbles.addAll(Arrays.asList(Arrays.asList(marble00, marble01, marble02, marble03), Arrays.asList(marble10, marble11, marble12, marble13), Arrays.asList(marble20, marble21, marble22, marble23)));

        // Dev Card Market
        marketDevCards.addAll(Arrays.asList(Arrays.asList(dev00, dev01, dev02), Arrays.asList(dev10, dev11, dev12), Arrays.asList(dev20, dev21, dev22), Arrays.asList(dev30, dev31, dev32)));

        // Faith track
        faithTrack.addAll(Arrays.asList(c0, c01, c02, c03, c04, c05, c06, c07, c08, c09, c10, c11, c12, c13, c14, c15, c16, c17, c18, c19, c20, c21, c22, c23, c24));

        // Black track
        blackTrack.addAll(Arrays.asList(c001, c011, c021, c031, c041, c051, c061, c071, c081, c091, c101, c111, c121, c131, c141, c151, c161, c171, c181, c191, c201, c211, c221, c231, c241));

        // Initialize black cross as null
        for(ImageView blackTrackCell : blackTrack) {
            blackTrackCell.setImage(null);
        }

        // Reports
        reportImageViews.addAll(Arrays.asList(report2, report3, report4));
        reportImages.addAll(Arrays.asList(report2Image, report3Image, report4Image));

        // Apply color adjust effect to the leader nodes
        lead1.setEffect(GUIUtility.getBlackEffect());
        lead2.setEffect(GUIUtility.getBlackEffect());

        // Clear production selected
        productionsSelected.clear();
    }

    /**
     * Load the images from the files.
     */
    public static void init() {
        // Retrieve images
        String path = "images/resources/cross.png";
        InputStream is = GUIMainGame.class.getClassLoader().getResourceAsStream(path);
        cross = new Image(is);

        path = "images/solotokens/croce.png";
        is = GUIMainGame.class.getClassLoader().getResourceAsStream(path);
        blackCross = new Image(is);

        path = "images/cards/LeaderBack.png";
        is = GUIMainGame.class.getClassLoader().getResourceAsStream(path);
        leaderBack = new Image(is);

        path = "images/vaticanreports/report2.png";
        is = GUIMainGame.class.getClassLoader().getResourceAsStream(path);
        report2Image = new Image(is);

        path = "images/vaticanreports/report3.png";
        is = GUIMainGame.class.getClassLoader().getResourceAsStream(path);
        report3Image = new Image(is);

        path = "images/vaticanreports/report4.png";
        is = GUIMainGame.class.getClassLoader().getResourceAsStream(path);
        report4Image = new Image(is);

        path = "images/solotokens/retro cerchi.png";
        is = GUIMainGame.class.getClassLoader().getResourceAsStream(path);
        lorenzoImage = new Image(is);
    }

    @Override
    public void startObserver() {
        nickname.set(GameApplication.getInstance().getUserNickname());

        // Clear choice box
        boardChoiceBox.getItems().clear();

        GUIUtility.executorService.submit(() -> {
            GameData gameData = GameApplication.getInstance().getGameController().getGameData();
            gameData.getCommon().getMarketTray().setObserver(this);
            gameData.getCommon().getDevCardMarket().setObserver(this);
            gameData.getCommon().getLorenzo().setObserver(this);
            gameData.getCommon().setObserver(this);
            gameData.setObserver(this);

            for(String player: GameApplication.getInstance().getGameController().getGameData().getPlayersList()) {
                observePlayer(player);
            }
        });


        Platform.runLater(() -> {
            // Set correctly the initial username value in the choice box
            boardChoiceBox.setValue(nickname.get());
            System.out.println("GUIMainGame.startObserver: current choice box value = " + boardChoiceBox.getValue());

            // Handle chat/lorenzo box
            if(GameApplication.getInstance().getGameController().isSinglePlayer()) {
                LCHbox.getChildren().remove(chatHBox);
                LCHbox.getChildren().set(1, lorenzoHBox);
            } else {
                blackTrack.forEach(imageView -> imageView.setImage(null));
                LCHbox.getChildren().remove(lorenzoHBox);
                LCHbox.getChildren().set(1, chatHBox);
            }
        });
    }

    /**
     * Start observing a player.
     * @param player nickname of the player to observe.
     */
    private void observePlayer(String player) {
        GameData gameData = GameApplication.getInstance().getGameController().getGameData();
        gameData.getPlayerData(player).getPlayerLeaders().setObserver(this);
        gameData.getPlayerData(player).getFaithTrack().setObserver(this);
        gameData.getPlayerData(player).getWarehouse().setObserver(this);
        gameData.getPlayerData(player).getDevCards().setObserver(this);
        gameData.getPlayerData(player).getStrongbox().setObserver(this);
        gameData.getCommon().setObserver(this);
    }

    /* PACKET LISTENER METHODS */

    @Override
    public void onMessage(Message message) {
        Platform.runLater(() -> {
            switch(message) {
                case REQUIREMENTS_NOT_MET:
                case ILLEGAL_CARD_PLACE:
                case NOT_ENOUGH_RESOURCES:
                case INCORRECT_REPLACEMENT:
                case ALREADY_USED_PRIMARY_ACTION:
                case ERROR:
                case OK:
                case START_TURN:
                    gameStateLabel.setText(message.toString());
                    break;
                case WAREHOUSE_UNORGANIZED:
                    setOrganizeWarehouseScene();
                    break;
                case SELECT_OUTPUT:
                case SELECT_INPUT:
                    GUIScene.getChooseResourcesController().setMessage(message);
                    break;
                case CHOOSE_REPLACEMENT:
                    GUIScene.getChooseResourcesController().setMessage(message);
                case CHOOSE_RESOURCE:
                    setChooseResourceScene();
                    break;
                case WINNER:
                    GUIScene.getGuiEndGameController().setWin(true);
                    setEndGameScene();
                    break;
                case LOSER:
                case LOSER_MULTIPLAYER:
                    GUIScene.getGuiEndGameController().setWin(false);
                    setEndGameScene();
                    break;
            }
        });
    }

    /* DATA LISTENER METHODS */

    @Override
    public void onDevCardMarketChange() {
        GUIUtility.executorService.submit(() -> {
            DevCard[][] availableCards = GameApplication.getInstance().getGameController().getGameData().getCommon().getDevCardMarket().getAvailableCards();

            Platform.runLater(() -> {
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 3; j++) {
                        DevCard topCard = availableCards[i][j];
                        if(topCard == null) marketDevCards.get(i).get(j).setImage(null);
                        else marketDevCards.get(i).get(j).setImage(getImage(topCard.getCardId()));
                    }
                }
            });
        });
    }

    @Override
    public void onMarketTrayChange() {
        GUIUtility.executorService.submit(() -> {
            ResourceMarble[] waiting = GameApplication.getInstance().getGameController().getGameData().getCommon().getMarketTray().getWaiting();
            ResourceMarble[][] available = GameApplication.getInstance().getGameController().getGameData().getCommon().getMarketTray().getAvailable();

            Platform.runLater(() -> {
                marble.setMaterial(getMaterial(waiting[0].getMarbleColor()));
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 3; j++) {
                        PhongMaterial material = getMaterial(available[j][i].getMarbleColor());
                        Sphere marble = marbles.get(j).get(i);
                        System.out.println("GUIMainGame.onMarketTrayChange " + marble);
                        marble.setMaterial(material);
                    }
                }
            });
        });
    }

    @Override
    public void onFaithChange() {
        GUIUtility.executorService.submit(() -> {
            int tempFaithPos = GameApplication.getInstance().getGameController().getGameData().getPlayerData(getCurrentUser()).getFaithTrack().getFaith();
            int faithPos = Math.min(tempFaithPos, 24);
            Platform.runLater(() -> {
                for(ImageView faithTrackCell: faithTrack) {
                    faithTrackCell.setImage(null);
                }
                faithTrack.get(faithPos).setImage(cross);
            });
        });
    }

    @Override
    public void onReportsAttendedChange() {
        String nickname = getCurrentUser();

        GUIUtility.executorService.submit(() -> {
            System.out.println("GUIMainGame.onReportsAttendedChange");
            Boolean[] reportsAttended = GameApplication.getInstance().getGameController().getGameData().getPlayerData(nickname).getFaithTrack().getReportsAttended();
            Platform.runLater(() -> {
                for(int i = 0; i < 3; i++) {
                    System.out.println("GUIMainGame.onReportsAttendedChange: iteration " + i);
                    if(reportsAttended[i] != null && reportsAttended[i]) {
                        reportImageViews.get(i).setImage(reportImages.get(i));
                        System.out.println("GUIMainGame.onReportsAttendedChange");
                        System.out.println("GUIMainGame.onReportsAttendedChange: setting image");
                    }
                    else {
                        reportImageViews.get(i).setImage(null);
                        System.out.println("GUIMainGame.onReportsAttendedChange: setting null");
                    }
                }
            });
        });
    }

    @Override
    public void onLeadersChange() {
        String nickname = getCurrentUser();

        GUIUtility.executorService.submit(() -> {
            LeadCard[] leadCards = GameApplication.getInstance().getGameController().getGameData().getPlayerData(nickname).getPlayerLeaders().getLeaders();
            CardState[] cardStates = GameApplication.getInstance().getGameController().getGameData().getPlayerData(nickname).getPlayerLeaders().getStates();
            Platform.runLater(() -> {
                lead1.setImage(getImage(leadCards[0].getCardId()));
                lead2.setImage(getImage(leadCards[1].getCardId()));
                handleLeaderState(cardStates[0], lead1);
                handleLeaderState(cardStates[1], lead2);
            });
        });
    }

    @Override
    public void onLeadersStatesChange() {
        String nickname = getCurrentUser();

        GUIUtility.executorService.submit(() -> {
            CardState[] cardStates = GameApplication.getInstance().getGameController().getGameData().getPlayerData(nickname).getPlayerLeaders().getStates();
            Platform.runLater(() -> {
                handleLeaderState(cardStates[0], lead1);
                handleLeaderState(cardStates[1], lead2);
            });
        });
    }

    /**
     * Handle the state of a leader and apply the right effect to the corresponding image view.
     * @param cardState state of the leader.
     * @param imageView image view of the leader.
     */
    private void handleLeaderState(CardState cardState, ImageView imageView) {
        switch(cardState) {
            case PLAYED:
                imageView.setEffect(null);
                break;
            case DISCARDED:
                imageView.setImage(null);
                break;
            case INHAND:
                if(isItMe()) imageView.setEffect(GUIUtility.getBlackEffect());
                else {
                    imageView.setImage(leaderBack);
                    imageView.setEffect(null);
                }
                break;
        }
    }



    @Override
    public void onStrongboxChange() {
        String nickname = getCurrentUser();

        GUIUtility.executorService.submit(() -> {
            Resources warehouseContent = GameApplication.getInstance().getGameController().getGameData().getPlayerData(nickname).getStrongbox().getContent();
            Platform.runLater(() -> {
                coins.setText(warehouseContent.getAmountOf(ResourceType.COINS).toString());
                shields.setText(warehouseContent.getAmountOf(ResourceType.SHIELDS).toString());
                servants.setText(warehouseContent.getAmountOf(ResourceType.SERVANTS).toString());
                stones.setText(warehouseContent.getAmountOf(ResourceType.STONES).toString());
            });
        });
    }

    @Override
    public void onWarehouseContentChange() {
        String nickname = getCurrentUser();

        GUIUtility.executorService.submit(() -> {
            Resources[] warehouse = GameApplication.getInstance().getGameController().getGameData().getPlayerData(nickname).getWarehouse().getContent();
            Platform.runLater(() -> {
                for(int i = 0; i < 3; i++) {
                    Resources rowResources = warehouse[i];
                    if(rowResources != null) {
                        fillRow(rowResources, rows.get(2-i));
                    }
                }
            });
        });
    }

    /**
     * Fill a warehouse row.
     * @param resources resources to put in the row.
     * @param row row to fill.
     */
    private void fillRow(Resources resources, List<ImageView> row) {
        ResourceType resourceType = getResourceType(resources);
        if(resourceType != null) {
            int resCount = resources.getAmountOf(resourceType);
            row.stream().limit(resCount).forEach(imageView -> imageView.setImage(resourceType.getImage()));
            row.stream().skip(resCount).forEach(imageView -> imageView.setImage(null));
        } else {
            row.forEach(imageView -> imageView.setImage(null));
        }
    }

    /**
     * Get the type of resource of a <code>Resources</code> object.
     * @param resources object containing a single type of resource.
     * @return the resource type contained in the object.
     */
    private ResourceType getResourceType(Resources resources) {
        for(ResourceType resourceType: ResourceType.values()) {
            int resCount = resources.getAmountOf(resourceType);
            if(resCount > 0) return resourceType;
        }
        return null;
    }

    @Override
    public void onWarehouseExtraChange() {
        GUIUtility.executorService.submit(() -> {
            String nickname = getCurrentUser();
            List<LeadCard> leaders = Arrays.asList(GameApplication.getInstance().getGameController().getGameData().getPlayerData(nickname).getPlayerLeaders().getLeaders());
            List<LeadCard> leadersData = Arrays.asList(GameApplication.getInstance().getGameController().getGameData().getPlayerData(nickname).getWarehouse().getActivatedLeaders());
            Resources[] extra = GameApplication.getInstance().getGameController().getGameData().getPlayerData(nickname).getWarehouse().getExtra();
            Platform.runLater(() -> {
                for(int i = 0; i < 2; i++) {
                    LeadCard shownLeader = leaders.get(i);
                    if(shownLeader != null) {

                        // Find the index in the list of leaders
                        Optional<LeadCard> searchedCard = leadersData.stream().filter(leadCard -> leadCard != null && leadCard.getCardId().equals(shownLeader.getCardId())).findFirst();
                        if(searchedCard.isPresent()) {
                            int leaderIndex = leadersData.indexOf(searchedCard.get());

                            ResourceType leaderResourceType = getResourceType(shownLeader.getAbility().getExtraWarehouseSpace());
                            // If the leader has an extra space
                            if(leaderResourceType != null) {
                                // Get the current amount of extra
                                int extraAmount = extra[leaderIndex].getAmountOf(leaderResourceType);
                                // Update the leader resources
                                for(int j = 0; j < extraAmount; j++) {
                                    leadersResources.get(i).get(j).setImage(leaderResourceType.getImage());
                                }
                                for(int j = extraAmount; j < 2; j++) {
                                    leadersResources.get(i).get(j).setImage(null);
                                }
                            }
                        }
                        else {
                            for(int j = 0; j < 2; j++) {
                                leadersResources.get(i).get(j).setImage(null);
                            }
                        }
                    }
                }
            });
        });
    }

    @Override
    public void onDevCardsChange() {
        GUIUtility.executorService.submit(() -> {
            DevCard[] devCards =  GameApplication.getInstance().getGameController().getGameData().getPlayerData(getCurrentUser()).getDevCards().getDevCards();
            Platform.runLater(() -> {
                for(int i = 0; i < 3; i++) {
                    DevCard visibleCard = devCards[i];
                    if(visibleCard != null) {
                        ownDevs.get(i).setImage(getImage(visibleCard.getCardId()));
                    } else {
                        ownDevs.get(i).setImage(null);
                    }
                }
            });
        });
    }

    /**
     * React to a change to the player choice box by showing the new current user player board.
     */
    public void boardChanged(){
        String nickname = getCurrentUser();

        System.out.println(nickname);
        Platform.runLater(() -> {
            if(!isItMe()){
                setChoice(Action.NONE);
            }

            // Update nodes
            everythingChanged();
        });
    }

    /**
     * Update all the player board views.
     */
    private void everythingChanged(){
        onDevCardsChange();
        onFaithChange();
        onLeadersChange();
        onReportsAttendedChange();
        onWarehouseContentChange();
        onWarehouseExtraChange();
        onStrongboxChange();
    }

    @Override
    public void onBlackCrossChange() {
        if(GameApplication.getInstance().getGameController().isSinglePlayer()) {
            GUIUtility.executorService.submit(() -> {
                int tempCrossPos = GameApplication.getInstance().getGameController().getGameData().getCommon().getLorenzo().getBlackCross();
                int crossPos = Math.min(tempCrossPos, 24);
                Platform.runLater(() -> {
                    for (ImageView imageView : blackTrack) {
                        imageView.setImage(null);
                    }
                    blackTrack.get(crossPos).setImage(blackCross);
                });
            });
        }
    }

    @Override
    public void onLastTokenChange() {
        GUIUtility.executorService.submit(() -> {
            if(GameApplication.getInstance().getGameController().isSinglePlayer()) {
                SoloActionTokens lastToken = GameApplication.getInstance().getGameController().getGameData().getCommon().getLorenzo().getLastToken();
                if (lastToken != null) {
                    Platform.runLater(() -> lorenzoImageView.setImage(lastToken.getImage()));
                } else {
                    Platform.runLater(() -> lorenzoImageView.setImage(lorenzoImage));
                }
            }
        });
    }

    @Override
    public void onPlayerTableChange() {
        GUIUtility.executorService.submit(() -> {
            List<String> playersList = GameApplication.getInstance().getGameController().getGameData().getPlayersList();

            Platform.runLater(() -> {
                for (int i = 0; i < playersList.size(); i++) {
                    boardChoiceBox.getItems().add(i, playersList.get(i));
                }
            });
        });
    }

    @Override
    public void onCurrentPlayerChange() {
        String currentPlayer = GameApplication.getInstance().getGameController().getGameData().getCommon().getCurrentPlayer();
        Platform.runLater(() -> {
            if(currentPlayer != null && !currentPlayer.equals(nickname.get())) {
                gameStateLabel.setText("It's " + currentPlayer + "'s turn, please wait");
            }
        });
    }

    /* BUTTONS */

    // TOP MENU

    /**
     * On play leader icon click.
     * @param actionEvent
     */
    public void playLeader(ActionEvent actionEvent) {
        setChoice(Action.PLAY_LEADER);
    }

    /**
     * On discard leader icon click.
     */
    public void discardLeader() {
        setChoice(Action.DISCARD_LEADER);
    }

    /**
     * On acquire resources icon click.
     * @param actionEvent
     */
    public void acquireResources(ActionEvent actionEvent) {
        setChoice(Action.RESOURCE_MARKET);
    }

    /**
     * On reorganize warehouse icon click.
     * @param actionEvent
     */
    public void reorganizeWarehouse(ActionEvent actionEvent) {
        Platform.runLater(() -> {
            setChoice(Action.REARRANGE_WAREHOUSE);
            NoneActionData noneActionData = new NoneActionData();
            noneActionData.setPlayer(nickname.get());
            ActionPacket actionPacket = new ActionPacket(Action.REARRANGE_WAREHOUSE, JSONUtility.toJson(noneActionData, NoneActionData.class));
            GameApplication.getInstance().getGameController().getGameControllerIOHandler().notifyAction(actionPacket);
        });
    }

    /**
     * On end turn icon click.
     * @param actionEvent
     */
    public void endTurn(ActionEvent actionEvent) {
        NoneActionData noneActionData = new NoneActionData();
        noneActionData.setPlayer(nickname.get());
        ActionPacket actionPacket = new ActionPacket(Action.END_TURN, JSONUtility.toJson(noneActionData, NoneActionData.class));
        GameApplication.getInstance().getGameController().getGameControllerIOHandler().notifyAction(actionPacket);
    }

    /**
     * On open chat icon click.
     * @param actionEvent
     */
    public void openChat(ActionEvent actionEvent) {
        GUIUtility.launchChat();
    }

    /**
     * On open settings icon click.
     * @param actionEvent
     */
    public void openSettings(ActionEvent actionEvent) {
        GUIScene.GAME_SETTINGS.load();
    }

    // LEADERS

    /**
     * On first leader click.
     */
    @FXML
    public void lead1Click(){
        handleLeaderClick(0, lead1);
    }

    /**
     * On second leader click.
     */
    @FXML
    public void lead2Click(){
        handleLeaderClick(1, lead2);
    }

    /**
     * Handle a leader click according to the current selected action.
     * @param i index of the leader.
     * @param leaderImage image view of the leader.
     */
    private void handleLeaderClick(int i, ImageView leaderImage) {
        System.out.println("GUIMainGame.handleLeaderClick");
        if(choice == Action.DISCARD_LEADER){
            discardLeader(i);
        }
        if(choice == Action.PLAY_LEADER){
            playLeader(i);
        }
        if(choice == Action.PRODUCE){
            produceLeader(i, leaderImage);
        }
    }

    /**
     * Handle a leader click with production as selected action.
     * @param i index of the leader.
     * @param leaderImage image view of the leader.
     */
    private void produceLeader(int i, ImageView leaderImage){
        String nickname = getCurrentUser();

        GUIUtility.executorService.submit(() -> {
            //if player has played the leader
            if(GameApplication.getInstance().getGameController().getGameData().getPlayerData(nickname).getPlayerLeaders().getStates()[i] == CardState.PLAYED) {
                Production production = GameApplication.getInstance().getGameController().getGameData().getPlayerData(nickname).getPlayerLeaders().getLeaders()[i].getAbility().getProduction();
                if(production.getInput().getTotalAmount() != 0 || production.getOutput().getTotalAmount() != 0) {
                    Platform.runLater(() -> {
                        if(productionsSelected.contains(production)) {
                            productionsSelected.remove(production);
                            leaderImage.setEffect(null);
                        } else {
                            productionsSelected.add(production);
                            addEffect(leaderImage);
                        }
                    });
                }
            }
        });
    }

    /**
     * Handle a leader click with discard leader as selected action.
     * @param i index of the leader.
     */
    private void discardLeader(int i){

        ChooseLeaderActionData chooseLeaderActionData = new ChooseLeaderActionData(GameApplication.getInstance().getGameController().getGameData().getPlayerData(nickname.get()).getPlayerLeaders().getLeaders()[i]);
        chooseLeaderActionData.setPlayer(nickname.get());

        ActionPacket actionPacket = new ActionPacket(Action.DISCARD_LEADER, JSONUtility.toJson(chooseLeaderActionData, ChooseLeaderActionData.class));
        GameApplication.getInstance().getGameController().getGameControllerIOHandler().notifyAction(actionPacket);
    }

    /**
     * Handle a leader click with play leader as selected action.
     * @param i index of the leader.
     */
    public void playLeader(int i){
        System.out.println("GUIMainGame.playLeader");
        System.out.println("GUIMainGame.playLeader. nickname = " + nickname.get());
        System.out.println("GUIMainGame.playLeader. i = " + i);

        ChooseLeaderActionData chooseLeaderActionData = new ChooseLeaderActionData(GameApplication.getInstance().getGameController().getGameData().getPlayerData(nickname.get()).getPlayerLeaders().getLeaders()[i]);
        chooseLeaderActionData.setPlayer(nickname.get());

        ActionPacket actionPacket = new ActionPacket(Action.PLAY_LEADER, JSONUtility.toJson(chooseLeaderActionData, ChooseLeaderActionData.class));
        GameApplication.getInstance().getGameController().getGameControllerIOHandler().notifyAction(actionPacket);

        System.out.println("GUIMainGame.playLeader");
    }

    // MARKET TRAY

    /**
     * On get MT column 0 click.
     * @param actionEvent
     */
    public void acquireX0(ActionEvent actionEvent) {
        acquireResPos(false, 0);
    }

    /**
     * On get MT column 1 click.
     * @param actionEvent
     */
    public void acquireX1(ActionEvent actionEvent) {
        acquireResPos(false, 1);
    }

    /**
     * On get MT column 2 click.
     * @param actionEvent
     */
    public void acquireX2(ActionEvent actionEvent) {
        acquireResPos(false, 2);
    }

    /**
     * On get MT column 3 click.
     * @param actionEvent
     */
    public void acquireX3(ActionEvent actionEvent) {
        acquireResPos(false, 3);
    }

    /**
     * On get MT row 0 click.
     * @param actionEvent
     */
    public void acquireY0(ActionEvent actionEvent) {
        acquireResPos(true, 2);
    }

    /**
     * On get MT row 1 click.
     * @param actionEvent
     */
    public void acquireY1(ActionEvent actionEvent) {
        acquireResPos(true, 1);
    }

    /**
     * On get MT row 2 click.
     * @param actionEvent
     */
    public void acquireY2(ActionEvent actionEvent) {
        acquireResPos(true, 0);
    }

    /**
     * Acquire a line of the MT.
     * @param row whether the index corresponds to a row or to a column index.
     * @param index index of the row/column to acquire.
     */
    public void acquireResPos(boolean row, int index){
        if(choice == Action.RESOURCE_MARKET){
            ResourceMarketActionData resourceMarketActionData = new ResourceMarketActionData(row, index);
            resourceMarketActionData.setPlayer(nickname.get());

            ActionPacket actionPacket = new ActionPacket(Action.RESOURCE_MARKET, JSONUtility.toJson(resourceMarketActionData, ResourceMarketActionData.class));
            GameApplication.getInstance().getGameController().getGameControllerIOHandler().notifyAction(actionPacket);
        }
    }

    // DEV CARD MARKET

    /**
     * On dev card (0, 1) click.
     * @param mouseEvent
     */
    public void devClick01(MouseEvent mouseEvent) {
        if(choice == Action.DEV_CARD) {
            GUIUtility.executorService.submit(() -> {
                DevCard selectedDevCard = GameApplication.getInstance().getGameController().getGameData().getCommon().getDevCardMarket().getAvailableCards()[0][1];
                Platform.runLater(() -> selectMarketCard(selectedDevCard, dev01));
            });
        }
    }

    /**
     * On dev card (0, 2) click.
     * @param mouseEvent
     */
    public void devClick02(MouseEvent mouseEvent) {
        if(choice == Action.DEV_CARD) {
            GUIUtility.executorService.submit(() -> {
                DevCard selectedDevCard = GameApplication.getInstance().getGameController().getGameData().getCommon().getDevCardMarket().getAvailableCards()[0][2];
                Platform.runLater(() -> selectMarketCard(selectedDevCard, dev02));
            });
        }
    }

    /**
     * On dev card (1, 2) click.
     * @param mouseEvent
     */
    public void devClick12(MouseEvent mouseEvent) {
        if(choice == Action.DEV_CARD) {
            GUIUtility.executorService.submit(() -> {
                DevCard selectedDevCard = GameApplication.getInstance().getGameController().getGameData().getCommon().getDevCardMarket().getAvailableCards()[1][2];
                Platform.runLater(() -> selectMarketCard(selectedDevCard, dev12));
            });
        }
    }

    /**
     * On dev card (1, 1) click.
     * @param mouseEvent
     */
    public void devClick11(MouseEvent mouseEvent) {
        if(choice == Action.DEV_CARD) {
            GUIUtility.executorService.submit(() -> {
                DevCard selectedDevCard = GameApplication.getInstance().getGameController().getGameData().getCommon().getDevCardMarket().getAvailableCards()[1][1];
                Platform.runLater(() -> selectMarketCard(selectedDevCard, dev11));
            });
        }
    }

    /**
     * On dev card (3, 2) click.
     * @param mouseEvent
     */
    public void devClick32(MouseEvent mouseEvent) {
        if(choice == Action.DEV_CARD) {
            GUIUtility.executorService.submit(() -> {
                DevCard selectedDevCard = GameApplication.getInstance().getGameController().getGameData().getCommon().getDevCardMarket().getAvailableCards()[3][2];
                Platform.runLater(() -> selectMarketCard(selectedDevCard, dev32));
            });
        }
    }

    /**
     * On dev card (2, 2) click.
     * @param mouseEvent
     */
    public void devClick22(MouseEvent mouseEvent) {
        if(choice == Action.DEV_CARD) {
            GUIUtility.executorService.submit(() -> {
                DevCard selectedDevCard = GameApplication.getInstance().getGameController().getGameData().getCommon().getDevCardMarket().getAvailableCards()[2][2];
                Platform.runLater(() -> selectMarketCard(selectedDevCard, dev22));
            });
        }
    }

    /**
     * On dev card (2, 1) click.
     * @param mouseEvent
     */
    public void devClick21(MouseEvent mouseEvent) {
        if(choice == Action.DEV_CARD) {
            GUIUtility.executorService.submit(() -> {
                DevCard selectedDevCard = GameApplication.getInstance().getGameController().getGameData().getCommon().getDevCardMarket().getAvailableCards()[2][1];
                Platform.runLater(() -> selectMarketCard(selectedDevCard, dev21));
            });
        }
    }

    /**
     * On dev card (3, 1) click.
     * @param mouseEvent
     */
    public void devClick31(MouseEvent mouseEvent) {
        if(choice == Action.DEV_CARD) {
            GUIUtility.executorService.submit(() -> {
                DevCard selectedDevCard = GameApplication.getInstance().getGameController().getGameData().getCommon().getDevCardMarket().getAvailableCards()[3][1];
                Platform.runLater(() -> selectMarketCard(selectedDevCard, dev31));
            });
        }
    }

    /**
     * On dev card (0, 0) click.
     * @param mouseEvent
     */
    public void devClick00(MouseEvent mouseEvent) {
        if(choice == Action.DEV_CARD) {
            GUIUtility.executorService.submit(() -> {
                DevCard selectedDevCard = GameApplication.getInstance().getGameController().getGameData().getCommon().getDevCardMarket().getAvailableCards()[0][0];
                Platform.runLater(() -> selectMarketCard(selectedDevCard, dev00));
            });
        }
    }

    /**
     * On dev card (1, 0) click.
     * @param mouseEvent
     */
    public void devClick10(MouseEvent mouseEvent) {
        if(choice == Action.DEV_CARD) {
            GUIUtility.executorService.submit(() -> {
                DevCard selectedDevCard = GameApplication.getInstance().getGameController().getGameData().getCommon().getDevCardMarket().getAvailableCards()[1][0];
                Platform.runLater(() -> selectMarketCard(selectedDevCard, dev10));
            });
        }
    }

    /**
     * On dev card (2, 0) click.
     * @param mouseEvent
     */
    public void devClick20(MouseEvent mouseEvent) {
        if(choice == Action.DEV_CARD) {
            GUIUtility.executorService.submit(() -> {
                DevCard selectedDevCard = GameApplication.getInstance().getGameController().getGameData().getCommon().getDevCardMarket().getAvailableCards()[2][0];
                Platform.runLater(() -> selectMarketCard(selectedDevCard, dev20));
            });
        }
    }

    /**
     * On dev card (3, 0) click.
     * @param mouseEvent
     */
    public void devClick30(MouseEvent mouseEvent) {
        if(choice == Action.DEV_CARD) {
            GUIUtility.executorService.submit(() -> {
                DevCard selectedDevCard = GameApplication.getInstance().getGameController().getGameData().getCommon().getDevCardMarket().getAvailableCards()[3][0];
                Platform.runLater(() -> selectMarketCard(selectedDevCard, dev30));
            });
        }
    }

    /**
     * Apply the effects after a dev card click.
     * @param chosenDev
     * @param imageView
     */
    private void selectMarketCard(DevCard chosenDev, ImageView imageView) {
        this.chosenDev = chosenDev;
        removeAllEffects();
        addEffect(imageView);
    }

    /**
     * On buy dev card icon click.
     * @param actionEvent
     */
    public void buyDevCard(ActionEvent actionEvent) {
        setChoice(Action.DEV_CARD);
        chosenDev = null;
    }

    // OWN DEV CARDS

    /**
     * On first production click.
     * @param mouseEvent
     */
    public void prodClick1(MouseEvent mouseEvent) {
        handleProdClick(0, prod1);
    }

    /**
     * On second production click.
     * @param mouseEvent
     */
    public void prodClick2(MouseEvent mouseEvent) {
        handleProdClick(1, prod2);
    }

    /**
     * On third production click.
     * @param mouseEvent
     */
    public void prodClick3(MouseEvent mouseEvent) {
        handleProdClick(2, prod3);
    }

    /**
     * Handle a production click.
     * @param i index of the production.
     * @param prod image view of the production.
     */
    private void handleProdClick(int i, ImageView prod) {
        if(choice==Action.DEV_CARD && chosenDev!=null){
            devCardSend(chosenDev, i);
            removeAllEffects();
            chosenDev = null;
        }

        if(choice==Action.PRODUCE){
            GUIUtility.executorService.submit(() -> {
                Production production = GameApplication.getInstance().getGameController().getGameData().getPlayerData(getCurrentUser()).getDevCards().getDevCards()[i].getProduction();
                Platform.runLater(() -> {
                    // If the card was already selected, remove it
                    if(productionsSelected.contains(production)) {
                        productionsSelected.remove(production);
                        prod.setEffect(null);
                    } else {
                        productionsSelected.add(production);
                        addEffect(prod);
                    }
                });
            });
        }
    }

    /**
     * Perform a buy dev card action by sending it to the controller.
     * @param devCard
     * @param space
     */
    private void devCardSend(DevCard devCard, int space) {
        DevCardActionData devCardActionData  = new DevCardActionData(devCard, space);
        devCardActionData.setPlayer(nickname.get());

        ActionPacket actionPacket = new ActionPacket(Action.DEV_CARD, JSONUtility.toJson(devCardActionData, DevCardActionData.class));
        GameApplication.getInstance().getGameController().getGameControllerIOHandler().notifyAction(actionPacket);
    }

    /**
     * On basic production click.
     * @param mouseEvent
     */
    public void basicProdClick(MouseEvent mouseEvent) {

        if(choice==Action.PRODUCE){
            GUIUtility.executorService.submit(() -> {
                Production production = getBasicProduction();
                Platform.runLater(() -> {
                    // If the card was already selected, remove it
                    if(productionsSelected.contains(production)) {
                        productionsSelected.remove(production);
                        basicProd.setEffect(null);
                    } else {
                        productionsSelected.add(production);
                        addEffect(basicProd);
                    }
                });
            });
        }
    }

    /**
     * On produce icon click.
     * @param actionEvent
     */
    public void produce(ActionEvent actionEvent) {
        if(choice != Action.PRODUCE){
            setChoice(Action.PRODUCE);
            productionsSelected.clear();
            changeProduceButtonImage("confirmProductionButton");
        } else {
            if(!productionsSelected.isEmpty()) {
                ArrayList<Production> arrayList = new ArrayList<>(productionsSelected);
                ProduceActionData produceActionData = new ProduceActionData(arrayList);
                produceActionData.setPlayer(nickname.get());

                ActionPacket actionPacket = new ActionPacket(Action.PRODUCE, JSONUtility.toJson(produceActionData, ProduceActionData.class));
                GameApplication.getInstance().getGameController().getGameControllerIOHandler().notifyAction(actionPacket);

                changeProduceButtonImage("produceButton");
                setChoice(Action.NONE);
            }
        }
    }

    /**
     * Change the icon of the produce button.
     * @param newStyle style class to be applied to the produce button.
     */
    private void changeProduceButtonImage(String newStyle) {
        System.out.println("GUIMainGame.changeProduceButtonImage. New style = " + newStyle);
        produceButton.getStyleClass().clear();
        produceButton.getStyleClass().add(newStyle);
    }

    /* SET SCENE METHODS */

    /**
     * Set the end game scene.
     */
    private void setEndGameScene() {
        Platform.runLater(() -> {
            Stage chatWindow = GUIChat.getChatStage();
            if(chatWindow != null) chatWindow.close();
            GUIScene.showLoadingScene();
            GUIUtility.runSceneWithDelay(GUIScene.END_GAME);
        });
    }

    /**
     * Show the choose resource window.
     */
    private void setChooseResourceScene() {
        GUIUtility.launchPickResourceWindow(c0.getScene().getWindow());
    }

    /**
     * Show the set organize warehouse window.
     */
    private void setOrganizeWarehouseScene() {
        GUIUtility.launchOrganizeWarehouseWindow(c0.getScene().getWindow());
    }

    /**
     * Set the current choice.
     * @param newChoice new choice to set.
     */
    private void setChoice(Action newChoice) {
        Platform.runLater(() -> {
            if(isItMe()) {
                removeAllEffects();
            } else {
                if(newChoice != Action.NONE) {
                    System.out.println("GUIMainGame.setChoice: pre board changed");
                    boardChoiceBox.setValue(GameApplication.getInstance().getUserNickname());
                    boardChanged();
                    System.out.println("GUIMainGame.setChoice: post board changed");
                }
            }

            if(newChoice != Action.PRODUCE) {
                changeProduceButtonImage("produceButton");
            }
            choice = newChoice;

            System.out.println("GUIMainGame.setChoice: after set choice");
        });
    }

    /**
     * Get the current user of the GUI, i.e. the user whose player board is currently shown.
     * @return nickname of the current user.
     */
    private String getCurrentUser() {
        String temp = boardChoiceBox.getValue();
        return temp == null ? GameApplication.getInstance().getUserNickname() : temp;
    }

    /**
     * Check if the current shown user matches the user who is playing.
     * @return true if the player selected in the choice box of the player board view is the current player
     */
    private boolean isItMe(){
        return getCurrentUser().equals(nickname.get());
    }

    /**
     * Add a glow effect to an image view.
     * @param imageView image view to which apply the effect.
     */
    private void addEffect(ImageView imageView) {
        viewsWithEffect.add(imageView);
        imageView.setEffect(GUIUtility.getGlow());
    }

    /**
     * Remove all the effects to the image views with effects.
     */
    private void removeAllEffects() {
        viewsWithEffect.forEach(imageView -> imageView.setEffect(null));
        viewsWithEffect.clear();
    }
}
