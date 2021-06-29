package it.polimi.ingsw.model.cards;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
    public void loadLeadCardsFromJsonTest() {
        // Load the LeadCards
        ArrayList<LeadCard> cards = CardManager.loadLeadCardsFromJson();

        // Check the amount of LeadCards
        assertSame(CardManager.LEAD_CARDS_SIZE, cards.size());
    }

    @Test
    public void testLoadImages() {
        CardManager.loadImages();
        assertNotNull(CardManager.getImage(CardManager.loadDevCardsFromJson().get(0).getCardId()));
    }
}
