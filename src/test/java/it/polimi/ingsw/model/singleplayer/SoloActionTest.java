
package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.cards.CardManager;
import it.polimi.ingsw.model.game.DevCardMarket;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class SoloActionTest {

    @Test
    public void testPlayLorenzoTurn() {
        // Create new solo action
        SoloAction soloAction = new SoloAction(0);

        // Check that initial last played token is null
        assertNull(soloAction.getLastPlayedToken());

        //Create dcm
        DevCardMarket dcm = new DevCardMarket(CardManager.loadDevCardsFromJson(), 3);

        // Play Lorenzo turn
        soloAction.playLorenzoTurn(dcm);

        // Check that last played token is an existent token
        assertNotNull(soloAction.getLastPlayedToken());
        assertTrue(Arrays.stream(SoloActionTokens.values()).anyMatch(soloActionTokens -> soloActionTokens == soloAction.getLastPlayedToken()));
    }
}
