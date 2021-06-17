package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;

public class CurrentPlayerUpdateData implements UpdateData {
    String currentPlayer;

    public CurrentPlayerUpdateData(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }
}
