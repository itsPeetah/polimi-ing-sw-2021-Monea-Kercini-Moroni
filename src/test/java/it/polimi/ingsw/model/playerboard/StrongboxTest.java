package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.model.general.ResourcesException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StrongboxTest {

    @Test
    public void depositAndWithdraw() {
        Strongbox sb = new Strongbox();
        Resources res = new Resources();
        res.add(ResourceType.STONES, 3);
        sb.deposit(res);
        assertEquals(sb.getResourcesAvailable().getAmountOf(ResourceType.STONES), res.getAmountOf(ResourceType.STONES));
        assertNotEquals(sb.getResourcesAvailable().getAmountOf(ResourceType.STONES), res.getAmountOf(ResourceType.SHIELDS));

        Resources res2 = new Resources();
        res2.add(ResourceType.STONES, 2);

        sb.withdraw(res2);

        assertNotEquals(sb.getResourcesAvailable().getAmountOf(ResourceType.STONES), res.getAmountOf(ResourceType.STONES)); //non deve essere uguale a prima

        sb.withdraw(res);

        Resources res3 = new Resources();
        res3.add(ResourceType.STONES, 1);

        assertEquals(sb.getResourcesAvailable().getAmountOf(ResourceType.STONES), res3.getAmountOf(ResourceType.STONES)); //deve essere uguale a 1

    }

    @Test
    public void getResAmount(){
        Strongbox sb = new Strongbox();
        Resources res = new Resources();
        res.add(ResourceType.STONES, 3);
        res.add(ResourceType.SHIELDS, 8);
        sb.deposit(res);

        assertEquals(sb.getResourceAmountStrongbox(), 11);

    }

}