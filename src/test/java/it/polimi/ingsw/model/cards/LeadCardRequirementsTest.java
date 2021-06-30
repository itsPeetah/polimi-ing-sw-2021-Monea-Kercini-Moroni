package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.general.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LeadCardRequirementsTest {

    @Test
    public void testCheck() {
        // Create req
        Resources cost = new Resources();
        cost.add(ResourceType.SHIELDS, 1);

        LeadCardRequirements req = new LeadCardRequirements(new HashMap<Color, Integer>(), new HashMap<Color, Level>(), cost);

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
        LeadCardRequirements req2 = new LeadCardRequirements(new HashMap<Color, Integer>(), new HashMap<Color, Level>(), cost2);

        // Test affordable == false
        assertFalse(req2.check(player));

        // Test check with leaders color req
        DevCard dev1 = new DevCard(0, "0", Level.LOW, Color.BLUE, cost, new Production(cost, cost));
        player.getBoard().getProductionPowers().addDevCard(dev1, 0);
        HashMap<Color, Integer> colorIntegerHashMap = new HashMap<>();
        colorIntegerHashMap.put(Color.BLUE, 1);
        LeadCardRequirements req3 = new LeadCardRequirements(colorIntegerHashMap, new HashMap<Color, Level>(), cost);

        // Test affordable == true
        assertTrue(req3.check(player));

        // Test check with leaders level req
        DevCard dev2 = new DevCard(0, "0", Level.MEDIUM, Color.GREEN, cost, new Production(cost, cost));
        player.getBoard().getProductionPowers().addDevCard(dev2, 1);
        HashMap<Color, Level> colorLevelHashMap = new HashMap<>();
        colorLevelHashMap.put(Color.GREEN, Level.MEDIUM);
        LeadCardRequirements req4 = new LeadCardRequirements(new HashMap<>(), colorLevelHashMap, cost);

        // Test affordable == true
        assertTrue(req4.check(player));
    }
}
