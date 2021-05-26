package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

        fillWarehouse();
        fillRemainingResources();
        fillLeaders();
    }

    public void onConfirmClick(ActionEvent actionEvent) {
        // TODO send packet

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
                    if(rowResources != null) fillRow(rowResources, rows.get(i));
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
        // todo mettere giusti tipi di risorse dei leader
        leadersResourceTypes.addAll(Arrays.asList(ResourceType.COINS, ResourceType.COINS));
    }
}
