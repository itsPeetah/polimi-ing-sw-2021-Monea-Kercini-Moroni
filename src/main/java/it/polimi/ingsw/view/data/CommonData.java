package it.polimi.ingsw.view.data;

import it.polimi.ingsw.view.data.common.DevCardMarket;
import it.polimi.ingsw.view.data.common.MarketTray;
import it.polimi.ingsw.view.data.single.Lorenzo;

public class CommonData {

    private DevCardMarket dcm;
    private MarketTray mt;

    //including single player data
    private Lorenzo lorenzo;

    private String currentPlayer;

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public DevCardMarket getDevCardMarket() {
        return dcm;
    }

    public MarketTray getMarketTray() {
        return mt;
    }

    public Lorenzo getLorenzo() {
        return lorenzo;
    }

    //for quickly constructing empty classes


    public CommonData() {
        dcm = new DevCardMarket();
        mt = new MarketTray();
        lorenzo = new Lorenzo();
    }

    @Override
    public String toString() {
        return "CommonData{" +
                "dcm=" + dcm.toString() +
                ", mt=" + mt.toString() +
                ", lorenzo=" + lorenzo.toString() +
                '}';
    }
}
