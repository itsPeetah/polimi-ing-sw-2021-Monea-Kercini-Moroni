package it.polimi.ingsw.view.data;

import it.polimi.ingsw.view.data.player.LeadersToChooseFrom;
import it.polimi.ingsw.view.data.player.*;
import it.polimi.ingsw.view.observer.player.VPObserver;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class PlayerData {

    private String nickname;
    private final AtomicInteger VP = new AtomicInteger(0);

    private final DevCards devCards;
    private final FaithTrack faithTrack;
    private final PlayerLeaders playerLeaders;
    private final Warehouse warehouse;
    private final Strongbox strongbox;
    private final LeadersToChooseFrom leadersToChooseFrom;

    private AtomicReference<VPObserver> vpObserver;

    public PlayerData() {
        devCards = new DevCards();
        playerLeaders = new PlayerLeaders();
        faithTrack = new FaithTrack();
        warehouse = new Warehouse();
        strongbox = new Strongbox();
        leadersToChooseFrom = new LeadersToChooseFrom();
        vpObserver = new AtomicReference<>();
    }

    /**
     * Get the leaders to choose from.
     * @return leaders the player must choose from.
     */
    public LeadersToChooseFrom getLeadersToChooseFrom() {
        return leadersToChooseFrom;
    }

    /**
     * Get the dev cards of the user.
     * @return dev cards of the user.
     */
    public DevCards getDevCards() {
        return devCards;
    }

    /**
     * Get the faith track of the user.
     * @return faith track of the user.
     */
    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    /**
     * Get the leaders of the player.
     * @return leaders of the player.
     */
    public PlayerLeaders getPlayerLeaders() {
        return playerLeaders;
    }

    /**
     * Get the warehouse of the player.
     * @return warehouse of the player.
     */
    public Warehouse getWarehouse() {
        return warehouse;
    }

    /**
     * Get the strongbox of the player.
     * @return strongbox of the player.
     */
    public Strongbox getStrongbox() {
        return strongbox;
    }

    /**
     * Get the nickname of the player.
     * @return nickname of the player.
     */
    public synchronized String getNickname() {
        return nickname;
    }

    /**
     * Set the nickname of the player.
     * @param nickname nickname of the player
     */
    public synchronized void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Set the VPs of the player.
     * @param VP VPs of the player.
     */
    public void setVP(int VP) {
        int oldVP = this.VP.get();
        while(!this.VP.compareAndSet(oldVP, VP)) setVP(VP);
        if(vpObserver.get() != null) vpObserver.get().onVPChange();
    }

    /**
     * Get the VPs of the player.
     * @return VPs of the player.
     */
    public int getVP() {
        return VP.get();
    }

    /**
     * Set the observer of the player data.
     * @param vpObserver observer that will be notified whenever a change occurs.
     */
    public void setObserver(VPObserver vpObserver) {
        this.vpObserver.set(vpObserver);
        vpObserver.onVPChange();
    }
}
