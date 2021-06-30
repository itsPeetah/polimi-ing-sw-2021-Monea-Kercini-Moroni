package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.game.MarketTray;
import it.polimi.ingsw.model.game.MarketTrayException;
import it.polimi.ingsw.model.game.util.GameSettingsLevel;
import it.polimi.ingsw.model.game.util.MarketTrayFactory;
import it.polimi.ingsw.model.general.Resources;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CardManagerTest {

    @Test
    public void testLoadDevCardsFromJson() {
        // Load the DevCards
        ArrayList<DevCard> cards = CardManager.loadDevCardsFromJson();

        // Check the amount of DevCards
        assertSame(CardManager.DEV_CARDS_SIZE, cards.size());
    }

    @Test
    public void testLoadLeadCardsFromJson() {
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
