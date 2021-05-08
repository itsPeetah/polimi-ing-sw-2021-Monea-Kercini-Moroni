package it.polimi.ingsw.controller.model.updates.data;

import it.polimi.ingsw.controller.model.updates.UpdateData;

public class FaithUpdateData implements UpdateData {
    int fp;

    public int getFaithPoints() {
        return fp;
    }

    public FaithUpdateData(int fp) {
        this.fp = fp;
    }
}
