package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerBoard {

    private int faithPoints;
    private Boolean[] reportsAttended;
    private Warehouse warehouse;
    private Strongbox strongbox;
    private ProductionPowers productionPowers;

    // Leader's effects:
    private Resources leadDiscount;
    private List<ResourceType> leadMarbles;

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Strongbox getStrongbox() {
        return strongbox;
    }

    public ProductionPowers getProductionPowers() {
        return productionPowers;
    }

    /**
     *
     * @param amount to add to faith points (moves of faith marker)
     */

    public void incrementFaithPoints(int amount){
        faithPoints += amount;

        //Faith points cannot be be more than 24
        if(faithPoints>24){
            faithPoints = 24;
        }
    }

    /**
     * Set report at index as attended
     * @param index should be 0,1,2 depending on the report the player attended
     */

    public void attendReport(int index){
        reportsAttended[index] = true;
    }

    public void discardReport(int index){
        reportsAttended[index] = false;
    }

    /**
     *
     * @param reportsAttended default should be 3
     * @param pp the devcard board
     */

    public PlayerBoard(int reportsAttended, Warehouse wh, Strongbox sb, ProductionPowers pp) {
        this.faithPoints = 0;
        this.reportsAttended = new Boolean[reportsAttended];
        this.warehouse = wh;
        this.strongbox = sb;
        this.productionPowers = pp;
        this.leadDiscount = new Resources();
        this.leadMarbles = new ArrayList<>();
    }

    /**
     *
     * @return current faith points player has (where the faith marker is)
     */

    public int getFaithPoints(){
        return this.faithPoints;
    }

    /**
     * Calculates victory points from faith track and reports attended.
     * @return total number of VP from faith
     */


    public int getFaithVP(){
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

        if(reportsAttended[0]!=null){
        if(reportsAttended[0]){
            faithVictoryPoints+= 2;
        }}
        if(reportsAttended[1]!=null){
        if(reportsAttended[1]){
            faithVictoryPoints+= 3;
        }}
        if(reportsAttended[2]!=null){
        if(reportsAttended[2]){
            faithVictoryPoints+= 4;
        }}

        //total victory points from the board

        return faithVictoryPoints;

    }

    /**
     * Calculates victory points from faith track, reports attended, resources and development cards owned.
     * @return sum of victory points the board has
     */

    public int getBoardVictoryPoints(){

        return getFaithVP() + (warehouse.getResourceAmountWarehouse()+ strongbox.getResourceAmountStrongbox())/5 + productionPowers.getOwnedCardsVictoryPoints();
    }

    /**
     *
     * @return all resources available from the board (warehouse + strongbox)
     */

    public Resources getResourcesAvailable(){
        Resources available = new Resources();
        available.add(warehouse.getResourcesAvailable());
        available.add(strongbox.getResourcesAvailable());

        System.out.println("getResources available tot amount: " + available.getTotalAmount());

        return available;
    }

    /**
     *
     * @return all the Development Cards The player has purchased
     */

    public ArrayList<DevCard> getOwnedDevCards() {
        return productionPowers.getOwnedDevCards();
    }

    /**
     * @return a deep copy of the current discount
     */
    public Resources getDiscount() {
        // Create a new Resources object identical to the one of this
        Resources temp = new Resources();
        temp.add(leadDiscount);

        return temp;
    }

    /**
     * Increase the discount by the Resources in the parameter
     * @param additionalDiscount to be added to the user discount
     */
    public void addDiscount(Resources additionalDiscount) {
        leadDiscount.add(additionalDiscount);
    }

    /**
     * @return a deep copy of the current player's marble replacements
     */
    public List<ResourceType> getLeadMarbles() {
        return new ArrayList<>(leadMarbles);
    }

    /**
     * Add one more replacement for the grey marble
     * @param additionalMarble
     */
    public void addMarble(ResourceType additionalMarble) {
        leadMarbles.add(additionalMarble);
    }

    public Boolean[] getReportsAttended() {
        return reportsAttended;
    }
}
