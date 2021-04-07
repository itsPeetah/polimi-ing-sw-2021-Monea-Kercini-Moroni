package it.polimi.ingsw.model.general;

public class Production {
    private Resources input, output;

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
}
