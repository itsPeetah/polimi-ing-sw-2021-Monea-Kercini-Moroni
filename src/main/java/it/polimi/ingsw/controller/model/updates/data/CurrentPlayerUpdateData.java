package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;

public class CurrentPlayerUpdateData implements UpdateData {
    private final String currentPlayer;

    /**
     * Create a new current player update.
     * @param currentPlayer new current player's nickname.
     */
    public CurrentPlayerUpdateData(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Get the current player that is playing.
     * @return nickname of the current player.
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }
}
