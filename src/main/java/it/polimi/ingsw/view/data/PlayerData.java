package it.polimi.ingsw.view.data;

import it.polimi.ingsw.view.data.player.*;

public class PlayerData {

    private String nickname;
    private int VP;

    private DevCards devCards;
    private FaithTrack faithTrack;
    private PlayerLeaders playerLeaders;
    private Warehouse warehouse;
    private Strongbox strongbox;

    public DevCards getDevCards() {
        return devCards;
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public PlayerLeaders getPlayerLeaders() {
        return playerLeaders;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Strongbox getStrongbox() {
        return strongbox;
    }

    public PlayerData() {
        devCards = new DevCards();
        playerLeaders = new PlayerLeaders();
        faithTrack = new FaithTrack();
        warehouse = new Warehouse();
        strongbox = new Strongbox();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setVP(int VP) {
        this.VP = VP;
    }
}
