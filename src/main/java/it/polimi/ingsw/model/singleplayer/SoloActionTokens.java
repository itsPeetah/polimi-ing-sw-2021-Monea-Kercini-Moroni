package it.polimi.ingsw.model.singleplayer;

import javafx.scene.image.Image;

import java.io.InputStream;

public enum SoloActionTokens {
    DISCARD_2_GREEN("images/solotokens/green.png"),
    DISCARD_2_BLUE("images/solotokens/blue.png"),
    DISCARD_2_YELLOW("images/solotokens/purple.png"),
    DISCARD_2_PURPLE("images/solotokens/yellow.png"),
    MOVE_2("images/solotokens/faith2.png"),
    MOVE_1_SHUFFLE("images/solotokens/shuffle.png");

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
            InputStream is = SoloActionTokens.class.getClassLoader().getResourceAsStream(soloActionTokens.path);
            soloActionTokens.image = new Image(is);
        }
    }
}
