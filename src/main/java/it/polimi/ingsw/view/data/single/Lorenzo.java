package it.polimi.ingsw.view.data.single;

import it.polimi.ingsw.model.singleplayer.SoloActionTokens;
import it.polimi.ingsw.view.observer.single.LorenzoObserver;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Lorenzo {
    /* OBSERVER */
    private LorenzoObserver lorenzoObserver;

    private final AtomicInteger blackCross;
    private final AtomicReference<SoloActionTokens> lastToken;

    public void setBlackCross(int blackCross) {
        this.blackCross.set(blackCross);
        if(lorenzoObserver != null) lorenzoObserver.onBlackCrossChange();
    }

    public void setLastToken(SoloActionTokens lastToken) {
        /*System.out.println("Lorenzo.setLastToken");*/
        this.lastToken.set(lastToken);
        if(lorenzoObserver != null) lorenzoObserver.onLastTokenChange();
    }

    public void setObserver(LorenzoObserver lorenzoObserver) {
        this.lorenzoObserver = lorenzoObserver;
        lorenzoObserver.onLastTokenChange();
        lorenzoObserver.onBlackCrossChange();
    }

    public int getBlackCross() {
        return blackCross.get();
    }

    public SoloActionTokens getLastToken() {
        return lastToken.get();
    }

    public Lorenzo() {
        this.blackCross = new AtomicInteger(0);
        this.lastToken = new AtomicReference<>(null);
    }
}


