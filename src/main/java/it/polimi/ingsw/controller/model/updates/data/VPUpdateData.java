package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;

public class VPUpdateData implements UpdateData {
    int vp[];
    String players[];



    public VPUpdateData(int vp[], String players[]) {
        this.vp = vp;
        this.players = players;
    }

    public int[] getVP() {
        return vp;
    }

    public String[] getPlayers() {
        return players;
    }
}
