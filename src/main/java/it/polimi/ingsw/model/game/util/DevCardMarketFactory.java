package it.polimi.ingsw.model.game.util;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.CardManager;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.game.DevCardMarket;

import java.util.ArrayList;
import java.util.Comparator;

public class DevCardMarketFactory {

    /**
     * Build a Dev Card Market instance based on the desired settings
     * @param cardAmount amount level of cards to be included in the the DCM.
     */
    public static DevCardMarket BuildDevCardMarket(GameSettingsLevel cardAmount){

        final int CARDS_PER_TIER = 4;

        // Get all cards
        ArrayList<DevCard> allCards = CardManager.loadDevCardsFromJson();
        // Sort them based on their id
        allCards.sort(Comparator.comparing(Card::getCardId));
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
        // Instantiate and return
        DevCardMarket dcm = new DevCardMarket(finalCards);
        return dcm;
    }

}
