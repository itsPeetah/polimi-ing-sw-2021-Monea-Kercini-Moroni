package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.general.Resources;

public class LeadCard extends Card {

    private LeadCardRequirements requirements;
    private LeadCardAbility ability;

    public LeadCard(Integer victoryPoints, String cardId, LeadCardRequirements requirements, LeadCardAbility ability) {
        super(victoryPoints, cardId);
        this.requirements = requirements;
        this.ability = ability;
    }

    // TODO fake method !!!!!!!!!!
    public Resources getExtraWarehouseSpace(){
        Resources r = new Resources();
        return r;
    }

    public LeadCardRequirements getRequirements() {
        return requirements;
    }

    public LeadCardAbility getAbility() {
        return ability;
    }

    @Override
    public Boolean affordable(Player player) {
        return requirements.check(player);
    }

    @Override
    public void play(Player player) {

    }
}