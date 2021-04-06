package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {

    @Test
    void deposit() {
        Warehouse wh = new Warehouse();
        Resources res = new Resources();
        //res.add(ResourceType.STONES, 3);
        wh.deposit(res,0);
        //res.add(ResourceType.SHIELDS, 1);
        wh.deposit(res, 2);

        Resources res2 = new Resources();
        //res2.add(ResourceType.STONES, 3);
        //res2.add(ResourceType.SHIELDS, 1);

        assertTrue(res==res2);

    }

    @Test
    void expandWithLeader() {
    }

    @Test
    void getResourceAmountWarehouse() {
    }

    @Test
    void getResourcesAvailable() {
    }
}