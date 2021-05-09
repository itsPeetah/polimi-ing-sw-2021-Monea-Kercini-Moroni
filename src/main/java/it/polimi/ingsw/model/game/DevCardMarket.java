package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.general.Color;
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
     *
     * Constructor.
     * The input order should be: blue, low, random; blue, medium, random and so on (Color, level, 1 of the 4)
     * @param cards Cards to be made available at start.
     *
     */
    public DevCardMarket(ArrayList<DevCard> cards){

        //Adding cards in the correct position
        for (int j = 0; j < 4; j++) { //4 colors
            for (int i = 0; i < 3; i++) { //3 levels
                availableCards[j][i] = new ArrayList<>();
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
                available.add(availableCards[j][i].get(availableCards[j][i].size()-1));
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
                if (card.equals(availableCards[j][i].get(availableCards[j][i].size()-1))) {
                    //Remove found card
                    availableCards[j][i].remove(availableCards[j][i].size()-1);
                    return true;
                }
            }
        }
        //If it was not found
        throw new DevCardMarketException("Trying to buy a card that is not available for sale.");
    }


    public boolean isAnyColumnFree(){
        int column_cards;

        //Check all columns
        for (int i = 0; i < 4; i++) {

            column_cards=0;

            for(int j=0; j<3; j++){
                if(availableCards[i][j].size()!=0){
                    column_cards+=1;
                }
            }

            if(column_cards==0){
                return true;
            }
        }
        return false;
    }

    /**
     * Discrads 2 dev cards of that color, starting from the lower ones to the higher levels
     * @param color
     * @return true if all the cards of that color are finished (free column)
     *          in this case Lorenzo has won
     */

    public boolean discard2 (Color color){

        int i = 0;
        int discarded = 0;
        int c = 0;

        //converting color to integer
        switch (color){
            case BLUE:
                c=0;
                break;
            case PURPLE:
                c=1;
                break;
            case GREEN:
                c=2;
                break;
            case YELLOW:
                c=3;
                break;
        }

        while(i<3 && discarded < 2){

            //Check if that level is over
            if (availableCards[c][i].size()==0){
                //Change level
                i++;
            }else{
                //Cards is removed
                availableCards[c][i].remove(availableCards[c][i].size()-1);
                discarded++;
            }
        }

        //If a column was finished
        if (isAnyColumnFree()){
            return true;
        }else{
            return false;
        }
    }
}