package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.model.general.Resources;


public class Strongbox {

    private Resources content;

    /**
     * Add the resources taken as input in the Strongbox
     * @param resources
     */

    public void deposit(Resources resources){
        content.add(resources);
    }

    /**
     * Remove the resources taken as input from the Strongbox
     * @param resources
     * @return
     */

    public Resources withdraw(Resources resources){
        try {
            content.remove(resources);
        }
        catch (Exception e) {
            //e.printStackTrace();
        }
        return resources;
    }

    public Strongbox() {
        this.content = new Resources();
    }

    /**
     *
     * @return the total amount (number) of resources there are in Strongbox
     */

    public int getResourceAmountStrongbox(){
        return content.getTotalAmount();
    }

    /**
     *
     * @return all the available resources in the Strongbox
     */

    public Resources getResourcesAvailable(){
        return content;
    }
}
