package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.cards.DevCard;

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
     * Return the available DevCards.
     * @return Array of currently available cards.
     */
    public DevCard[] getAvailableCards(){
        DevCard[] dcs = new DevCard[availableCards.size()];
        return availableCards.toArray(dcs);
    }

    /**
     * Remove a card from the market.
     * @param index Card to be removed.
     */
    public void buyCard(int index){
        DevCard dc = availableCards.remove(index);
    }

}
