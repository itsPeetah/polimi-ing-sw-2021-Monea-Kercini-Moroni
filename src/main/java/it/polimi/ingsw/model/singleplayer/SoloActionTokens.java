package it.polimi.ingsw.model.singleplayer;

import javafx.scene.image.Image;

import java.io.File;

public enum SoloActionTokens {
    DISCARD_2_GREEN("src/main/resources/images/solotokens/green.png"),
    DISCARD_2_BLUE("src/main/resources/images/solotokens/blue.png"),
    DISCARD_2_YELLOW("src/main/resources/images/solotokens/purple.png"),
    DISCARD_2_PURPLE("src/main/resources/images/solotokens/yellow.png"),
    MOVE_2("src/main/resources/images/solotokens/faith2.png"),
    MOVE_1_SHUFFLE("src/main/resources/images/solotokens/shuffle.png");

    private final String path;
    private Image image;

    SoloActionTokens(String path) {
        this.path = path;
    }

    public Image getImage(){
        return image;
    }

    public static void init() {
        for(SoloActionTokens soloActionTokens: SoloActionTokens.values()) {
            File file = new File(soloActionTokens.path);
            soloActionTokens.image = new Image(file.toURI().toString());
        }
    }
}
