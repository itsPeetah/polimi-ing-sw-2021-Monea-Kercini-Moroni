package it.polimi.ingsw.network.common.sysmsg;

/**
 * System message enum method interface.
 */
public interface SystemMessage {

    /**
     * Message code getter.
     */
    public String getCode();

    /**
     * Append arguments to the message code.
     * @param body The arguments for the message.
     * @return The full message.
     */
    public String addBody(String body);

    /**
     * Compare message code with string.
     */
    public boolean check(String message);

    /**
     * Compare message with string.
     * @param arguments Expected arguments.
     * @param message Message to compare.
     */
    public boolean check(String arguments, String message);
}
