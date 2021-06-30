package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.general.*;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class LeadCardTest {

    @Test
    public void testLeadCard() {
        Resources emptyRes = new Resources();


        LeadCardAbility ability = new LeadCardAbility(emptyRes, emptyRes, ResourceType.STONES, new Production(emptyRes, emptyRes));
        LeadCardRequirements req = new LeadCardRequirements(new HashMap<Color, Integer>(), new HashMap<Color, Level>(), emptyRes);

        LeadCard lc = new LeadCard(0, "0", req, ability);

        assertEquals(ability, lc.getAbility());
    }

    @Test
    public void testAffordable() {
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

    @Test
    public void testPlay() {
        // Create lead card
        Resources cost = (new Resources()).add(ResourceType.SHIELDS, 2);;
        Resources discount = (new Resources()).add(ResourceType.STONES, 2);
        Resources extraWarehouseSpace = (new Resources()).add(ResourceType.SERVANTS, 2);
        Resources input = (new Resources()).add(ResourceType.SHIELDS, 1);
        Resources output = (new Resources()).add(ResourceType.STONES, 1);


        LeadCardAbility ability = new LeadCardAbility(discount, extraWarehouseSpace, ResourceType.COINS, new Production(input, output));
        LeadCardRequirements req = new LeadCardRequirements(new HashMap<Color, Integer>(), new HashMap<Color, Level>(), cost);

        LeadCard lc = new LeadCard(0, "0", req, ability);

        // Check that the requirements and the ability are correct
        assertEquals(ability, lc.getAbility());
        assertEquals(req, lc.getRequirements());

        // Create player
        Player player = new Player("name");
        LeadCard[] hand = new LeadCard[2];
        hand[0] = lc;
        player.getLeaders().setCards(hand);
        Resources playerRes = new Resources();
        playerRes.add(ResourceType.SHIELDS, 2);
        player.getBoard().getStrongbox().deposit(playerRes);

        // Test check == true
        assertTrue(lc.affordable(player));

        // Check card not played
        assertTrue(player.getLeaders().getPlayableCards().contains(lc));
        assertFalse(player.getLeaders().getPlayedCards().contains(lc));

        // Play the leader
        lc.play(player);

        // Check card played
        assertFalse(player.getLeaders().getPlayableCards().contains(lc));
        assertTrue(player.getLeaders().getPlayedCards().contains(lc));
    }

    @Test
    public void testDiscard() {
        // Create lead card
        Resources cost = (new Resources()).add(ResourceType.SHIELDS, 2);;
        Resources discount = (new Resources()).add(ResourceType.STONES, 2);
        Resources extraWarehouseSpace = (new Resources()).add(ResourceType.SERVANTS, 2);
        Resources input = (new Resources()).add(ResourceType.SHIELDS, 1);
        Resources output = (new Resources()).add(ResourceType.STONES, 1);


        LeadCardAbility ability = new LeadCardAbility(discount, extraWarehouseSpace, ResourceType.COINS, new Production(input, output));
        LeadCardRequirements req = new LeadCardRequirements(new HashMap<Color, Integer>(), new HashMap<Color, Level>(), cost);

        LeadCard lc = new LeadCard(0, "0", req, ability);

        // Create player
        Player player = new Player("name");
        LeadCard[] hand = new LeadCard[1];
        hand[0] = lc;
        player.getLeaders().setCards(hand);

        // Check leader is present
        assertTrue(player.getLeaders().getPlayableCards().contains(lc));

        // Discard leader
        lc.discard(player);

        // Check leader not present anymore
        assertFalse(player.getLeaders().getPlayableCards().contains(lc));
        assertFalse(player.getLeaders().getPlayedCards().contains(lc));
    }
}
