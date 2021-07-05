package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.general.Color;
import it.polimi.ingsw.model.general.Level;
import it.polimi.ingsw.model.general.Production;
import it.polimi.ingsw.model.general.Resources;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CardTest {
    /**
     * Test card initialization and methods
     */
    @Test
    public void testCard() {
        final int expectedVP = 10;
        final String expectedCardID = "testCard";
        final Resources emptyRes = new Resources();
        Card card = new DevCard(expectedVP, expectedCardID, Level.LOW, Color.YELLOW, emptyRes, new Production(emptyRes, emptyRes));

        assertEquals(expectedVP, card.getVictoryPoints());
        assertEquals(expectedCardID, card.getCardId());
    }
}
