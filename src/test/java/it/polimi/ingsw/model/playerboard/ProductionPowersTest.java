package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.general.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProductionPowersTest {

    @Test
    void getAvailableProductions() {

    }

    @Test
    void getOwnedDevCards() {
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
        DevCard dc2 = new DevCard(3, "b", Level.LOW, Color.RED, cost, prod);
        //Initialized a fake DevCard

        ProductionPowers pp = new ProductionPowers(3);

        pp.addDevCard(dc, 0);

        ArrayList<DevCard> AvailableCards = new ArrayList<DevCard>();
        AvailableCards.add(dc);

        assertEquals(pp.getOwnedDevCards(), AvailableCards);

        AvailableCards.add(dc2);
        pp.addDevCard(dc2, 2);

        assertEquals(pp.getOwnedDevCards(), AvailableCards);


    }

    @Test
    void addLeadCardProduction() {
    }

    @Test
    void getOwnedCardsVictoryPoints() {
    }
}