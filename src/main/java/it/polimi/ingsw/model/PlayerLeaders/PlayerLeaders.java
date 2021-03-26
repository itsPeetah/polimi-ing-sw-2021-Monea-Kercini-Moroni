package it.polimi.ingsw.model.PlayerLeaders;

import it.polimi.ingsw.model.cards.LeadCard;
import it.polimi.ingsw.model.general.Production;

import java.util.Arrays;
import java.util.ArrayList;

public class PlayerLeaders {

    private LeadCard[] cards;
    private CardState[] cardStates;



    public ArrayList<LeadCard> getPlayableCards(){

        ArrayList<LeadCard> playableCards = new ArrayList<LeadCard>();

        for(int i = 0; i < cards.length; i++){
            if (cardStates[i] == CardState.INHAND){
                playableCards.add(cards[i]);
            }
        }
        return playableCards;
    }


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
