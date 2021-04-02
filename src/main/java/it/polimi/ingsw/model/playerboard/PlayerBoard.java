package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.model.general.Resources;

public class PlayerBoard {

    private int faithPoints;
    private boolean[] reportsAttended;
    private Warehouse warehouse;
    private Strongbox strongbox;
    private ProductionPowers productionPowers;

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Strongbox getStrongbox() {
        return strongbox;
    }

    /**
     *
     * @param amount to add to faith points (moves of faith marker)
     */

    public void incrementFaithPoints(int amount){
        faithPoints += amount;
    }

    /**
     * Set report at index as attended
     * @param index should be 0,1,2 depending on the report the player attended
     */

    public void attendReport(int index){
        reportsAttended[index] = true;
    }

    /**
     *
     * @param reportsAttended default should be 3
     */

    public PlayerBoard(int reportsAttended) {
        this.faithPoints = 0;
        this.reportsAttended = new boolean[reportsAttended];
        this.warehouse = new Warehouse();
        this.strongbox = new Strongbox();
        this.productionPowers = new ProductionPowers(3);
    }

    /**
     * Calculates victory points from faith track, reports attended, resources and development cards owned.
     * @return sum of victory points the board has
     */

    public int getBoardVictoryPoints(){

        int faithVictoryPoints = 0;

        //victory points from faith track

        if(faithPoints>23){
            faithVictoryPoints+= 20;
        }
        else if(faithPoints>20){
            faithVictoryPoints+= 16;
        }
        else if(faithPoints>17){
            faithVictoryPoints+= 12;
        }
        else if(faithPoints>14){
            faithVictoryPoints+= 9;
        }
        else if(faithPoints>11){
            faithVictoryPoints+= 6;
        }
        else if(faithPoints>5){
            faithVictoryPoints+= 2;
        }
        else if(faithPoints>2){
            faithVictoryPoints+= 1;
        }

        //victory points from reports attended

        if(reportsAttended[0]){
            faithVictoryPoints+= 2;
        }
        if(reportsAttended[1]){
            faithVictoryPoints+= 3;
        }
        if(reportsAttended[2]){
            faithVictoryPoints+= 4;
        }

        //total victory points from the board

        return faithVictoryPoints + ((warehouse.getResourceAmountWarehouse()+ strongbox.getResourceAmountStrongbox())/5 + productionPowers.getOwnedCardsVictoryPoints());
    }

    /**
     *
     * @return all resources available from the board (warehouse + strongbox)
     */

    public Resources getResourcesAvailable(){
        Resources available = new Resources();
        available.add(warehouse.getResourcesAvailable());
        available.add(strongbox.getResourcesAvailable());
        return available;
    }
}