package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.general.Level;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductionPowersTest {

    @Test
    void getAvailableProductions() {

    }

    @Test
    void getOwnedDevCards() {
    }

    @Test
    void addDevCard() {
        Resources cost = new Resources();
        Resources in = new Resources();
        Resources out = new Resources();
        in.add(ResourceType.STONES, 3);
        out.add(ResourceType.SERVANTS, 2);
        //DevCard dc = new DevCard(3, 1, Level.LOW, cost,  );
    }

    @Test
    void addLeadCardProduction() {
    }

    @Test
    void getOwnedCardsVictoryPoints() {
    }
}