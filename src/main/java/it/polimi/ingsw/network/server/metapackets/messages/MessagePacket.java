package it.polimi.ingsw.network.server.metapackets.messages;

public class MessagePacket {
    String player;
    String message;

    /**
     * Constructor for GSON
     */
    public MessagePacket() {}

    public MessagePacket(String player, Message message) {
        this.player = player;
        this.message = message.toString();
    }

    /**
     * Get the player to whom the packet is addressed.
     */
    public String getPlayer() {
        return player;
    }

    /**
     * Get the content of the message.
     */
    public String getMessage() {
        return message;
    }
}
