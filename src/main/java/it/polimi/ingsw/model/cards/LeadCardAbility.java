package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.general.Production;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;

public class LeadCardAbility {
    private Resources resourceDiscount;
    private Resources extraWarehouseSpace;
    private ResourceType greyMarbleReplacement; // IMPORTANT: if BLANK, it means the ability has no replacements for the grey marble
    private Production production;

    public LeadCardAbility() {
    }

    public LeadCardAbility(Resources resourceDiscount, Resources extraWarehouseSpace, ResourceType greyMarbleReplacement, Production production) {
        this.resourceDiscount = resourceDiscount;
        this.extraWarehouseSpace = extraWarehouseSpace;
        this.greyMarbleReplacement = greyMarbleReplacement;
        this.production = production;
    }

    /**
     * @return discount for the market
     */
    public Resources getResourceDiscount() {
        return resourceDiscount;
    }

    /**
     * @return extra space for the warehouse
     */
    public Resources getExtraWarehouseSpace() {
        return extraWarehouseSpace;
    }

    /**
     * @return replacement for the grey marble
     */
    public ResourceType getGreyMarbleReplacement() {
        return greyMarbleReplacement;
    }

    /**
     * @return additional Production
     */
    public Production getProduction() {
        return production;
    }
}
