package it.polimi.ingsw.model.general;
import javafx.scene.image.Image;
import java.io.InputStream;

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

    private final static String RESOURCES_IMAGES_PATH = "images/resources/";

    public Image getImage() {
        String resourceImagePath = RESOURCES_IMAGES_PATH + imagePath;
        InputStream is = ResourceType.class.getClassLoader().getResourceAsStream(resourceImagePath);
        Image resourceImage = new Image(is);
        return resourceImage;
    }
}
