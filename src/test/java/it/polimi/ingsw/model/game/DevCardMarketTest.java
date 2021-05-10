package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.game.util.DevCardMarketFactory;
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
        DevCardMarket dcm = DevCardMarketFactory.BuildDevCardMarket(GameSettingsLevel.HIGH);

        //available cards
        ArrayList<DevCard> aval = dcm.getAvailableCards();

        //printing all devCard market cards
        for (int i = 0; i < aval.size(); i++) {
            System.out.println(aval.get(i).getCardId());
        }

        //trying to remove the first of the available cards in DCM
        try {
            dcm.buyCard(aval.get(0));
        }catch (Exception e){
            e.printStackTrace();
        }

        ArrayList<DevCard> aval2 = dcm.getAvailableCards();

        aval2 = dcm.getAvailableCards();
        //printing all devCard market cards
        for (int i = 0; i < aval.size(); i++) {
            System.out.println(aval2.get(i).getCardId());
        }

        //confirming that only the first card was removed

        assertFalse(aval.get(0).equals(aval2.get(0)));

        for (int i = 1; i < aval.size(); i++) {
            assertTrue(aval.get(i).equals(aval2.get(i)));
        }

        //buying 3 more cards of that type -> only 11 cards should be available after this
        for (int i = 0; i < 3; i++) {
            aval = dcm.getAvailableCards();
            try {
                dcm.buyCard(aval.get(0));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        aval = dcm.getAvailableCards();

        System.out.println("-------------------------------");
        //printing all devCard market cards
        for (int i = 0; i < aval.size(); i++) {
            System.out.println(aval.get(i).getCardId());
        }

        assertEquals(11, aval.size());

    }

    @Test
    void discard2test(){

        // initialize DevCardMarket
        DevCardMarket dcm = DevCardMarketFactory.BuildDevCardMarket(GameSettingsLevel.HIGH);

        //available cards
        ArrayList<DevCard> aval = dcm.getAvailableCards();

        //printing all devCard market cards
        for (int i = 0; i < aval.size(); i++) {
            System.out.println(aval.get(i).getCardId());
        }

        //trying to discard 2 blue cards
        dcm.discard2(Color.BLUE);

        //available cards
        ArrayList<DevCard> aval2 = dcm.getAvailableCards();

        //printing all devCard market cards
        for (int i = 0; i < aval2.size(); i++) {
            System.out.println(aval2.get(i).getCardId());
        }

        assertFalse(aval.get(0).equals(aval2.get(0)));

        //discarding 2 more blue cards
        //after this the first level blue cards should disappear

        dcm.discard2(Color.BLUE);

        //available cards
        ArrayList<DevCard> aval3 = dcm.getAvailableCards();

        //printing all devCard market cards
        for (int i = 0; i < aval3.size(); i++) {
            System.out.println(aval3.get(i).getCardId());
        }

        assertEquals(11, aval3.size());


        //Now let's try to remove the whole blue cards
        dcm.discard2(Color.BLUE);
        dcm.discard2(Color.BLUE);
        dcm.discard2(Color.BLUE);

        //Before removing the last 2 blue cards check if any columns are free
        assertEquals(false, dcm.isAnyColumnFree());

        dcm.discard2(Color.BLUE);

        //There are no more blue cards left

        //available cards
        ArrayList<DevCard> aval4 = dcm.getAvailableCards();

        //printing all devCard market cards
        for (int i = 0; i < aval4.size(); i++) {
            System.out.println(aval4.get(i).getCardId());
        }

        assertEquals(9, aval4.size());
        assertEquals(true, dcm.isAnyColumnFree());

    }
}