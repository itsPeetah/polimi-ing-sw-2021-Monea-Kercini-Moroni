package it.polimi.ingsw.network.common;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.messages.MessagePacket;
import it.polimi.ingsw.controller.model.updates.UpdatePacket;

/**
 * Generic packet to be sent across the network
 */
public class NetworkPacket {

    private static final Gson gson = new Gson();

    private NetworkPacketType packetType;
    private String payload;

    /**
     * Empty class constructor used for the JSON deserialization.
     */
    public NetworkPacket(){}

    /**
     * Class constructor.
     * @param packetType Type of the packet.
     * @param payload Payload of the packet.
     */
    public NetworkPacket(NetworkPacketType packetType, String payload){
        this.packetType = packetType;
        this.payload = payload;
    }

    /**
     * Packet Type getter.
     */
    public NetworkPacketType getPacketType() {
        return packetType;
    }

    /**
     * Payload getter.
     */
    public String getPayload() {
        return payload;
    }

    /**
     * Build a network packet from it's json representation.
     * @param text the json text representing the packet.
     */
    public static NetworkPacket fromString(String text){
        return gson.fromJson(text, NetworkPacket.class);
    }

    /**
     * Convert the packet to json.
     */
    public String toJson(){
        return gson.toJson(this, NetworkPacket.class);
    }

    /**
     * Convert a packet to json.
     */
    public static String toJsonString(NetworkPacket packet){
        return packet.toJson();
    }

    /**
     * Build a system message network packet.
     * @param message Content of the message.
     */
    public static NetworkPacket buildSystemMessagePacket(String message){
        return new NetworkPacket(NetworkPacketType.SYSTEM, message);
    }

    /**
     * Build a debug message network packet.
     * @param message Content of the message.
     */
    public static NetworkPacket buildDebugPacket(String message){
        return new NetworkPacket(NetworkPacketType.DEBUG, message);
    }

    /**
     * Build a message network packet.
     * @param messagePacket Packet to be wrapped in the network packet.
     */
    public static NetworkPacket buildMessagePacket(MessagePacket messagePacket) {
        String payload = gson.toJson(messagePacket, MessagePacket.class);
        return new NetworkPacket(NetworkPacketType.MESSAGE, payload);
    }

    /**
     * Build an update network packet.
     * @param updatePacket Packet to be wrapped in the network packet.
     */
    public static NetworkPacket buildUpdatePacket(UpdatePacket updatePacket) {
        String payload = gson.toJson(updatePacket, UpdatePacket.class);
        return new NetworkPacket(NetworkPacketType.UPDATE, payload);
    }

    /**
     * Build an action network packet.
     * @param actionPacket Packet to be wrapped in the network packet.
     */
    public static NetworkPacket buildActionPacket(ActionPacket actionPacket) {
        String payload = gson.toJson(actionPacket, ActionPacket.class);
        return new NetworkPacket(NetworkPacketType.ACTION, payload);
    }
}
