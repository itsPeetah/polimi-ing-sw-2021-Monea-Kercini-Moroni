package it.polimi.ingsw.model.singleplayer;

import javafx.scene.image.Image;

import java.io.File;

public enum SoloActionTokens {
    DISCARD_2_GREEN,
    DISCARD_2_BLUE,
    DISCARD_2_YELLOW,
    DISCARD_2_PURPLE,
    MOVE_2,
    MOVE_1_SHUFFLE;

    public Image getImage(){

        String path = ("src/main/resources/images/solotokens/blue.png");

        switch (this){
            case DISCARD_2_BLUE:
                 path = ("src/main/resources/images/solotokens/blue.png");
                break;
            case DISCARD_2_GREEN:
                 path = ("src/main/resources/images/solotokens/green.png");
                break;
            case DISCARD_2_PURPLE:
                 path = ("src/main/resources/images/solotokens/purple.png");
                break;
            case DISCARD_2_YELLOW:
                 path = ("src/main/resources/images/solotokens/yellow.png");
                break;
            case MOVE_1_SHUFFLE:
                path = ("src/main/resources/images/solotokens/shuffle.png");
                break;
            case MOVE_2:
                path = ("src/main/resources/images/solotokens/faith2.png");
                break;
        }

        File file = new File(path);

        return new Image(file.toURI().toString());
    }
}
