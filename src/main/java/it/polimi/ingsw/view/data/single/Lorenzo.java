package it.polimi.ingsw.view.data.single;

import it.polimi.ingsw.model.singleplayer.SoloActionTokens;
import it.polimi.ingsw.view.observer.single.LorenzoObserver;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Lorenzo {
    /* OBSERVER */
    private AtomicReference<LorenzoObserver> lorenzoObserver;
    private final AtomicInteger blackCross;
    private final AtomicReference<SoloActionTokens> lastToken;

    public Lorenzo() {
        this.blackCross = new AtomicInteger(0);
        this.lastToken = new AtomicReference<>(null);
        this.lorenzoObserver = new AtomicReference<>();
    }

    /**
     * Set the black cross position.
     * @param blackCross position of the black cross.
     */
    public void setBlackCross(int blackCross) {
        this.blackCross.set(blackCross);
        if(lorenzoObserver.get() != null) lorenzoObserver.get().onBlackCrossChange();
    }

    /**
     * Set the last token played by Lorenzo.
     * @param lastToken last token.
     */
    public void setLastToken(SoloActionTokens lastToken) {
        /*System.out.println("Lorenzo.setLastToken");*/
        this.lastToken.set(lastToken);
        if(lorenzoObserver.get() != null) lorenzoObserver.get().onLastTokenChange();
    }

    /**
     * Get the black cross position.
     * @return black cross position.
     */
    public int getBlackCross() {
        return blackCross.get();
    }

    /**
     * Get the last token played by Lorenzo.
     * @return last token.
     */
    public SoloActionTokens getLastToken() {
        return lastToken.get();
    }

    /**
     * Set the observer of Lorenzo data.
     * @param lorenzoObserver observer that will be notified whenever a change occurs.
     */
    public void setObserver(LorenzoObserver lorenzoObserver) {
        this.lorenzoObserver.set(lorenzoObserver);
        lorenzoObserver.onLastTokenChange();
        lorenzoObserver.onBlackCrossChange();
    }
}


