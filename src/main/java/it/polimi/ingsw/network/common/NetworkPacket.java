package it.polimi.ingsw.network.common;

import com.google.gson.Gson;

public class NetworkPacket {

    private static final Gson gson = new Gson();

    private NetworkPacketType type;
    private String payload;

    public NetworkPacket(){}

    public NetworkPacket(NetworkPacketType type, String payload){
        this.type = type;
        this.payload = payload;
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

}
