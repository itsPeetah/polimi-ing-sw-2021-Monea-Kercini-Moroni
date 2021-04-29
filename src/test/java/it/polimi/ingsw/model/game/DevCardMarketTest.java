package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.game.util.GameSettingsLevel;
import it.polimi.ingsw.model.game.util.MarketTrayFactory;
import it.polimi.ingsw.model.general.Color;
import it.polimi.ingsw.model.general.Level;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DevCardMarketTest {

    @Test
    void testBuyCard() {
        // initialize DevCardMarket
        // todo replace this with factory when ready
        Resources cost = new Resources();
        cost.add(ResourceType.COINS, 1);

        ArrayList<DevCard> cards = new ArrayList<DevCard>();
        cards.add(new DevCard(1, "card1", Level.LOW, Color.BLUE, cost, null));
        cards.add(new DevCard(1, "card2", Level.LOW, Color.BLUE, cost, null));
        //cards.add(new DevCard(1, "card3", Level.LOW, Color.BLUE, cost, null));

        DevCardMarket dcm = new DevCardMarket(cards);
        Player p = new Player("Joe Chapter");

        // add resources to player
        Resources r = new Resources();
        r.add(ResourceType.COINS, 2);
        r.add(ResourceType.SHIELDS, 2);

        p.getBoard().getStrongbox().deposit(r);

        boolean result = dcm.buyCard(1, p);
        assert(result);
        result = dcm.buyCard(0, p);
        assert (result);
        try{
            result = dcm.buyCard(0,p);
        } catch (ArrayIndexOutOfBoundsException ex){
            System.out.println(ex.getMessage());
        }

    }
}