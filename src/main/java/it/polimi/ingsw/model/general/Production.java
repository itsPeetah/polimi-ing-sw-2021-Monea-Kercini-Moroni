package it.polimi.ingsw.model.general;

public class Production {
    private final Resources input;
    private final Resources output;

    public Resources getInput() {
        return input;
    }

    public Resources getOutput() {
        return output;
    }

    public Production(Resources input, Resources output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public String toString() {
        return  "IN: " + input.toString() +
                ", OUT: " + output.toString();
    }
}
