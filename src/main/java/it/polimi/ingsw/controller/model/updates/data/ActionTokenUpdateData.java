package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;
import it.polimi.ingsw.model.singleplayer.SoloActionTokens;

/**
 * Used to save the last action played by Lorenzo in a single player game
 */

public class ActionTokenUpdateData implements UpdateData {
    SoloActionTokens ActionToken;
    int blackCross;

    public SoloActionTokens getActionToken() {
        return ActionToken;
    }

    public ActionTokenUpdateData(SoloActionTokens actionToken, int blackCross) {
        ActionToken = actionToken;
        this.blackCross = blackCross;
    }

    public int getBlackCross() {
        return blackCross;
    }
}
