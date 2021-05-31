package it.polimi.ingsw.network.common.sysmsg;

public enum SocialMessage implements SystemMessage {
    CHAT("CHAT"),
    WHISPER("WHISPER");

    private final String messageCode;

    SocialMessage(String code){
        messageCode = code;
    }

    public String getCode() {
        return messageCode;
    }

    public String addBody(String body){
        return messageCode + " " + body;
    }

    public boolean check(String message){
        return messageCode.equals(message);
    }

    public boolean check(String arguments, String message){
        return this.addBody(arguments).equals(message);
    }
}
