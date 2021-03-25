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

    public PlayerBoard() {
        this.faithPoints = 0;
        this.reportsAttended = new boolean[3];
        this.warehouse = new Warehouse();
        this.strongbox = new Strongbox();
        this.productionPowers = new ProductionPowers();
    }
}
