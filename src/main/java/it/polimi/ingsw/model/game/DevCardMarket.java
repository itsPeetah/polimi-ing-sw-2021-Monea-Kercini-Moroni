package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.general.Resources;

import java.util.ArrayList;

/**
 * Development card market rep class.
 */
public class DevCardMarket {

    // members todo perhaps differentiate tiers/colors
    private ArrayList<DevCard> availableCards;

    /**
     * Constructor.
     * @param cards Cards to be made available at start.
     */
    public DevCardMarket(ArrayList<DevCard> cards){
        // Add cards to the available
        availableCards = cards;
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
    public boolean buyCard(int index, Player player) throws ArrayIndexOutOfBoundsException {
        if(index >= availableCards.size()) throw new ArrayIndexOutOfBoundsException("Trying to buy a card that does not exist.");
        // if(!availableCards.get(index).affordable(player)) return false;
        // Todo move buying logic here from controller?
        DevCard dc = availableCards.remove(index);
        return true;
    }
}