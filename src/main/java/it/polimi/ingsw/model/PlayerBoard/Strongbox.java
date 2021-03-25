package it.polimi.ingsw.model.PlayerBoard;

import it.polimi.ingsw.model.general.Resources;

public class Strongbox {

    private Resources content;

    public void deposit(Resources resources){
        content.add(resources);
    }

    public Resources withdraw(Resources resources){
        try { content.remove(resources);
        } catch (Exception e) {
            e.printStackTrace(); }

        return resources;
    }

    public Strongbox() {
        this.content = new Resources();
    }

    public int getResourceAmountStrongbox(){
        return content.getTotalAmount();
    }
}
