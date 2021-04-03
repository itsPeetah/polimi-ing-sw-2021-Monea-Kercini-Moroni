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

    public Level getLevel() {
        return level;
    }

    public Color getColor() {
        return color;
    }

    public Resources getCost() {
        return cost;
    }

    public Production getProduction() {
        return production;
    }

    @Override
    public Boolean affordable(Player player) {
        return player.getBoard().getResourcesAvailable().isGreaterThan(cost);
    }

    // TODO missing method
    @Override
    public void play(Player player) {

    }
}
