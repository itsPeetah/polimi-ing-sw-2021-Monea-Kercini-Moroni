package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.general.Resources;

import java.util.ArrayList;

/**
 * Development card market rep class.
 */
public class DevCardMarket {

    // matrix of array list
    //first [] symbolizes the color in this order: blue, purple, green, yellow
    //second [] symbolizes the level
    private ArrayList<DevCard> availableCards[][] = new ArrayList[4][3];

    /**
     * Constructor.
     * @param cards Cards to be made available at start.
     */
    public DevCardMarket(ArrayList<DevCard> cards){

        //Adding cards in the correct position
        for (int j = 0; j < 4; j++) { //4 colors
            for (int i = 0; i < 3; i++) { //3 levels
                for (int k = 0; k < 4; k++) { //4 cards for each
                    availableCards[j][i].add(cards.get(j*12 + i*4 +k));
                }

            }
        }

    }

    /**
     * Returns the available DevCards for purchase
     * @return Arraylist of currently available cards.
     */
    public ArrayList<DevCard> getAvailableCards(){

        ArrayList<DevCard> available = new ArrayList<>();

        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 3; i++) {
                //The cards available for purchase are the top ones
                available.add(availableCards[j][i].get(availableCards[j][i].size()));
            }
        }

        return available;

    }


    /**
     * Removes a card from the market.
     * @param card Card to be removed.
     * @return Whether the operation was successful.
     * */

    public boolean buyCard(DevCard card) throws DevCardMarketException {

        //Search for the card the player has chosen to remove
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 3; i++) {
                //The cards available for purchase are the top ones
                if (card.equals(availableCards[j][i].get(availableCards[j][i].size()))) {
                    //Remove found card
                    availableCards[j][i].remove(availableCards[j][i].size());
                    return true;
                }
            }
        }
        //If it was not found
        throw new DevCardMarketException("Trying to buy a card that is not available for sale.");
    }


    public boolean isAnyColumnFree(){
        for (int i = 0; i < 0; i++) {

        }
        return true;
    }
}