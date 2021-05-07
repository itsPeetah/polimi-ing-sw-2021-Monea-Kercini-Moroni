package it.polimi.ingsw.network.common;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.messages.MessagePacket;
import it.polimi.ingsw.controller.model.updates.UpdatePacket;

public class NetworkPacket {

    private static final Gson gson = new Gson();

    private NetworkPacketType packetType;
    private String payload;

    public NetworkPacket(){}

    public NetworkPacket(NetworkPacketType packetType, String payload){
        this.packetType = packetType;
        this.payload = payload;
    }

    public NetworkPacketType getPacketType() {
        return packetType;
    }

    public String getPayload() {
        return payload;
    }

    public static NetworkPacket fromString(String text){
        return gson.fromJson(text, NetworkPacket.class);
    }

    public String toJson(){
        return gson.toJson(this, NetworkPacket.class);
    }

    public static String toJsonString(NetworkPacket packet){
        return packet.toJson();
    }

    public static NetworkPacket buildMessagePacket(MessagePacket messagePacket) {
        String payload = gson.toJson(messagePacket, MessagePacket.class);
        return new NetworkPacket(NetworkPacketType.MESSAGE, payload);
    }

    public static NetworkPacket buildUpdatePacket(UpdatePacket updatePacket) {
        String payload = gson.toJson(updatePacket, UpdatePacket.class);
        return new NetworkPacket(NetworkPacketType.UPDATE, payload);
    }

    public static NetworkPacket buildActionPacket(ActionPacket actionPacket) {
        String payload = gson.toJson(actionPacket, ActionPacket.class);
        return new NetworkPacket(NetworkPacketType.ACTION, payload);
    }
}
