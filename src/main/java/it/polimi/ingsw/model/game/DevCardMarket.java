package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.general.Resources;

import java.util.ArrayList;

public class DevCardMarket {

    private ArrayList<DevCard> availableCards;

    /**
     * Constructor.
     * @param cards Cards to be made available at start.
     */
    public DevCardMarket(DevCard[] cards){
        // Add cards to the available
        availableCards = new ArrayList<DevCard>();
        for(DevCard dc : cards)
            availableCards.add(dc);
    }

    /**
     * Returns the available DevCards.
     * @return Array of currently available cards.
     */
    public DevCard[] getAvailableCards(){
        DevCard[] dcs = new DevCard[availableCards.size()];
        return availableCards.toArray(dcs);
    }

    /**
     * Removes a card from the market.
     * @param index Card to be removed.
     * @return Whether the operation was successful.
     */
    public boolean buyCard(int index, Player player) throws DevCardMarketException {
        if(index >= availableCards.size()) throw new DevCardMarketException("Trying to buy a card that does not exist.");
        if(!availableCards.get(index).affordable(player)) return false;
        DevCard dc = availableCards.remove(index);
        return true;
    }
}