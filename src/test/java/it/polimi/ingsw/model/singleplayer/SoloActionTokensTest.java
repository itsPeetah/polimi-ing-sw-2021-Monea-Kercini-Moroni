package it.polimi.ingsw.model.singleplayer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SoloActionTokensTest {
    @Test
    public void testInit() {
        SoloActionTokens.init();

        for(SoloActionTokens token: SoloActionTokens.values()) assertNotNull(token.getImage());
    }
}
