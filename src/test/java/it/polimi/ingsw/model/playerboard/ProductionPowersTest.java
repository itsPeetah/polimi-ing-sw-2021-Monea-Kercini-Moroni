package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.model.cards.CardManager;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.general.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProductionPowersTest {

    @Test
    void getAvailableProductions() {
        //Included in the test below

    }

    @Test
    void DevCards_Productions() {
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
    void addLeadCardProduction() {
        //The method is really easy and takes too much to initialize the test
    }

    @Test
    void getOwnedCardsVictoryPoints() {
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
    void addDevCard() {



        DevCard dev1 = CardManager.loadDevCardsFromJson().get(0);
        DevCard dev2 = CardManager.loadDevCardsFromJson().get(4);
        DevCard dev3 = CardManager.loadDevCardsFromJson().get(20);

        ProductionPowers pp = new ProductionPowers(3);

        pp.addDevCard(dev1, 0);


        for (int i = 0; i < pp.getOwnedDevCards().size(); i++) {

            System.out.println("ProductionPowersTest.addDevCard " + pp.getOwnedDevCards().get(i).getCardId());

        }

        assertEquals(pp.getVisibleDevCards()[0], dev1);

        pp.addDevCard(dev2, 0);

        assertEquals(pp.getVisibleDevCards()[0], dev2);


        for (int i = 0; i < pp.getOwnedDevCards().size(); i++) {

            System.out.println("-ProductionPowersTest.addDevCard " + pp.getOwnedDevCards().get(i).getCardId());

        }

        pp.addDevCard(dev3, 0);

        assertEquals(pp.getVisibleDevCards()[0], dev3);

        for (int i = 0; i < pp.getOwnedDevCards().size(); i++) {

            System.out.println("--ProductionPowersTest.addDevCard " + pp.getOwnedDevCards().get(i).getCardId());

        }
    }
}