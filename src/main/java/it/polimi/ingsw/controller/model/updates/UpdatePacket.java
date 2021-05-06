package it.polimi.ingsw.controller.model.updates;

public class UpdatePacket {
    Update type;
    UpdateData data;

    /**
     * Constructor for GSON
     */
    public UpdatePacket() {}

    public UpdatePacket(Update type, UpdateData data) {
        this.type = type;
        this.data = data;
    }

    /**
     * Get the update type.
     */
    public Update getUpdate() {
        return type;
    }

    /**
     * Get json formatted string of data.
     */
    public UpdateData getData() {
        return data;
    }
}
