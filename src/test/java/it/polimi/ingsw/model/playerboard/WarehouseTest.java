package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {

    @Test
    void deposit() {
        Warehouse wh = new Warehouse();
        Resources res = new Resources();
        Resources res2 = new Resources();
        res.add(ResourceType.STONES, 3);
        wh.deposit(res,0);
        res2.add(ResourceType.SHIELDS, 1);
        wh.deposit(res2, 3); //Trying with Leader Extra (Also checked with other floors)
        res.add(res2);

        assertTrue(wh.getResourcesAvailable().equals(res));
    }

    @Test
    void withdraw() {
        Warehouse wh = new Warehouse();
        Resources res = new Resources();
        Resources res2 = new Resources();
        res.add(ResourceType.STONES, 3);
        wh.deposit(res,0);
        res2.add(ResourceType.SHIELDS, 1);
        wh.deposit(res2, 3);
        //now we have 3 stones in floor 0 and 1 shield in floor 3
        Resources res3 = new Resources();
        Resources res4 = new Resources();
        res3.add(ResourceType.STONES, 2);

        assertTrue(res3.equals(wh.withdraw(res3)));

        //Resources left
        res4.add(ResourceType.STONES, 1);
        res4.add(ResourceType.SHIELDS, 1);

        assertTrue(res4.equals(wh.getResourcesAvailable()));

        Resources res5 = new Resources();
        res5.add(ResourceType.SERVANTS, 1);

        assertFalse(wh.withdraw(res5).equals(res5)); //It can't get the servant

        assertTrue(res4.equals(wh.getResourcesAvailable()));

        assertFalse(wh.withdraw(res3).equals(res3)); //It will get only one stone not two

        assertTrue(res2.equals(wh.getResourcesAvailable())); //Only one Servant left

    }

    @Test
    void areLeadersOrganized() {

        Warehouse wh = new Warehouse();
        Resources res1 = new Resources();
        res1.add(ResourceType.SERVANTS, 1);

        //Leader 5 gives two extra warehouse stone space
        wh.expandWithLeader(CardManager.loadLeadCardsFromJson().get(4));

        //warehouse should be organized at the beginning
        assertTrue((wh).isOrganized());

        //floor 3 is the correct but resource no
        wh.deposit(res1, 3);

        //so the warehouse is not organized
        assertTrue(!(wh).isOrganized());

        //removing the servant
        wh.withdraw(res1);


        //creating correct type of res
        Resources res2 = new Resources();
        res2.add(ResourceType.STONES, 1);

        //repeating the procedure
        wh.deposit(res2, 3);
        assertTrue((wh).isOrganized());

        //if we add it to the wrong place
        wh.deposit(res2, 4);
        assertTrue(!(wh).isOrganized());



        //cleaning the warehouse
        wh.withdraw(res2);
        wh.withdraw(res2);



        //putting two stone at the correct place
        wh.deposit(res2, 3);
        wh.deposit(res2, 3);

        //last check
        assertTrue((wh).isOrganized());
    }

    @Test
    void getResourceAmountWarehouse() {
        Warehouse wh = new Warehouse();
        Resources res = new Resources();
        Resources res2 = new Resources();
        Resources res3 = new Resources();

        assertEquals(wh.getResourceAmountWarehouse(), 0); //There should be none at the beginning

        res.add(ResourceType.STONES, 3);
        wh.deposit(res,0);
        res2.add(ResourceType.SHIELDS, 1);
        wh.deposit(res2, 2);
        res3.add(ResourceType.SERVANTS, 2);
        wh.deposit(res3, 3);

        assertEquals(6, wh.getResourceAmountWarehouse()); //There should be none at the beginning
    }

    @Test
    void getResourcesAvailable() {
        //This test is already included in the deposit and withdraw tests
    }
}