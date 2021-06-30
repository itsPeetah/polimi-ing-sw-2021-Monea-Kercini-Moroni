package it.polimi.ingsw.view.data;

import it.polimi.ingsw.view.data.common.DevCardMarket;
import it.polimi.ingsw.view.data.common.MarketTray;
import it.polimi.ingsw.view.data.single.Lorenzo;
import it.polimi.ingsw.view.observer.GameDataObserver;

import java.util.concurrent.atomic.AtomicReference;

public class CommonData {

    private final DevCardMarket dcm;
    private final MarketTray mt;
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

    /**
     * Get the current player who is playing.
     * @return player who is currently playing.
     */
    public String getCurrentPlayer() {
        return currentPlayer.get();
    }

    /**
     * Set the current player.
     * @param currentPlayer player whose turn has just started.
     */
    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer.set(currentPlayer);
        if(gameDataObserver.get() != null) gameDataObserver.get().onCurrentPlayerChange();
    }

    /**
     * Get the dev card market.
     * @return dev card market.
     */
    public DevCardMarket getDevCardMarket() {
        return dcm;
    }

    /**
     * Get the market tray.
     * @return market tray.
     */
    public MarketTray getMarketTray() {
        return mt;
    }

    /**
     * Get Lorenzo.
     * @return Lorenzo.
     */
    public Lorenzo getLorenzo() {
        return lorenzo;
    }

    /**
     * Set the observer of the common data.
     * @param gameDataObserver observer that will be notified whenever a change occurs.
     */
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
