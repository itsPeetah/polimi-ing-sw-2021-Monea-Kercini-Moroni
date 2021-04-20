package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.general.*;

public class DevCard extends Card {
    private Level level;
    private Color color;
    private Resources cost;
    private Production production;

    public DevCard() {
    }

    public DevCard(Integer victoryPoints, String cardId, Level level, Color color, Resources cost, Production production) {
        super(victoryPoints, cardId);
        this.level = level;
        this.color = color;
        this.cost = cost;
        this.production = production;
    }

    /**
     * @return level of the Dev Card
     */
    public Level getLevel() {
        return level;
    }

    /**
     * @return color of the Dev Card
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return cost of the Dev Card
     */
    public Resources getCost() {
        return cost;
    }

    /**
     * @return production of the Dev Card
     */
    public Production getProduction() {
        return production;
    }

    /**
     * Check if a player can afford the Dev Card
     */
    @Override
    public Boolean affordable(Player player) {
        // Set the cost after the possible discount
        Resources costAfterDiscount = new Resources();
        costAfterDiscount.add(cost);
        cost.removeWithoutException(player.getBoard().getDiscount());

        // Check if the player can afford the card
        return player.getBoard().getResourcesAvailable().isGreaterThan(costAfterDiscount);
    }

    /**
     * TODO remove the method or update it with the play logic
     */
    @Override
    public void play(Player player) {
    }
}
