package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.gui.GUIUtility;
import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.actions.data.PutResourcesActionData;
import it.polimi.ingsw.model.cards.CardManager;
import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.util.JSONUtility;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class GUIWarehouse implements Initializable {
    // RESOURCES
    public ImageView coin;
    public ImageView servant;
    public ImageView shield;
    public ImageView stone;
    public Label coinsCount;
    public Label servantsCount;
    public Label shieldsCount;
    public Label stonesCount;
    public HBox lead1HBox;
    public HBox lead2HBox;

    // LEADERS
    public ImageView lead1;
    public ImageView lead2;
    public ImageView lead1res1;
    public ImageView lead1res2;
    public ImageView lead2res1;
    public ImageView lead2res2;

    // WAREHOUSE
    public ImageView im00;
    public ImageView im10;
    public ImageView im11;
    public ImageView im20;
    public ImageView im21;
    public ImageView im22;

    // HELPER LISTS
    private final List<ImageView> resources = new ArrayList<>();
    private final List<ImageView> firstRow = new ArrayList<>();
    private final List<ImageView> secondRow = new ArrayList<>();
    private final List<ImageView> thirdRow = new ArrayList<>();
    private final List<List<ImageView>> rows = new ArrayList<>();
    private final List<ResourceType> rowsResourceTypes = new ArrayList<>();
    private final HashMap<ResourceType, Label> remainingResources = new HashMap<>();
    private final List<ImageView> leaders = new ArrayList<>();
    private final List<List<ImageView>> leadersResources = new ArrayList<>();
    private final List<ResourceType> leadersResourceTypes = new ArrayList<>();
    private final List<HBox> leadersHBox = new ArrayList<>();

    private ResourceType selectedResource;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        coinClick(null);

        resources.addAll(Arrays.asList(coin, shield, servant, stone));

        firstRow.add(im00);
        secondRow.addAll(Arrays.asList(im10, im11));
        thirdRow.addAll(Arrays.asList(im20, im21, im22));
        rows.addAll(Arrays.asList(firstRow, secondRow, thirdRow));
        rowsResourceTypes.addAll(Arrays.asList(null, null, null));

        remainingResources.put(ResourceType.COINS, coinsCount);
        remainingResources.put(ResourceType.SERVANTS, servantsCount);
        remainingResources.put(ResourceType.STONES, stonesCount);
        remainingResources.put(ResourceType.SHIELDS, shieldsCount);

        leaders.addAll(Arrays.asList(lead1, lead2));

        leadersResources.addAll(Arrays.asList(Arrays.asList(lead1res1, lead1res2), Arrays.asList(lead2res1, lead2res2)));
        leadersResourceTypes.addAll(Arrays.asList(null, null));

        leadersHBox.addAll(Arrays.asList(lead1HBox, lead2HBox));

        fillWarehouse();
        fillRemainingResources();
        fillLeaders();
    }

    /* BUTTONS */

    // CONFIRM

    /**
     * On confirm button click.
     * @param actionEvent
     */
    public void onConfirmClick(ActionEvent actionEvent) {
        GUIUtility.executorService.submit(() -> {
            String nickname = GameApplication.getInstance().getUserNickname();
            LeadCard[] leadCards = GameApplication.getInstance().getGameController().getGameData().getPlayerData(nickname).getWarehouse().getActivatedLeaders();
            List<LeadCard> playerLeaders = Arrays.asList(GameApplication.getInstance().getGameController().getGameData().getPlayerData(nickname).getPlayerLeaders().getLeaders());
            PutResourcesActionData putResourcesActionData = new PutResourcesActionData();
            putResourcesActionData.setPlayer(nickname);
            Warehouse sentWarehouse = new Warehouse();
            // Create warehouse to send
            for(int i=0; i<3; i++) {
                Resources floorResources = new Resources();
                int resCount = (int)rows.get(2-i).stream().filter(imageView -> imageView.getImage() != null).count();
                ResourceType resType = rowsResourceTypes.get(2-i);
                if(resCount > 0) {
                    floorResources.add(resType, resCount);
                }
                sentWarehouse.deposit(floorResources,i);
            }
            // Create leader extra to send
            for(int i=0; i<2; i++) {
                LeadCard activatedLeader = leadCards[i];
                if(activatedLeader != null) {
                    Optional<LeadCard> searchedCard = playerLeaders.stream().filter(leadCard -> leadCard != null && leadCard.getCardId().equals(activatedLeader.getCardId())).findFirst();
                    assert searchedCard.isPresent();
                    int leadIndex = playerLeaders.indexOf(searchedCard.get());
                    int resCount = (int)leadersResources.get(leadIndex).stream().filter(imageView -> imageView.getImage() != null).count();
                    ResourceType resType = leadersResourceTypes.get(leadIndex);
                    System.out.println("GUIWarehouse.onConfirmClick: " + resCount + " of " + resType);
                    Resources extraResources = new Resources();
                    extraResources.add(resType, resCount);
                    sentWarehouse.deposit(extraResources,i+3);
                    sentWarehouse.expandWithLeader(activatedLeader);
                }
            }
            System.out.println("GUIWarehouse.onConfirmClick leaders = ");
            Arrays.stream(sentWarehouse.getLeadersExtra()).forEach(System.out::println);
            putResourcesActionData.setWh(sentWarehouse);

            // Send packet
            ActionPacket actionPacket = new ActionPacket(Action.PUT_RESOURCES, JSONUtility.toJson(putResourcesActionData, PutResourcesActionData.class));
            GameApplication.getInstance().getGameController().getGameControllerIOHandler().notifyAction(actionPacket);
        });


        Stage s = (Stage) coin.getScene().getWindow();
        s.close();
    }

    // RESOURCES

    /**
     * On coin icon button click.
     * @param mouseEvent
     */
    public void coinClick(MouseEvent mouseEvent) {
        selectedResource = ResourceType.COINS;
        if(!GUIUtility.getGlow().equals(coin.getEffect())) {
            resources.forEach(image -> image.setEffect(null));
            coin.setEffect(GUIUtility.getGlow());
        }
    }

    /**
     * On servant icon button click.
     * @param mouseEvent
     */
    public void servantClick(MouseEvent mouseEvent) {
        selectedResource = ResourceType.SERVANTS;
        if(!GUIUtility.getGlow().equals(servant.getEffect())) {
            resources.forEach(image -> image.setEffect(null));
            servant.setEffect(GUIUtility.getGlow());
        }
    }

    /**
     * On shield icon click.
     * @param mouseEvent
     */
    public void shieldClick(MouseEvent mouseEvent) {
        selectedResource = ResourceType.SHIELDS;
        if(!GUIUtility.getGlow().equals(shield.getEffect())) {
            resources.forEach(image -> image.setEffect(null));
            shield.setEffect(GUIUtility.getGlow());
        }
    }

    /**
     * On stone icon click.
     * @param mouseEvent
     */
    public void stoneClick(MouseEvent mouseEvent) {
        selectedResource = ResourceType.STONES;
        if(!GUIUtility.getGlow().equals(stone.getEffect())) {
            resources.forEach(image -> image.setEffect(null));
            stone.setEffect(GUIUtility.getGlow());
        }
    }

    // SLOTS

    /**
     * On slot (0, 0) click.
     * @param mouseEvent
     */
    public void onIm00Click(MouseEvent mouseEvent) {
        handleWarehouseClick(0, 0);
    }

    /**
     * On slot (1, 0) click.
     * @param mouseEvent
     */
    public void onIm10Click(MouseEvent mouseEvent) {
        handleWarehouseClick(1, 0);
    }

    /**
     * On slot (1, 1) click.
     * @param mouseEvent
     */
    public void onIm11Click(MouseEvent mouseEvent) {
        handleWarehouseClick(1, 1);
    }

    /**
     * On slot (2, 0) click.
     * @param mouseEvent
     */
    public void onIm20Click(MouseEvent mouseEvent) {
        handleWarehouseClick(2, 0);
    }

    /**
     * On slot (2, 1) click.
     * @param mouseEvent
     */
    public void onIm21Click(MouseEvent mouseEvent) {
        handleWarehouseClick(2, 1);
    }

    /**
     * On slot (2, 2) click.
     * @param mouseEvent
     */
    public void onIm22Click(MouseEvent mouseEvent) {
        handleWarehouseClick(2, 2);
    }

    /**
     * On leader 1 slot 1 click.
     * @param mouseEvent
     */
    public void onLead1Res1Click(MouseEvent mouseEvent) {
        handleLeaderClick(0, 0);
    }

    /**
     * On leader 1 slot 2 click.
     * @param mouseEvent
     */
    public void onLead1Res2Click(MouseEvent mouseEvent) {
        handleLeaderClick(0, 1);
    }

    /**
     * On leader 2 slot 1 click.
     * @param mouseEvent
     */
    public void onLead2Res1Click(MouseEvent mouseEvent) {
        handleLeaderClick(1, 0);
    }

    /**
     * On leader 2 slot 2 click.
     * @param mouseEvent
     */
    public void onLead2Res2Click(MouseEvent mouseEvent) {
        handleLeaderClick(1, 1);
    }

    /* UTILITY METHODS */

    /**
     * Handle a warehouse slot click.
     * @param row row of the slot.
     * @param column column of the slot.
     */
    private void handleWarehouseClick(int row, int column) {
        ImageView clickedImage = rows.get(row).get(column);
        // If the image view is already filled with an image, remove the resource
        if(clickedImage.getImage() != null) {
            clickedImage.setImage(null);
            // Update the remaining resources
            updateLabel(remainingResources.get(rowsResourceTypes.get(row)), +1);
            // Remove the image
            if(rows.get(row).stream().allMatch(imageView -> imageView.getImage() == null)) rowsResourceTypes.set(row, null);
        }
        // If the image view is empty, add the resource
        else {
            // If the remaining resources of this type are 0, do nothing
            if(Integer.parseInt(remainingResources.get(selectedResource).getText()) > 0) {
                // If the row has no resource types, check if the resource type is already used. In this case, do nothing, otherwise, set the new resource type of the row
                if (rowsResourceTypes.get(row) == null && rowsResourceTypes.stream().noneMatch(resourceType -> resourceType == selectedResource)) {
                    rowsResourceTypes.set(row, selectedResource);
                    clickedImage.setImage(selectedResource.getImage());
                    updateLabel(remainingResources.get(rowsResourceTypes.get(row)), -1);
                } else {
                    if (rowsResourceTypes.get(row) == selectedResource) {
                        clickedImage.setImage(selectedResource.getImage());
                        updateLabel(remainingResources.get(rowsResourceTypes.get(row)), -1);
                    }
                }
            }
        }
    }

    /**
     * Handle a leader slot click.
     * @param leaderIndex index of the leader.
     * @param slotIndex index of the slot.
     */
    private void handleLeaderClick(int leaderIndex, int slotIndex) {
        ImageView clickedImage = leadersResources.get(leaderIndex).get(slotIndex);

        // If the image view is already filled with an image, remove the resource
        if(clickedImage.getImage() != null) {
            clickedImage.setImage(null);
            // Update the remaining resources
            updateLabel(remainingResources.get(leadersResourceTypes.get(leaderIndex)), +1);
        }
        // If the image view is empty, add the resource
        else {
            // If the remaining resources of this type are 0, do nothing
            if(leadersResourceTypes.get(leaderIndex) == selectedResource && Integer.parseInt(remainingResources.get(selectedResource).getText()) > 0) {
                // If the row has no resource types, check if the resource type is already used. In this case, do nothing, otherwise, set the new resource type of the row
                clickedImage.setImage(selectedResource.getImage());
                updateLabel(remainingResources.get(leadersResourceTypes.get(leaderIndex)), -1);
            }
        }
    }

    /**
     * Update a resource label.
     * @param remainingLabel label to be updated.
     * @param increment increment to be applied.
     */
    private void updateLabel(Label remainingLabel, int increment) {
        remainingLabel.setText(Integer.toString(Integer.parseInt(remainingLabel.getText()) + increment));
    }

    /**
     * Fill the warehouse with the resources in view data.
     */
    private void fillWarehouse() {
        String nickname = GameApplication.getInstance().getUserNickname();
        GUIUtility.executorService.submit(() -> {
            Resources[] warehouse = GameApplication.getInstance().getGameController().getGameData().getPlayerData(nickname).getWarehouse().getContent();
            Platform.runLater(() -> {
                for(int i = 0; i < 3; i++) {
                    Resources rowResources = warehouse[i];
                    if(rowResources != null) fillRow(rowResources, rows.get(2-i));
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
            rowsResourceTypes.set(rows.indexOf(row), resourceType);
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

    /**
     * Fill the labels with the resources to put.
     */
    private void fillRemainingResources() {
        GUIUtility.executorService.submit(() -> {
            Resources resourcesToPut = GameApplication.getInstance().getGameController().getGameData().getMomentary().getResourcesToPut().getRes();
            Platform.runLater(() -> {
                remainingResources.forEach((resourceType, label) -> label.setText(Integer.toString(0)));
                for(ResourceType resourceType: ResourceType.values()) {
                    int resCount = resourcesToPut.getAmountOf(resourceType);
                    if(resCount > 0) {
                        updateLabel(remainingResources.get(resourceType), resCount);
                    }
                }
            });
        });
    }

    /**
     * Fill the leaders.with the resources in view data.
     */
    private void fillLeaders() {
        String nickname = GameApplication.getInstance().getUserNickname();

        GUIUtility.executorService.submit(() -> {
            List<LeadCard> playerLeaders = Arrays.asList(GameApplication.getInstance().getGameController().getGameData().getPlayerData(nickname).getPlayerLeaders().getLeaders());
            List<LeadCard> leadersData = Arrays.asList(GameApplication.getInstance().getGameController().getGameData().getPlayerData(nickname).getWarehouse().getActivatedLeaders());
            Resources[] extra = GameApplication.getInstance().getGameController().getGameData().getPlayerData(nickname).getWarehouse().getExtra();
            Platform.runLater(() -> {
                for(int i = 0; i < 2; i++) {
                    LeadCard shownLeader = playerLeaders.get(i);
                    if(shownLeader != null) {

                        // Find the index in the list of leaders
                        Optional<LeadCard> searchedCard = leadersData.stream().filter(leadCard -> leadCard != null && leadCard.getCardId().equals(shownLeader.getCardId())).findFirst();
                        if(searchedCard.isPresent()) {
                            System.out.println("GUIWarehouse.fillLeaders: activate leader");
                            int leaderIndex = leadersData.indexOf(searchedCard.get());
                            System.out.println("GUIWarehouse.fillLeaders: activate leader index = " + leaderIndex);
                            ResourceType leaderResourceType = getResourceType(shownLeader.getAbility().getExtraWarehouseSpace());
                            HBox leaderHBox = leadersHBox.get(i);
                            leaderHBox.setVisible(true);
                            leaderHBox.setDisable(false);
                            // Get the current amount of extra
                            int extraAmount = extra[leaderIndex].getAmountOf(leaderResourceType);
                            // Update the leader image
                            leaders.get(i).setImage(CardManager.getImage(shownLeader.getCardId()));
                            // Update the leader resources
                            for(int j = 0; j < extraAmount; j++) {
                                leadersResources.get(i).get(j).setImage(leaderResourceType.getImage());
                            }
                            for(int j = extraAmount; j < 2; j++) {
                                leadersResources.get(i).get(j).setImage(null);
                            }
                            // Update the leader resource type
                            leadersResourceTypes.set(i, leaderResourceType);
                        }
                        else {
                            System.out.println("GUIWarehouse.fillLeaders: disable leader");
                            for(int j = 0; j < 2; j++) {
                                leadersResources.get(i).get(j).setImage(null);
                            }
                            leaders.get(i).setImage(null);
                            HBox leaderHBox = leadersHBox.get(i);
                            leaderHBox.setVisible(false);
                            leaderHBox.setDisable(true);
                        }
                    }
                }
            });
        });
    }
}
