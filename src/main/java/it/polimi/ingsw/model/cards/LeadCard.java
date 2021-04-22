package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.playerleaders.PlayerLeadersException;

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
     * Check if a player can afford the leader card.
     */
    @Override
    public Boolean affordable(Player player) {
        return requirements.check(player);
    }

    /**
     * Perform the action of playing the card and apply its abilities
     */
    public void play(Player player) {
        // Make sure the card is actually affordable
        assert affordable(player);

        // Apply the abilities
        player.getBoard().getWarehouse().expandWithLeader(this);
        player.getBoard().getProductionPowers().addLeadCardProduction(this);
        player.getBoard().addDiscount(ability.getResourceDiscount());
        player.getBoard().addMarble(ability.getWhiteMarbleReplacement());

        // Put the leader in the player board
        try {
            player.getLeaders().playCard(this);
        } catch (PlayerLeadersException e) {
            e.printStackTrace();
        }
    }

    /**
     * Discards the leader and gains an extra faith point
     * @param player
     */

    public void discard(Player player) {

        // Get the extra faith point.
        player.getBoard().incrementFaithPoints(1);

        // Discard the leader
        try {
            player.getLeaders().discardCard(this);
        } catch (PlayerLeadersException e) {
            e.printStackTrace();
        }
    }
}