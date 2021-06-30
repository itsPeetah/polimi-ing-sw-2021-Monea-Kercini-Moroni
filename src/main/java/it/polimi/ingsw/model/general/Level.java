package it.polimi.ingsw.model.general;

import java.util.Arrays;
import java.util.List;

public enum Level {
    LOW,
    MEDIUM,
    HIGH;

    // Auxiliary attribute
    private static final List<Level> values = Arrays.asList(Level.values());

    /**
     * Return the level preceding this. If this is the lowest level, this will be returned.
     * @return the level preceding this.
     */
    public Level getPrevious() {
        return (this.toInteger() == 0) ? this : values.get(this.toInteger() - 1);
    }

    /**
     * Return the level after this. If this is the highest level, this will be returned.
     * @return the level after this.
     */
    public Level getNext() {
        return (values.indexOf(this) == values.size() - 1) ? this : values.get(values.indexOf(this) + 1);
    }

    /**
     * Return the int value associated with the enum value.
     * @return index of this.
     */
    public int toInteger() {
        return values.indexOf(this);
    }
}
