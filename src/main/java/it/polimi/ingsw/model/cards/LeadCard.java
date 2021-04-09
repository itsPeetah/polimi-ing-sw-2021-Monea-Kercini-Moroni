package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.general.Resources;

public class LeadCard extends Card {

    private LeadCardRequirements requirements;
    private LeadCardAbility ability;

    public LeadCard() {
    }

    public LeadCard(Integer victoryPoints, String cardId, LeadCardRequirements requirements, LeadCardAbility ability) {
        super(victoryPoints, cardId);
        this.requirements = requirements;
        this.ability = ability;
    }

    /**
     * @return ability of the leader
     */
    public LeadCardAbility getAbility() {
        return ability;
    }

    /**
     * Check if a user can afford the leader card.
     * @param player
     * @return
     */
    @Override
    public Boolean affordable(Player player) {
        return requirements.check(player);
    }

    /**
     * Perform the action of playing the card and apply its static abilities
     * @param player
     */
    @Override
    public void play(Player player) {
        player.getBoard().getWarehouse().expandWithLeader(this);
        /* TODO mancano gli effetti delle altre abilità nelle altre classi */
    }
}