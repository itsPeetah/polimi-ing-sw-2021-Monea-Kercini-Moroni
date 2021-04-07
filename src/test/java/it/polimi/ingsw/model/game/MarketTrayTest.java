package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MarketTrayTest {

    private static MarketTray create2x2Tray(){
        ArrayList<ResourceMarble> marbles = new ArrayList<ResourceMarble>();
        marbles.add(new ResourceMarble(ResourceType.COINS, 1));
        marbles.add(new ResourceMarble(ResourceType.SERVANTS, 1));
        marbles.add(new ResourceMarble(ResourceType.SHIELDS, 1));
        marbles.add(new ResourceMarble(ResourceType.BLANK, 1));
        marbles.add(new ResourceMarble(ResourceType.FAITH, 1));
        try {
            return new MarketTray(2, 2, marbles);
        } catch (MarketTrayException e){
            return null;
        }
    }

    @Test
    public void testPickColumn0(){
        MarketTray mt = create2x2Tray();
        Resources r1, r2, r3;
        try {
            r1 = mt.pickColumn(0);
            assertTrue(r1.getAmountOf(ResourceType.COINS) == 1);
        } catch (MarketTrayException e){
            fail();
        }
        try {
            r2 = mt.pickColumn(0);
            assertTrue(r2.getAmountOf(ResourceType.SERVANTS) == 1);
        } catch (MarketTrayException e){
            fail();
        }
        try {
            r3 = mt.pickColumn(0);
            assertTrue(r3.getAmountOf(ResourceType.FAITH) == 1);
        } catch (MarketTrayException e){
            fail();
        }
    }

    @Test
    public void testPickRow0(){
        MarketTray mt = create2x2Tray();
        Resources r1, r2, r3;
        try {
            r1 = mt.pickRow(0);
            assertTrue(r1.getAmountOf(ResourceType.COINS) == 1);
        } catch (MarketTrayException e){
            fail();
        }
        try {
            r2 = mt.pickRow(0);
            assertTrue(r2.getAmountOf(ResourceType.SHIELDS) == 1);
        } catch (MarketTrayException e){
            fail();
        }
        try {
            r3 = mt.pickRow(0);
            assertTrue(r3.getAmountOf(ResourceType.FAITH) == 1);
        } catch (MarketTrayException e){
            fail();
        }
    }



}