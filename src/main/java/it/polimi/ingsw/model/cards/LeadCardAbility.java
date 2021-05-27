package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.general.Production;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;

public class LeadCardAbility {
    private Resources resourceDiscount;
    private Resources extraWarehouseSpace;
    private ResourceType whiteMarbleReplacement; // IMPORTANT: if BLANK, it means the ability has no replacements for the white marble
    private Production production;

    public LeadCardAbility() {
    }

    public LeadCardAbility(Resources resourceDiscount, Resources extraWarehouseSpace, ResourceType whiteMarbleReplacement, Production production) {
        this.resourceDiscount = resourceDiscount;
        this.extraWarehouseSpace = extraWarehouseSpace;
        this.whiteMarbleReplacement = whiteMarbleReplacement;
        this.production = production;
    }

    @Override
    public String toString() {
        return "LeadCardAbility{" +
                "Resource market discount: " + resourceDiscount +
                ", Warehouse expansion slots:" + extraWarehouseSpace +
                ", Grey marble replacement: " + (whiteMarbleReplacement == ResourceType.BLANK ? "None" : whiteMarbleReplacement.toString()) +
                ", Production power: " + production +
                '}';
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
     * @return replacement for the white marble
     */
    public ResourceType getWhiteMarbleReplacement() {
        return whiteMarbleReplacement;
    }

    /**
     * @return additional Production
     */
    public Production getProduction() {
        return production;
    }
}
