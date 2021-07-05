package it.polimi.ingsw.view.data.player;

import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.view.observer.player.StrongboxObserver;

import java.util.concurrent.atomic.AtomicReference;

public class Strongbox {
    /* OBSERVER */
    private final AtomicReference<StrongboxObserver> strongboxObserver;

    private Resources content;

    public Strongbox() {
        this.content = new Resources();
        this.strongboxObserver = new AtomicReference<>();
    }

    /**
     * Set the content of the strongbox.
     * @param content resources in the strongbox.
     */
    public synchronized void setContent(Resources content) {
        this.content = content;
        if(strongboxObserver.get() != null) strongboxObserver.get().onStrongboxChange();
    }

    /**
     * Get the content of the strongbox.
     * @return resources in the strongbox.
     */
    public Resources getContent() {
        return content;
    }

    /**
     * Set the observer of the strongbox of the player.
     * @param strongboxObserver observer that will be notified whenever a change occurs.
     */
    public void setObserver(StrongboxObserver strongboxObserver) {
        this.strongboxObserver.set(strongboxObserver);
        strongboxObserver.onStrongboxChange();
    }

}
