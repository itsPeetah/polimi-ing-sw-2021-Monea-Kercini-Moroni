package it.polimi.ingsw.network.common.social;

/**
 * Class representing social NPs (whispers/chat messages).
 */
public class SocialPacket {
    private SocialPacketType type;
    private String to;
    private String from;
    private String body;

    public SocialPacket() {}

    /**
     * Chat message constructor.
     */
    public SocialPacket(String from, String body) {
        this.from = from;
        this.body = body;
        this.type = SocialPacketType.CHAT;
        this.to = null;
    }

    /**
     * Whisper message constructor.
     */
    public SocialPacket(String to, String from, String body) {
        this.to = to;
        this.from = from;
        this.body = body;
        this.type = SocialPacketType.WHISPER;
    }

    public SocialPacketType getType() {
        return type;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getBody() {
        return body;
    }
}
