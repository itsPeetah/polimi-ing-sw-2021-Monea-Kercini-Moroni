package it.polimi.ingsw.view.data.single;

import it.polimi.ingsw.model.singleplayer.SoloActionTokens;

import java.util.Observable;

public class Lorenzo extends Observable {
    private int blackCross;
    private SoloActionTokens lastToken;

    public void setBlackCross(int blackCross) {
        this.blackCross = blackCross;
        setChanged();
        notifyObservers(blackCross);
    }

    public void setLastToken(SoloActionTokens lastToken) {
        this.lastToken = lastToken;
        setChanged();
        notifyObservers(lastToken);
    }
}
