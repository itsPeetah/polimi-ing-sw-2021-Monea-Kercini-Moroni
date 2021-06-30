package it.polimi.ingsw.view.data.player;

import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.view.observer.player.StrongboxObserver;

public class Strongbox {
    /* OBSERVER */
    private StrongboxObserver strongboxObserver;

    private Resources content;

    public Strongbox() {
        this.content = new Resources();
    }

    /**
     * Set the content of the strongbox.
     * @param content resources in the strongbox.
     */
    public synchronized void setContent(Resources content) {
        this.content = content;
        if(strongboxObserver != null) strongboxObserver.onStrongboxChange();
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
        this.strongboxObserver = strongboxObserver;
        strongboxObserver.onStrongboxChange();
    }

}
