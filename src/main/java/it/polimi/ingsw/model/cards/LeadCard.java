package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.general.Resources;

public class LeadCard {

    //fake method !!!!!!!!!!
    public Resources getExtraWarehouseSpace(){
        Resources r = new Resources();
        return r;
    }
}

class LeadCardRequirements {
    Integer[] devCardColors;
    Integer[] devCardLevels;
    Resources resourceAmounts;

    public LeadCardRequirements(Integer[] devCardColors, Integer[] devCardLevels, Resources resourceAmounts) {
        this.devCardColors = devCardColors;
        this.devCardLevels = devCardLevels;
        this.resourceAmounts = resourceAmounts;
    }
}

class LeadCardAbility {

}

