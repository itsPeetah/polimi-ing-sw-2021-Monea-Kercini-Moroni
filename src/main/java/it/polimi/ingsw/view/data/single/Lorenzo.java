package it.polimi.ingsw.view.data.single;

import it.polimi.ingsw.model.singleplayer.SoloActionTokens;
import it.polimi.ingsw.view.observer.single.LorenzoObserver;

public class Lorenzo {
    /* OBSERVER */
    LorenzoObserver lorenzoObserver;

    private int blackCross;
    private SoloActionTokens lastToken;

    public synchronized void setBlackCross(int blackCross) {
        this.blackCross = blackCross;
        if(lorenzoObserver != null) lorenzoObserver.onBlackCrossChange();
    }

    public synchronized void setLastToken(SoloActionTokens lastToken) {
        System.out.println("Lorenzo.setLastToken");
        this.lastToken = lastToken;
        if(lorenzoObserver != null) lorenzoObserver.onLastTokenChange();
    }

    public void setObserver(LorenzoObserver lorenzoObserver) {
        this.lorenzoObserver = lorenzoObserver;
        lorenzoObserver.onLastTokenChange();
        lorenzoObserver.onBlackCrossChange();
    }

    public int getBlackCross() {
        return blackCross;
    }

    public SoloActionTokens getLastToken() {
        return lastToken;
    }

    public Lorenzo() {
        this.blackCross = 0;
        this.lastToken = null;
    }
}


