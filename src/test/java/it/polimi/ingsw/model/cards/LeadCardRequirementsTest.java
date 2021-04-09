package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.general.*;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LeadCardRequirementsTest {

    @Test
    public void checkTest() {
        // Create devCard
        Resources cost = new Resources();
        cost.add(ResourceType.SHIELDS, 1);

        LeadCardRequirements req = new LeadCardRequirements(new HashMap<Color, Integer>(), new HashMap<Level, Integer>(), cost);

        // Create player
        Player player = new Player("name");
        Resources playerRes = new Resources();
        playerRes.add(ResourceType.SHIELDS, 2);
        player.getBoard().getStrongbox().deposit(playerRes);

        // Test check == true
        assertTrue(req.check(player));

        // Create new requirements
        Resources cost2 = new Resources();
        cost2.add(ResourceType.STONES, 1);
        LeadCardRequirements req2 = new LeadCardRequirements(new HashMap<Color, Integer>(), new HashMap<Level, Integer>(), cost2);

        // Test affordable == false
        assertFalse(req2.check(player));
    }
}
