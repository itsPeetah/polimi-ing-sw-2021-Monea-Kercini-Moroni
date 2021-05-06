package it.polimi.ingsw.controller.model.messages;

public class MessagePacket {
    String player;
    Message message;

    /**
     * Constructor for GSON
     */
    public MessagePacket() {}

    public MessagePacket(String player, Message message) {
        this.player = player;
        this.message = message;
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
    public Message getMessage() {
        return message;
    }
}
