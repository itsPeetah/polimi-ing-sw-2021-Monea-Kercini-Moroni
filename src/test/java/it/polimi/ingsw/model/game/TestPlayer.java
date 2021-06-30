package it.polimi.ingsw.model.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class TestPlayer {
    @Test
    public void testGetVictoryPoints() {
        Player player = new Player("test");
        // Test that tha initial VP are zero
        assertSame(0, player.getVictoryPoints());
    }
}
