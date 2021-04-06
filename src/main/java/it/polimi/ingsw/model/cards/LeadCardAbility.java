package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.general.Production;
import it.polimi.ingsw.model.general.Resources;

public class LeadCardAbility {
    private Resources resourceDiscount;
    private Resources extraWarehouseSpace;
    private Resources greyMarbleReplacement;
    private Production production;

    public LeadCardAbility(Resources resourceDiscount, Resources extraWarehouseSpace, Resources greyMarbleReplacement, Production production) {
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
    public Resources getGreyMarbleReplacement() {
        return greyMarbleReplacement;
    }

    /**
     * @return additional Production
     */
    public Production getProduction() {
        return production;
    }
}
