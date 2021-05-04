package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.model.cards.LeadCard;

public class Warehouse{

    private Resources[] content; // 3 resources ( 1 single top, 2 same type, 3 same type bottom) with floor reference 2/1/0

    private Resources leaderExtraAvailable; // 2+2 extra (2 same type for each leader card bonus that might have been played) with floor reference 3
    private Resources leaderExtraUsed;

    /**
     * Deposit the resources at the floor gotten as integer input
     * @param resources
     * @param floor
     */

    public void deposit(Resources resources, int floor){
        if (floor<3){
            content[floor].add(resources);
        }else
            leaderExtraUsed.add(resources); //this is else in case we change our mind to make leader resources in 2 floors in GUI
    }

    /**
     * Tries to remove all resources taken as input from the warehouse, searching floor by floor
     * If it cannot remove all, it returns the resources effectively removed
     * @param res
     * @return Resources removed from warehouse
     */

    public Resources withdraw(Resources res) {

        Resources withdrawed = new Resources();

        for (ResourceType tipo : ResourceType.values()) {
        //Switch to the next resource type to search for

            int curr_res = res.getAmountOf(tipo); // The quantity of the resource type to search for

            for (int i = 0; i < 3; i++) { //Search all three main floors

                if (content[i].getAmountOf(tipo) >= curr_res) { //If the resource is available more than needed

                    try { content[i].remove(tipo, curr_res); //remove the resources from warehouse
                    } catch (Exception e) {
                        e.printStackTrace(); }

                    withdrawed.add(tipo, curr_res); // add to withdrawed
                    curr_res = 0; // No need to take anymore of that type of resource


                } else { //We don't have enough resources to get all we're looking for, we will search in the next floor (and finally at the leader extra)
                    curr_res -= content[i].getAmountOf(tipo);
                    withdrawed.add(tipo, content[i].getAmountOf(tipo));

                    try { content[i].remove(tipo, content[i].getAmountOf(tipo)); //Nullify that resource from the warehouse as we have used all of it available
                    } catch (Exception e) {
                        e.printStackTrace(); }
                }
            }

            //Same procedure for the extra resources from leader
            if (leaderExtraUsed.getAmountOf(tipo) >= curr_res) {

                try { leaderExtraUsed.remove(tipo, curr_res);
                } catch (Exception e) {
                    e.printStackTrace(); }

                    withdrawed.add(tipo, curr_res);
                    curr_res = 0;
                } else {
                    curr_res -= leaderExtraUsed.getAmountOf(tipo);
                    withdrawed.add(tipo, leaderExtraUsed.getAmountOf(tipo));

                    try {  leaderExtraUsed.remove(tipo, leaderExtraUsed.getAmountOf(tipo));
                    } catch (Exception e) {
                        e.printStackTrace(); }
            }


        }
        return withdrawed;
    }

    /**
     * Add the leader bonus as extra warehouse space
     * @param leader the leader card who gives us the bonus
     */

    public void expandWithLeader(LeadCard leader){
        leaderExtraAvailable.add(leader.getAbility().getExtraWarehouseSpace());
    }

    /**
     *
     * @return the total number of resources we have
     */

    public int getResourceAmountWarehouse(){
        return (content[0].getTotalAmount()+ content[1].getTotalAmount()+ content[2].getTotalAmount() + leaderExtraUsed.getTotalAmount());
    }

    /**
     *
     * @return all the resources you can find in the warehouse & leader bonuses
     */

    public Resources getResourcesAvailable(){
        Resources available = new Resources();
        for (int i = 0; i< content.length; i++){
            if (content[i] != null){
                available.add(content[i]);
            }
        }
        available.add(leaderExtraUsed);
        return available;
    }

    public Warehouse() {
        this.content = new Resources[3];
        content[0] = new Resources();
        content[1] = new Resources();
        content[2] = new Resources();
        this.leaderExtraAvailable = new Resources();
        this.leaderExtraUsed = new Resources();
    }

    public void copy(Warehouse w){
        this.content = w.content;
        this.leaderExtraAvailable = w.leaderExtraAvailable;
        this.leaderExtraUsed = w.leaderExtraUsed;
    }


}