package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.actions.data.Choose2LeadersActionData;
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
import javafx.scene.effect.Glow;
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
    private Glow glow;

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
        glow = new Glow();
        glow.setLevel(0.8);

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

    public void onConfirmClick(ActionEvent actionEvent) {
        String nickname = GameApplication.getInstance().getUserNickname();
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
            Resources extraResources = new Resources();
            int resCount = (int)leadersResources.get(i).stream().filter(imageView -> imageView.getImage() != null).count();
            ResourceType resType = leadersResourceTypes.get(i);
            if(resCount > 0) {
                extraResources.add(resType, resCount);
            }
            sentWarehouse.deposit(extraResources,i);
        }
        putResourcesActionData.setWh(sentWarehouse);

        // Send packet
        ActionPacket actionPacket = new ActionPacket(Action.PUT_RESOURCES, JSONUtility.toJson(putResourcesActionData, PutResourcesActionData.class));
        GameApplication.getInstance().getGameController().getGameControllerIOHandler().notifyAction(actionPacket);

        Stage s = (Stage) coin.getScene().getWindow();
        s.close();
    }


    public void coinClick(MouseEvent mouseEvent) {
        selectedResource = ResourceType.COINS;
        if(!glow.equals(coin.getEffect())) {
            resources.forEach(image -> image.setEffect(null));
            coin.setEffect(glow);
        }
    }

    public void servantClick(MouseEvent mouseEvent) {
        selectedResource = ResourceType.SERVANTS;
        if(!glow.equals(servant.getEffect())) {
            resources.forEach(image -> image.setEffect(null));
            servant.setEffect(glow);
        }
    }

    public void shieldClick(MouseEvent mouseEvent) {
        selectedResource = ResourceType.SHIELDS;
        if(!glow.equals(shield.getEffect())) {
            resources.forEach(image -> image.setEffect(null));
            shield.setEffect(glow);
        }
    }

    public void stoneClick(MouseEvent mouseEvent) {
        selectedResource = ResourceType.STONES;
        if(!glow.equals(stone.getEffect())) {
            resources.forEach(image -> image.setEffect(null));
            stone.setEffect(glow);
        }
    }

    public void onIm00Click(MouseEvent mouseEvent) {
        handleWarehouseClick(0, 0);
    }

    public void onIm10Click(MouseEvent mouseEvent) {
        handleWarehouseClick(1, 0);
    }

    public void onIm11Click(MouseEvent mouseEvent) {
        handleWarehouseClick(1, 1);
    }

    public void onIm20Click(MouseEvent mouseEvent) {
        handleWarehouseClick(2, 0);
    }

    public void onIm21Click(MouseEvent mouseEvent) {
        handleWarehouseClick(2, 1);
    }

    public void onIm22Click(MouseEvent mouseEvent) {
        handleWarehouseClick(2, 2);
    }

    public void onLead1Res1Click(MouseEvent mouseEvent) {
        handleLeaderClick(0, 0);
    }

    public void onLead1Res2Click(MouseEvent mouseEvent) {
        handleLeaderClick(0, 1);
    }

    public void onLead2Res1Click(MouseEvent mouseEvent) {
        handleLeaderClick(1, 0);
    }

    public void onLead2Res2Click(MouseEvent mouseEvent) {
        handleLeaderClick(1, 1);
    }

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

    private void updateLabel(Label remainingLabel, int increment) {
        remainingLabel.setText(Integer.toString(Integer.parseInt(remainingLabel.getText()) + increment));
    }

    private void fillWarehouse() {
        String nickname = GameApplication.getInstance().getUserNickname();
        new Thread(() -> {
            Resources[] warehouse = GameApplication.getInstance().getGameController().getGameData().getPlayerData(nickname).getWarehouse().getContent();
            Platform.runLater(() -> {
                for(int i = 0; i < 3; i++) {
                    Resources rowResources = warehouse[i];
                    if(rowResources != null) fillRow(rowResources, rows.get(2-i));
                }
            });
        }).start();
    }

    private void fillRow(Resources resources, List<ImageView> row) {
        ResourceType resourceType = getResourceType(resources);
        if(resourceType != null) {
            int resCount = resources.getAmountOf(resourceType);
            row.stream().limit(resCount).forEach(imageView -> imageView.setImage(resourceType.getImage()));
            rowsResourceTypes.set(rows.indexOf(row), resourceType);
        }
    }

    private ResourceType getResourceType(Resources resources) {
        for(ResourceType resourceType: ResourceType.values()) {
            int resCount = resources.getAmountOf(resourceType);
            if(resCount > 0) return resourceType;
        }
        return null;
    }

    private void fillRemainingResources() {
        new Thread(() -> {
            Resources resourcesToPut = GameApplication.getInstance().getGameController().getGameData().getMomentary().getResourcesToPut().getRes();
            Platform.runLater(() -> {
                remainingResources.forEach((resourceType, label) -> label.setText(Integer.toString(0)));
                for(ResourceType resourceType: ResourceType.values()) {
                    int resCount = resourcesToPut.getAmountOf(resourceType);
                    System.out.println("GUIWarehouse.fillRemainingResources. " + resourceType.toString() + " = " + resCount);
                    if(resCount > 0) {
                        updateLabel(remainingResources.get(resourceType), resCount);
                    }
                }
            });
        }).start();
    }

    private void fillLeaders() {
        String nickname = GameApplication.getInstance().getUserNickname();
        new Thread(() -> {
            int count = 0;
            LeadCard[] leadersData = GameApplication.getInstance().getGameController().getGameData().getPlayerData(nickname).getWarehouse().getActivatedLeaders();
            Resources[] extra = GameApplication.getInstance().getGameController().getGameData().getPlayerData(nickname).getWarehouse().getExtra();
            Platform.runLater(() -> {
                for(int i = 0; i < leadersData.length; i++) {
                    LeadCard leader = leadersData[i];
                    if(leader != null) {
                        ResourceType leaderResourceType = getResourceType(leader.getAbility().getExtraWarehouseSpace());
                        // If the leader has an extra space
                        if(leaderResourceType != null) {
                            // Get the current amount of extra
                            int extraAmount = extra[i].getAmountOf(leaderResourceType);
                            // Update the leader image
                            leaders.get(count).setImage(CardManager.getImage(leader.getCardId()));
                            // Update the leader resources
                            for(int j = 0; j < extraAmount; j++) {
                                leadersResources.get(count).get(j).setImage(leaderResourceType.getImage());
                            }
                            // Update the leader resource type
                            leadersResourceTypes.set(count, leaderResourceType);
                        }
                    }
                }
                for(int i = count; i < 2; i++) {
                    System.out.println("GUIWarehouse.fillLeaders: canceling leader box");
                    leaders.get(i).setImage(null);
                    HBox leaderHBox = leadersHBox.get(i);
                    leaderHBox.setVisible(false);
                    leaderHBox.setDisable(true);

                }
            });
        }).start();
    }
}
