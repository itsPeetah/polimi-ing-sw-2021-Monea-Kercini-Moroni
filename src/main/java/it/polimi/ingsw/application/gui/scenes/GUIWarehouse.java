package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.model.general.ResourceType;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;

public class GUIWarehouse implements Initializable {
    public Label coinsCount;
    public Label servantsCount;
    public Label shieldsCount;
    public Label stonesCount;
    private ResourceType selectedResource;
    private final HashMap<ResourceType, Label> remainingResources = new HashMap<>();

    // Resources available


    public ImageView im00;
    public ImageView im10;
    public ImageView im11;
    public ImageView im20;
    public ImageView im21;
    public ImageView im22;

    private final List<ImageView> firstRow = new ArrayList<>();
    private final List<ImageView> secondRow = new ArrayList<>();
    private final List<ImageView> thirdRow = new ArrayList<>();
    private final List<List<ImageView>> rows = new ArrayList<>();
    private final List<ResourceType> rowsResourceTypes = new ArrayList<>();



    public void coinClick(MouseEvent mouseEvent) {
        selectedResource = ResourceType.COINS;
    }

    public void servantClick(MouseEvent mouseEvent) {
        selectedResource = ResourceType.SERVANTS;
    }

    public void shieldClick(MouseEvent mouseEvent) {
        selectedResource = ResourceType.SHIELDS;
    }

    public void stoneClick(MouseEvent mouseEvent) {
        selectedResource = ResourceType.STONES;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firstRow.add(im00);
        secondRow.addAll(Arrays.asList(im10, im11));
        thirdRow.addAll(Arrays.asList(im20, im21, im22));
        rows.addAll(Arrays.asList(firstRow, secondRow, thirdRow));
        rowsResourceTypes.addAll(Arrays.asList(null, null, null));

        remainingResources.put(ResourceType.COINS, coinsCount);
        remainingResources.put(ResourceType.SERVANTS, servantsCount);
        remainingResources.put(ResourceType.STONES, stonesCount);
        remainingResources.put(ResourceType.SHIELDS, shieldsCount);

        // todo mettere vere quantità
        remainingResources.forEach((resourceType, label) -> label.setText(Integer.toString(2)));
    }

    public void onIm00Click(MouseEvent mouseEvent) {
        handleClick(0, 0);
    }

    public void onIm10Click(MouseEvent mouseEvent) {
        handleClick(1, 0);
    }

    public void onIm11Click(MouseEvent mouseEvent) {
        handleClick(1, 1);
    }

    public void onIm20Click(MouseEvent mouseEvent) {
        handleClick(2, 0);
    }

    public void onIm21Click(MouseEvent mouseEvent) {
        handleClick(2, 1);
    }

    public void onIm22Click(MouseEvent mouseEvent) {
        handleClick(2, 2);
    }

    private void handleClick(int row, int column) {
        ImageView clickedImage = rows.get(row).get(column);
        // If the image view is already filled with an image, remove the resource
        if(clickedImage.getImage() != null) {
            clickedImage.setImage(null);
            // Update the remaining resources
            Label resourceLabel = remainingResources.get(rowsResourceTypes.get(row));
            resourceLabel.setText(Integer.toString(Integer.parseInt(resourceLabel.getText()) + 1));
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
                    Label resourceLabel = remainingResources.get(rowsResourceTypes.get(row));
                    resourceLabel.setText(Integer.toString(Integer.parseInt(resourceLabel.getText()) - 1));
                } else {
                    if (rowsResourceTypes.get(row) == selectedResource) {
                        clickedImage.setImage(selectedResource.getImage());
                        Label resourceLabel = remainingResources.get(rowsResourceTypes.get(row));
                        resourceLabel.setText(Integer.toString(Integer.parseInt(resourceLabel.getText()) - 1));
                    }
                }
            }
        }
    }
}
