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

    public static boolean isGameRelated(NetworkPacket np){
        NetworkPacketType npt = np.getPacketType();
        return npt == MESSAGE || npt == ACTION || npt == UPDATE;
    }
}
