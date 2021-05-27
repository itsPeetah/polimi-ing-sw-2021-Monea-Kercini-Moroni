package it.polimi.ingsw.application.cli.commands;

public interface ICLICommandArgumentEvaluation {
    boolean evaluate(String[] args, int expectedArgc);
}
