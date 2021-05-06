package it.polimi.ingsw.controller.model.messages;

public enum Message {
    ERROR("ERROR"),
    START_TURN("It's your turn!"),
    NOT_ENOUGH_RESOURCES("You don't have enough resources."),
    ILLEGAL_CARD_PLACE("You can't put the card there."),
    REQUIREMENTS_NOT_MET("You don't meet the requirements to play this card."),
    ALREADY_USED_PRIMARY_ACTION("You have already used your primary action.");

    private final String message;

    Message(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return message;
    }
}
