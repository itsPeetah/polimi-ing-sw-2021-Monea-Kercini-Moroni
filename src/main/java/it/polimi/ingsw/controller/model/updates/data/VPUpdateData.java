package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;

public class VPUpdateData implements UpdateData {
    int vp[];

    public VPUpdateData(int vp[]) {
        this.vp = vp;
    }

    public int[] getVP() {
        return vp;
    }
}
