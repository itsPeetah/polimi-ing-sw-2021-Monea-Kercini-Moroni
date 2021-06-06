package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.model.general.Level;
import it.polimi.ingsw.model.general.Production;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;

import java.util.ArrayList;

public class ProductionPowers {

    private static final Production basicProduction = new Production(new Resources().add(ResourceType.CHOICE, 2), new Resources().add(ResourceType.CHOICE, 1));

    private final DevCard[][] cardPile;  //The first symbolizes the pile and each can have 3 cards maximum
    private final ArrayList<Production> leadProduction;
    private final static int MAX_LEADER_PRODUCTIONS = 2;

    /**
     *
     * @param piles in the default rules should be 3
     */

    public ProductionPowers(int piles) {
        this.cardPile = new DevCard[piles][3];
        this.leadProduction = new ArrayList<>();
    }

    /**
     * Searches all card piles and returns the card on top of each + Production from LeadCards
     * @return array of production of all the top development cards on all piles
     */
    public ArrayList<Production> getAvailableProductions(){

        ArrayList<Production> AvailableProductions = new ArrayList<Production>();

        for (DevCard[] devCards : cardPile) {
            //If the card exists I add the production of the top card
            //Here might need to revise if we want to add cards of higher level
            if (devCards[2] != null) {
                AvailableProductions.add(devCards[2].getProduction());
            } else if (devCards[1] != null) {
                AvailableProductions.add(devCards[1].getProduction());
            } else if (devCards[0] != null) {
                AvailableProductions.add(devCards[0].getProduction());
            }
        }

        AvailableProductions.add(basicProduction);

        AvailableProductions.addAll(leadProduction);

        return AvailableProductions;
    }

    /**
     *
     * @return all the Development Cards The player has purchased
     */

    public ArrayList<DevCard> getOwnedDevCards() {

        ArrayList<DevCard> AvailableCards = new ArrayList<DevCard>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cardPile[i][j] != null) {
                    AvailableCards.add(cardPile[i][j]);
                }
            }
        }
        return AvailableCards;
    }


    /**
     * Get visible cards
     * //todo test this method
     */

    public DevCard[] getVisibleDevCards(){

        DevCard[] visible = new DevCard[3];
        for (int i = 0; i < 3; i++) {
            visible[i] = cardPile[i][0];

            for (int j = 0; j < 3; j++) {

                //if the slot is empty and it's not the first slot, get the previous slot (the card on top)

                if (cardPile[i][j] == null && j!=0) {
                    visible[i] = cardPile[i][j-1];
                    break;
                }
            }

            //the third slot

            if(cardPile[i][2] != null){
                visible[i] = cardPile[i][2];
            }
        }
        return visible;
    }

    /**
     * Method that returns true if a dev card can be placed on top of the selected pile
     * @param card the devCard you are trying to place
     * @param pos the pile (position) where you are trying to place the devcard
     * @return
     */

    public boolean canDevCardBePlaced(DevCard card, int pos){

        //if card level is 1 then we need the pile to be clear
        if(card.getLevel() == Level.LOW){
            return cardPile[pos][0] == null;

        //if card level is 2 then we need the pile to have a level one card but the second one to be free
        }else if (card.getLevel() == Level.MEDIUM){
            return cardPile[pos][0] != null && cardPile[pos][1] == null;

        //if card level is 3 then we need the pile to have a level two card but the third one to be free
        }else{
            return cardPile[pos][1] != null && cardPile[pos][2] == null;
        }
    }
    //*note - modifying this method could be possible to check if a dev card can be placed at all, alternatively the method can be called 3 times

    /**
     * adds development card on top of the selected pile
     * @param devCard to add
     * @param position of the pile
     */

    public void addDevCard(DevCard devCard, int position){

        cardPile[position][devCard.getLevel().toInteger()] = devCard;
    }

    /**
     * Adds the extra production from LeadCard
     * @param leadCard the LeaderCard that gives the extra production power
     */
    public void addLeadCardProduction(LeadCard leadCard){
        if(leadProduction.size() <= MAX_LEADER_PRODUCTIONS) leadProduction.add(leadCard.getAbility().getProduction());
        else throw new IndexOutOfBoundsException("Maximum productions: " + MAX_LEADER_PRODUCTIONS);
    }

    /**
     *
     * @return sum of victory points all development cards you have give you
     */

    public int getOwnedCardsVictoryPoints(){
        int vp = 0;
        for (DevCard[] devCards : cardPile) {
            for (DevCard devCard : devCards) {
                if (devCard != null) {
                    vp += devCard.getVictoryPoints();
                }
            }
        }
        return vp;
    }

    public static Production getBasicProduction() {
        return basicProduction;
    }
}
