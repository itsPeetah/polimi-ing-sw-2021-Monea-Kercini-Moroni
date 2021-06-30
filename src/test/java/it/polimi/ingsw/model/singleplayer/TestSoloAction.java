package it.polimi.ingsw.model.singleplayer;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TestSoloAction {

    @Test
    public void testPlayLorenzoTurn() {
        // Create new solo action
        SoloAction soloAction = new SoloAction(0);

        // Check that initial last played token is null
        assertNull(soloAction.getLastPlayedToken());

        // Play Lorenzo turn
        soloAction.playLorenzoTurn(null);

        // Check that last played token is an existent token
        assertNotNull(soloAction.getLastPlayedToken());
        assertTrue(Arrays.stream(SoloActionTokens.values()).anyMatch(soloActionTokens -> soloActionTokens == soloAction.getLastPlayedToken()));
    }
}
