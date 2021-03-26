package it.polimi.ingsw.model.PlayerLeaders;

import it.polimi.ingsw.model.cards.LeadCard;

import java.util.Arrays;

public class PlayerLeaders {

    private LeadCard[] cards;
    private CardState[] cardStates;

    /** da finire

    public LeadCard[] getPlayableCards(){
        LeadCard[] playableCards;

        for(int i = 0; i < cards.length; i++){
            if (cardStates[i] == CardState.INHAND);
        }
    }

     */

    public void playCard(int index){
        cardStates[index] = CardState.PLAYED;
    }


    /**
     * Gives the player the leaders he has in his hand and sets the cardstates INHAND
     * @param cards the leaders
     */

    public void setCards(LeadCard[] cards) {
        this.cards = cards;
        Arrays.fill(this.cardStates, CardState.INHAND);
    }

    public int getPlayedCardVictoryPoints(){

        int vp = 0;

        for(int i = 0; i < cards.length; i++){
            if (cardStates[i] == CardState.PLAYED){
                vp += cards[i].getVictoryPoints;
            }
        }

        return vp;
    }
}
