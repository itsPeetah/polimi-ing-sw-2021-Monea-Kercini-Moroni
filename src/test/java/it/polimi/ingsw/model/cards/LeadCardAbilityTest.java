package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.general.Production;
import it.polimi.ingsw.model.general.Resources;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LeadCardAbilityTest {

    @Test
    public void leadCardAbilityTest() {
        Resources expRes = new Resources();

        LeadCardAbility ability = new LeadCardAbility(expRes, expRes, expRes, new Production(expRes, expRes));

        assertEquals(expRes, ability.getExtraWarehouseSpace());
        assertEquals(expRes, ability.getGreyMarbleReplacement());
        assertEquals(expRes, ability.getResourceDiscount());
        assertEquals(expRes, ability.getProduction().getInput());
        assertEquals(expRes, ability.getProduction().getOutput());
    }
}
