package it.polimi.ingsw.model.playerleaders;

import it.polimi.ingsw.model.cards.LeadCard;

import java.util.Arrays;
import java.util.ArrayList;

public class PlayerLeaders {

    private LeadCard[] cards;
    private CardState[] cardStates;

    public LeadCard[] getCards() {
        return cards;
    }

    public CardState[] getCardStates() {
        return cardStates;
    }

    /**
     *
     * @return All the cards the player has in hand.
     */

    public ArrayList<LeadCard> getPlayableCards(){

        ArrayList<LeadCard> playableCards = new ArrayList<LeadCard>();

        for(int i = 0; i < cards.length; i++){
            if (cardStates[i] == CardState.INHAND){
                playableCards.add(cards[i]);
            }
        }
        return playableCards;
    }

    /**
     *
     * @return All the LeadCards that have been played.
     */

    public ArrayList<LeadCard> getPlayedCards(){

        ArrayList<LeadCard> playedCards = new ArrayList<LeadCard>();

        for(int i = 0; i < cards.length; i++){
            if (cardStates[i] == CardState.PLAYED){
                playedCards.add(cards[i]);
            }
        }
        return playedCards;
    }

    /**
     * Sets the state of the card at index position as PLAYED
     * @param index is the position of the card that the player plays
     * @throws Exception if the chosen card is already played or discarded
     */
    public void playCard(int index) throws PlayerLeadersException{
        if(cardStates[index] == CardState.INHAND) {
            cardStates[index] = CardState.PLAYED;
        }else{
            throw new PlayerLeadersException("Leader Card is already played or discarded.");
        }
    }

    /**
     * Sets the state of the card in input as PLAYED
     * @param leadCard must be a card of the player
     * @throws PlayerLeadersException
     */
    public void playCard(LeadCard leadCard) throws PlayerLeadersException {
        int leadIndex = Arrays.asList(cards).indexOf(leadCard);
        if(leadIndex == -1) throw new PlayerLeadersException("Leader Card is not present.") ;
        else playCard(leadIndex);
    }

    /**
     * Sets the state of the card at index position as DISCARDED
     * @param index is the position of the card that the player discards
     * @throws Exception if the chosen card is already played or discarded
     */
    public void discardCard(int index) throws PlayerLeadersException{
        if(cardStates[index] == CardState.INHAND) {
            cardStates[index] = CardState.DISCARDED;
        }else{
            throw new PlayerLeadersException("Leader Card is already played or discarded.");
        }
    }

    /**
     * Sets the state of the card in input as DISCARDED
     * @param leadCard must be a card of the player
     * @throws PlayerLeadersException
     */
    public void discardCard(LeadCard leadCard) throws PlayerLeadersException {
        int leadIndex = Arrays.asList(cards).indexOf(leadCard);
        if(leadIndex == -1) throw new PlayerLeadersException("Leader Card is not present.") ;
        else discardCard(leadIndex);
    }


    /**
     * Gives the player the leader cards he has in his hand and sets the card states INHAND
     * @param cards the leader cards
     */

    public void setCards(LeadCard[] cards) {
        this.cards = cards;
        Arrays.fill(this.cardStates, CardState.INHAND);
    }

    /**
     *
     * @return the total victory points from all played leaders
     */

    public int getPlayedCardVictoryPoints(){

        int vp = 0;

        for(int i = 0; i < cards.length; i++){
            if (cardStates[i] == CardState.PLAYED){
                vp += cards[i].getVictoryPoints();
            }
        }
        return vp;
    }

    public PlayerLeaders(int PlayerLeaderMax) {
        this.cards = new LeadCard[PlayerLeaderMax];
        this.cardStates = new CardState[PlayerLeaderMax];

    }
}
