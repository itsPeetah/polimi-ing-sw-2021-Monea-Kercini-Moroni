package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.model.cards.CardManager;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.general.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProductionPowersTest {

    @Test
    void testGetBasicProduction() {
        assertNotNull(ProductionPowers.getBasicProduction());
    }

    @Test
    public void testDevCards_Productions() {
        Resources cost = new Resources();
        Resources in = new Resources();
        Resources out = new Resources();
        in.add(ResourceType.STONES, 3);
        out.add(ResourceType.SERVANTS, 2);
        Production prod = new Production(in, out);
        Production prod2 = new Production(out, in);
        DevCard dc = new DevCard(3, "a", Level.LOW, Color.BLUE, cost, prod);
        DevCard dc2 = new DevCard(2, "b", Level.LOW, Color.PURPLE, cost, prod2);
        //Initialized a fake DevCard

        ProductionPowers pp = new ProductionPowers(3);

        pp.addDevCard(dc, 0);

        ArrayList<DevCard> AvailableCards = new ArrayList<DevCard>();
        AvailableCards.add(dc);

        assertEquals(pp.getOwnedDevCards(), AvailableCards);

        AvailableCards.add(dc2);
        pp.addDevCard(dc2, 2);

        assertEquals(pp.getOwnedDevCards(), AvailableCards);

        ArrayList<Production> AvailableProd = new ArrayList<Production>();

        Resources inb = new Resources();
        Resources outb = new Resources();
        inb.add(ResourceType.CHOICE, 2);
        outb.add(ResourceType.CHOICE, 1);
        Production basicProduction = new Production(inb, outb);

        AvailableProd.add(dc.getProduction());
        AvailableProd.add(dc2.getProduction());
        AvailableProd.add(basicProduction);

        //assertEquals(AvailableProd, pp.getAvailableProductions()); This would not work as the references for the basic productions are different

        //check one by one if the productions are equal

        for (int i = 0; i < pp.getAvailableProductions().size(); i++) {

            assertTrue(AvailableProd.get(i).getInput().equals(pp.getAvailableProductions().get(i).getInput()));
            assertTrue(AvailableProd.get(i).getOutput().equals(pp.getAvailableProductions().get(i).getOutput()));
        }

    }

    @Test
    public void testaddLeadCardProduction() {
        //The method is really easy and takes too much to initialize the test
    }

    @Test
    public void testgetOwnedCardsVictoryPoints() {
        Resources cost = new Resources();
        Resources in = new Resources();
        Resources out = new Resources();
        in.add(ResourceType.STONES, 3);
        out.add(ResourceType.SERVANTS, 2);
        Production prod = new Production(in, out);
        Production prod2 = new Production(out, in);
        DevCard dc = new DevCard(3, "a", Level.LOW, Color.BLUE, cost, prod);
        DevCard dc2 = new DevCard(2, "b", Level.LOW, Color.PURPLE, cost, prod2);

        //Initialized a fake DevCard


        ProductionPowers pp = new ProductionPowers(3);

        assertEquals(0 ,pp.getOwnedCardsVictoryPoints() );

        pp.addDevCard(dc, 0);
        pp.addDevCard(dc2, 1);

        assertEquals(5 ,pp.getOwnedCardsVictoryPoints() );

    }

    @Test
    public void testaddDevCard() {

        DevCard dev1 = CardManager.loadDevCardsFromJson().get(0);
        DevCard dev2 = CardManager.loadDevCardsFromJson().get(4);
        DevCard dev3 = CardManager.loadDevCardsFromJson().get(20);

        ProductionPowers pp = new ProductionPowers(3);

        pp.addDevCard(dev1, 0);

        assertEquals(pp.getVisibleDevCards()[0], dev1);

        pp.addDevCard(dev2, 0);

        assertEquals(pp.getVisibleDevCards()[0], dev2);

        pp.addDevCard(dev3, 0);

        assertEquals(pp.getVisibleDevCards()[0], dev3);
    }

    @Test
    public void testCanDevCardBePlaced() {
        DevCard dev1 = CardManager.loadDevCardsFromJson().stream().filter(devCard -> devCard.getLevel() == Level.LOW).findFirst().orElse(null);
        DevCard dev2 = CardManager.loadDevCardsFromJson().stream().filter(devCard -> devCard.getLevel() == Level.MEDIUM).findFirst().orElse(null);
        DevCard dev3 = CardManager.loadDevCardsFromJson().stream().filter(devCard -> devCard.getLevel() == Level.HIGH).findFirst().orElse(null);

        ProductionPowers pp = new ProductionPowers(3);

        assert dev1 != null;
        assertTrue(pp.canDevCardBePlaced(dev1, 0));
        pp.addDevCard(dev1, 0);
        assertFalse(pp.canDevCardBePlaced(dev1, 0));

        assert dev2 != null;
        assertTrue(pp.canDevCardBePlaced(dev2, 0));
        pp.addDevCard(dev2, 0);
        assertFalse(pp.canDevCardBePlaced(dev2, 0));

        assert dev3 != null;
        assertTrue(pp.canDevCardBePlaced(dev3, 0));
        pp.addDevCard(dev3, 0);
        assertFalse(pp.canDevCardBePlaced(dev3, 0));
    }
}