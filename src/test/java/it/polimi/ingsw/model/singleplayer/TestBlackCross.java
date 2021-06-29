package it.polimi.ingsw.model.singleplayer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class TestBlackCross {

    @Test
    public void testIncrementBlackFaith() {
        BlackCross blackCross = new BlackCross();

        // Check that initial faith is zero
        assertSame(0, blackCross.getBlackFaith());

        int firstIncrement = 10;

        // Test correct increment
        blackCross.incrementBlackFaith(firstIncrement);
        assertSame(firstIncrement, blackCross.getBlackFaith());

        int secondIncrement = 30;

        // Test that 24 is the upper bound of the black cross
        blackCross.incrementBlackFaith(secondIncrement);
        assertSame(24, blackCross.getBlackFaith());
    }
}
