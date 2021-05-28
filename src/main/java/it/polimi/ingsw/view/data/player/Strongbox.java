package it.polimi.ingsw.view.data.player;

import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.view.observer.player.StrongboxObserver;

public class Strongbox {
    /* OBSERVER */
    StrongboxObserver strongboxObserver;

    private Resources content;

    public synchronized void setContent(Resources content) {
        this.content = content;
        if(strongboxObserver != null) strongboxObserver.onStrongboxChange();
    }

    public Resources getContent() {
        return content;
    }

    public Strongbox() {
        this.content = new Resources();
    }

    public void setObserver(StrongboxObserver strongboxObserver) {
        this.strongboxObserver = strongboxObserver;
        strongboxObserver.onStrongboxChange();
    }

}
