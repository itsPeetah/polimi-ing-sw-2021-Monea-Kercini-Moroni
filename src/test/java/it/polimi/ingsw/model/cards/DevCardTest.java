package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.general.*;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DevCardTest {

    @Test
    public void devCardTest() {
        Resources expRes = new Resources();
        Level expLev = Level.values()[0];
        Color expCol = Color.values()[0];

        DevCard devCard = new DevCard(0, "0", expLev, expCol, expRes, new Production(expRes, expRes));

        assertSame(expLev, devCard.getLevel());
        assertSame(expCol, devCard.getColor());
        assertEquals(expRes, devCard.getCost());
        assertEquals(expRes, devCard.getProduction().getInput());
        assertEquals(expRes, devCard.getProduction().getOutput());
    }

    @Test
    public void affordableTest() {
        // Create devCard
        Resources expRes = new Resources();
        Level expLev = Level.values()[0];
        Color expCol = Color.values()[0];
        Resources cost = new Resources();
        cost.add(ResourceType.SHIELDS, 1);

        DevCard devCard = new DevCard(0, "0", expLev, expCol, cost, new Production(expRes, expRes));

        // Create player
        Player player = new Player("name");
        Resources playerRes = new Resources();
        playerRes.add(ResourceType.SHIELDS, 2);
        player.getBoard().getStrongbox().deposit(playerRes);

        // Test affordable == true
        assertTrue(devCard.affordable(player));

        // Create new devCard
        Resources cost2 = new Resources();
        cost2.add(ResourceType.STONES, 1);
        DevCard devCard2 = new DevCard(0, "0", expLev, expCol, cost2, new Production(expRes, expRes));

        // Test affordable == false
        assertFalse(devCard2.affordable(player));
    }
}
