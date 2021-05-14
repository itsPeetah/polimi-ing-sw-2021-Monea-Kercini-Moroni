package it.polimi.ingsw.view.data;

import it.polimi.ingsw.view.data.player.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class PlayerData {

    private String nickname;
    private AtomicInteger VP;

    private DevCards devCards;
    private FaithTrack faithTrack;
    private PlayerLeaders playerLeaders;
    private Warehouse warehouse;
    private Strongbox strongbox;

    public synchronized DevCards getDevCards() {
        return devCards;
    }

    public synchronized FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public synchronized PlayerLeaders getPlayerLeaders() {
        return playerLeaders;
    }

    public synchronized Warehouse getWarehouse() {
        return warehouse;
    }

    public synchronized Strongbox getStrongbox() {
        return strongbox;
    }

    public PlayerData() {
        devCards = new DevCards();
        playerLeaders = new PlayerLeaders();
        faithTrack = new FaithTrack();
        warehouse = new Warehouse();
        strongbox = new Strongbox();
    }

    public synchronized String getNickname() {
        return nickname;
    }

    public synchronized void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setVP(int VP) {
        int oldVP = this.VP.get();
        while(!this.VP.compareAndSet(oldVP, VP)) setVP(VP);
    }
}