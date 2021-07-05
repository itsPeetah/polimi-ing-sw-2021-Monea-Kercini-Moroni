package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.general.Resources;

public abstract class Card {
    private Integer victoryPoints;
    private String cardId;

    public Card() {
    }

    public Card(Integer victoryPoints, String cardId) {
        this.victoryPoints = victoryPoints;
        this.cardId = cardId;
    }

    /**
     * Get the victory points of a card.
     * @return VP of a card.
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * Get the ID of the card.
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * Check if a player can afford the card.
     */
    public abstract Boolean affordable(Player player);
}
