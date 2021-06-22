package it.polimi.ingsw.network.common;

/**
 * Network packet labels.
 */
public enum NetworkPacketType {
    SYSTEM,
    DEBUG,
    MESSAGE,
    ACTION,
    UPDATE,
    SOCIAL;

    /**
     * Check if a NP is either a MESSAGE, ACTION or UPDATE packet
     */
    public static boolean isGameRelated(NetworkPacket np){
        NetworkPacketType npt = np.getPacketType();
        return npt == MESSAGE || npt == ACTION || npt == UPDATE;
    }
}
