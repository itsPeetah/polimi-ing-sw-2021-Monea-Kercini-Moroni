package it.polimi.ingsw.controller.updates;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.updates.data.*;

public enum Update {
    EMPTY(EmptyUpdateData.class);

    private final Class<?> classOfData;

    Update(Class<?> classOfData) {
        this.classOfData = classOfData;
    }
    
    /**
     * Parse a UpdateData object to a json string representing the correct subclass of UpdateData.
     * @return json formatted string representing an UpdateData object.
     */
    public String parseData(UpdateData data) {
        Gson gson = new Gson();
        return gson.toJson(data, classOfData);
    }
}
