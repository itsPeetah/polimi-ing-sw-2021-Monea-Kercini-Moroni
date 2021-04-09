package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.general.Resources;

public abstract class Card {
    private Integer victoryPoints;
    private String cardId;

    public Card(Integer victoryPoints, String cardId) {
        this.victoryPoints = victoryPoints;
        this.cardId = cardId;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public String getCardId() {
        return cardId;
    }

    public abstract Boolean affordable(Player player);

    public abstract void play(Player player);
}
