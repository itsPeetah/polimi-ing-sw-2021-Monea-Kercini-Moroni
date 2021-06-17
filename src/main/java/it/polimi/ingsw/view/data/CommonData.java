package it.polimi.ingsw.view.data;

import it.polimi.ingsw.view.data.common.DevCardMarket;
import it.polimi.ingsw.view.data.common.MarketTray;
import it.polimi.ingsw.view.data.single.Lorenzo;
import it.polimi.ingsw.view.observer.GameDataObserver;

import java.util.concurrent.atomic.AtomicReference;

public class CommonData {

    private final DevCardMarket dcm;
    private final MarketTray mt;

    //including single player data
    private final Lorenzo lorenzo;

    private final AtomicReference<String> currentPlayer;

    private final AtomicReference<GameDataObserver> gameDataObserver;


    public CommonData() {
        dcm = new DevCardMarket();
        mt = new MarketTray();
        lorenzo = new Lorenzo();
        currentPlayer = new AtomicReference<>();
        gameDataObserver = new AtomicReference<>(null);
    }

    public String getCurrentPlayer() {
        return currentPlayer.get();
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer.set(currentPlayer);
        if(gameDataObserver.get() != null) gameDataObserver.get().onCurrentPlayerChange();
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

    public void setObserver(GameDataObserver gameDataObserver) {
        this.gameDataObserver.set(gameDataObserver);
        gameDataObserver.onCurrentPlayerChange();
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
