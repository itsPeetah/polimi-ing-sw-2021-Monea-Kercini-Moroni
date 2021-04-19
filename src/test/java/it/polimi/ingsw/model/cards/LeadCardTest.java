package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.general.*;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

public class LeadCardTest {

    @Test
    public void LeadCardTest() {
        Resources emptyRes = new Resources();


        LeadCardAbility ability = new LeadCardAbility(emptyRes, emptyRes, ResourceType.STONES, new Production(emptyRes, emptyRes));
        LeadCardRequirements req = new LeadCardRequirements(new HashMap<Color, Integer>(), new HashMap<Color, Level>(), emptyRes);

        LeadCard lc = new LeadCard(0, "0", req, ability);

        assertEquals(ability, lc.getAbility());
    }

    @Test
    public void affordableTest() {
        // Create lead card
        Resources emptyRes = new Resources();
        Resources cost = new Resources();
        cost.add(ResourceType.SHIELDS, 1);

        LeadCardAbility ability = new LeadCardAbility(emptyRes, emptyRes, ResourceType.STONES, new Production(emptyRes, emptyRes));
        LeadCardRequirements req = new LeadCardRequirements(new HashMap<Color, Integer>(), new HashMap<Color, Level>(), cost);

        LeadCard lc = new LeadCard(0, "0", req, ability);

        // Create player
        Player player = new Player("name");
        Resources playerRes = new Resources();
        playerRes.add(ResourceType.SHIELDS, 2);
        player.getBoard().getStrongbox().deposit(playerRes);

        // Test check == true
        assertTrue(lc.affordable(player));

        // Create new lead card
        Resources cost2 = new Resources();
        cost2.add(ResourceType.STONES, 1);
        LeadCardRequirements req2 = new LeadCardRequirements(new HashMap<Color, Integer>(), new HashMap<Color, Level>(), cost2);
        LeadCard lc2 = new LeadCard(0, "0", req2, ability);

        // Test affordable == false
        assertFalse(lc2.affordable(player));
    }
}
