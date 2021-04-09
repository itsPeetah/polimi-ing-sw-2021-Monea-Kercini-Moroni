package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.game.util.GameSettingsLevel;
import it.polimi.ingsw.model.game.util.MarketTrayFactory;
import it.polimi.ingsw.model.general.Color;
import it.polimi.ingsw.model.general.Level;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DevCardMarketTest {

    @Test
    void testBuyCard() {
        // initialize DevCardMarket
        // todo replace this with factory when ready
        Resources cost = new Resources();
        cost.add(ResourceType.COINS, 1);
        DevCard[] cards = new DevCard[]{
                new DevCard(1, "card1", Level.LOW, Color.BLUE, cost, null),
                new DevCard(1, "card2", Level.LOW, Color.BLUE, cost, null),
                new DevCard(1, "card3", Level.LOW, Color.BLUE, cost, null),
        };
        // init game
        DevCardMarket dcm = new DevCardMarket(cards);
        Game g = new Game(MarketTrayFactory.BuildMarketTray(GameSettingsLevel.LOW), dcm);
        try {
            g.addPlayer("Joe Chapter");
        } catch (GameException ge) {
            ge.printStackTrace();
        }

        // add resources to player
        Resources r = new Resources();
        r.add(ResourceType.COINS, 2);
        r.add(ResourceType.SHIELDS, 2);
        g.getPlayers()[0].getBoard().getStrongbox().deposit(r);

        // TODO needs strongbox initialization
        for (int i = 0; i < 4; i++) {
            try {
                boolean success = g.getDevCardMarket().buyCard(0, g.getPlayers()[0]);

                assertEquals(i < 2 ? true : false, success);

            } catch (DevCardMarketException dcme) {
                assertEquals(3, i);
            }
        }


    }
}