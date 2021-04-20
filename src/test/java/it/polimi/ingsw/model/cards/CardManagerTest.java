package it.polimi.ingsw.model.cards;

import com.google.gson.Gson;
import it.polimi.ingsw.model.general.*;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
}
