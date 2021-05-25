package it.polimi.ingsw.model.general;

import it.polimi.ingsw.model.cards.CardManager;
import javafx.scene.image.Image;

import java.io.File;

public enum ResourceType {
    STONES("stone.png"),
    COINS("coin.png"),
    SERVANTS("servant.png"),
    SHIELDS("shield.png"),
    FAITH,
    BLANK,
    CHOICE;

    private String imagePath = null;

    ResourceType() {}

    ResourceType(String imagePath) {
        this.imagePath = imagePath;
    }

    private final static String RESOURCES_IMAGES_PATH = "src/main/resources/images/resources/";

    public Image getImage() {
        String resourceImagePath = RESOURCES_IMAGES_PATH + imagePath;
        File file = new File(resourceImagePath);
        Image resourceImage = new Image(file.toURI().toString());
        return resourceImage;
    }
}
