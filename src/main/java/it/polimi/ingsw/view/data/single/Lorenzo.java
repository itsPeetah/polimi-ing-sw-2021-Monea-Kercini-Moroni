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
        this.lastToken = lastToken;
        if(lorenzoObserver != null) lorenzoObserver.onLastTokenChange();
    }

    public void setObserver(LorenzoObserver lorenzoObserver) {
        this.lorenzoObserver = lorenzoObserver;
        lorenzoObserver.onLastTokenChange();
        lorenzoObserver.onBlackCrossChange();
    }
}
