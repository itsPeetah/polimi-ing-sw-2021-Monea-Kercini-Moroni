package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.general.*;

public class DevCard extends Card {
    private Level level;
    private Color color;
    private Resources cost;
    private Production production;

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
     * @param player
     * @return
     */
    @Override
    public Boolean affordable(Player player) {
        return player.getBoard().getResourcesAvailable().isGreaterThan(cost);
    }

    /**
     * Perform the action of playing the card (if affordable) pay its cost
     * @param player
     */
    @Override
    public void play(Player player) {

    }
}
