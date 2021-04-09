package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.model.general.Production;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerBoardTest {

    @Test
    void incrementFaithPoints() {

        Warehouse wh = new Warehouse();
        ProductionPowers pp = new ProductionPowers(3);
        Strongbox sb = new Strongbox();
        PlayerBoard pb = new PlayerBoard(3, wh, sb, pp);

        assertEquals(0, pb.getFaithPoints());

        pb.incrementFaithPoints(7);

        assertEquals(7, pb.getFaithPoints());
    }

    @Test
    void attendReport() {
        //To check this method we use getFaithVP
    }

    @Test
    void getFaithVP() {
        Warehouse wh = new Warehouse();
        ProductionPowers pp = new ProductionPowers(3);
        Strongbox sb = new Strongbox();
        PlayerBoard pb = new PlayerBoard(3, wh, sb, pp);

        assertEquals(0, pb.getFaithVP());

        pb.attendReport(0);

        assertEquals(2, pb.getFaithVP());

        pb.attendReport(2);

        assertEquals(6, pb.getFaithVP());
    }


    @Test
    void getBoardVictoryPoints() {
        Warehouse wh = new Warehouse();
        ProductionPowers pp = new ProductionPowers(3);
        Strongbox sb = new Strongbox();
        PlayerBoard pb = new PlayerBoard(3, wh, sb, pp);

        assertEquals(0, pb.getBoardVictoryPoints());

        //add FaithPoints 15 -> should add 9 VP
        pb.incrementFaithPoints(15);
        assertEquals(9, pb.getBoardVictoryPoints());

        //add 3 resources in warehouse and 8 in strongbox -> 2 VP
        Resources res = new Resources();
        res.add(ResourceType.STONES, 3);
        wh.deposit(res, 0);
        //System.out.println(wh.getResourceAmountWarehouse());

        Resources res2 = new Resources();
        res2.add(ResourceType.SHIELDS, 8);
        sb.deposit(res2);
        //System.out.println(sb.getResourceAmountStrongbox());

        assertEquals(11, pb.getBoardVictoryPoints());

        //The check on owned cards is already performed in ProductionPowersTest


    }

    @Test
    void getResourcesAvailable() {
        Warehouse wh = new Warehouse();
        ProductionPowers pp = new ProductionPowers(3);
        Strongbox sb = new Strongbox();
        PlayerBoard pb = new PlayerBoard(3, wh, sb, pp);

        Resources res = new Resources();

        assertTrue(res.equals(pb.getResourcesAvailable()));

        res.add(ResourceType.STONES, 3);

        wh.deposit(res, 0);

        Resources res2 = new Resources();
        res2.add(ResourceType.SHIELDS, 8);

        sb.deposit(res2);

        res.add(res2);

        assertTrue(res.equals(pb.getResourcesAvailable()));


    }

    @Test
    void getOwnedDevCards() {
        //No need for testing, method just returns what productionPowers.getOwnedDevCards() returns.
        //Already tested
    }
}