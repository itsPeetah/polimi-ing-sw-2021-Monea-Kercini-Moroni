package it.polimi.ingsw.model.PlayerBoard;

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

    public void incrementFaithPoints(int amount){
        faithPoints += amount;
    }

    public void attendReport(int index){ //0, 1, 2
        reportsAttended[index] = true;
    }

    public PlayerBoard(int reportsAttended) { //for our game rules parameter must be 3
        this.faithPoints = 0;
        this.reportsAttended = new boolean[reportsAttended];
        this.warehouse = new Warehouse();
        this.strongbox = new Strongbox();
        this.productionPowers = new ProductionPowers(3);
    }

    public int getBoardVictoryPoints(){
        return ((warehouse.getResourceAmountWarehouse()+ strongbox.getResourceAmountStrongbox())/5 + productionPowers.getOwnedCardsVictoryPoints());
    }
}
