package it.polimi.ingsw.model.cards;

import com.google.gson.Gson;
import it.polimi.ingsw.model.general.*;
import it.polimi.ingsw.util.JSONUtility;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static it.polimi.ingsw.model.cards.CardManager.MAP_CARDS_IMAGES_PATH;
import static org.junit.jupiter.api.Assertions.*;

public class CardManagerTest {

    @Test
    public void loadDevCardsFromJsonTest() {
        // Load the DevCards
        ArrayList<DevCard> cards = CardManager.loadDevCardsFromJson();

        // Check the amount of DevCards
        assertSame(CardManager.DEV_CARDS_SIZE, cards.size());
    }

    @Test
    public void loadLeadCardsFromJsonTest() throws IOException {
        // Load the LeadCards
        ArrayList<LeadCard> cards = CardManager.loadLeadCardsFromJson();

        // Check the amount of LeadCards
        assertSame(CardManager.LEAD_CARDS_SIZE, cards.size());
    }

    @Test
    public void putDevCardsImages() throws IOException {
        ArrayList<DevCard> cards = CardManager.loadDevCardsFromJson();
        HashMap<String, String> map = new HashMap<>();
        for(DevCard devCard: cards) {
            map.put(devCard.getCardId(), CardManager.DEV_CARDS_IMAGES_PATH + devCard.getColor().toString().toLowerCase() + "/" + devCard.getCardId() + ".png");
        }
        Gson gson = new Gson();
        FileWriter file = new FileWriter(MAP_CARDS_IMAGES_PATH);
        gson.toJson(map, file);
        file.flush();
        file.close();
    }
}
