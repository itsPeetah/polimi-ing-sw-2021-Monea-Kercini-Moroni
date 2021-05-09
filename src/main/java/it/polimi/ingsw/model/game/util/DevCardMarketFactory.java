package it.polimi.ingsw.model.game.util;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.CardManager;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.game.DevCardMarket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * DevCardMarket factory wrapper class.
 */
public class DevCardMarketFactory {

    private static final int CARDS_PER_TIER = 4;

    /**
     * Build a Dev Card Market instance based on the desired settings
     * @param cardAmount amount level of cards to be included in the the DCM.
     */
    public static DevCardMarket BuildDevCardMarket(GameSettingsLevel cardAmount){
        // Get all cards
        ArrayList<DevCard> allCards = CardManager.loadDevCardsFromJson();


        // Only take right cards
        ArrayList<DevCard> finalCards = new ArrayList<DevCard>();
        for(int i = 0; i < allCards.size(); i++){
            switch (cardAmount){
                // If LOW cards, just take cards 1 and 2
                case LOW: if(i%CARDS_PER_TIER < 2) finalCards.add(allCards.get(i)); break;
                // If MEDIUM cards, just take cards 1, 2 and 3
                case MEDIUM: if(i%CARDS_PER_TIER < 3) finalCards.add(allCards.get(i)); break;
                // If HIGH cards take cards 1, 2, 3 and 4
                default: finalCards.add(allCards.get(i)); break;
            }
        }

        //shuffle cards in a specific way: conserving the color order and the level order
        for (int start = 0; start <= 44; start+=4) {
            Collections.shuffle(allCards.subList(start, start+4));
        }

        // Instantiate and return
        DevCardMarket dcm;
        switch (cardAmount){
            // If LOW cards, just take cards 1 and 2
            case LOW: dcm = new DevCardMarket(finalCards, 2); break;
            // If MEDIUM cards, just take cards 1, 2 and 3
            case MEDIUM: dcm = new DevCardMarket(finalCards, 3); break;
            // If HIGH cards take cards 1, 2, 3 and 4
            default: dcm = new DevCardMarket(finalCards, 4); break;
        }
        return dcm;
    }

}
