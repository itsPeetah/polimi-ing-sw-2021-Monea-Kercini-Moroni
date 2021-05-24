package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.model.general.ResourceType;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;

public class GUIWarehouse implements Initializable {
    private ResourceType selectedResource;

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
        im00.setImage(ResourceType.COINS.getImage());
        firstRow.add(im00);
        secondRow.addAll(Arrays.asList(im10, im11));
        thirdRow.addAll(Arrays.asList(im20, im21, im22));
        rows.addAll(Arrays.asList(firstRow, secondRow, thirdRow));
        rowsResourceTypes.addAll(Arrays.asList(null, null, null));
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
        if(clickedImage.getImage() != null) {
            clickedImage.setImage(null);
            if(rows.get(row).stream().allMatch(imageView -> imageView.getImage() == null)) rowsResourceTypes.set(row, null);
        }
        else {
            if (rowsResourceTypes.get(row) == null) {
                rowsResourceTypes.set(row, selectedResource);
                clickedImage.setImage(selectedResource.getImage());
            } else {
                if (rowsResourceTypes.get(row) == selectedResource) clickedImage.setImage(selectedResource.getImage());
            }
        }
    }
}
