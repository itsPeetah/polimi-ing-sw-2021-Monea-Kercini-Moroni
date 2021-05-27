package it.polimi.ingsw.application.cli.commands;

public class CLICommand {

    private final int argc;
    private final ICLICommandArgumentEvaluation evaluation;
    private final ICLICommandMethod method;

    public CLICommand(int argc, ICLICommandArgumentEvaluation evaluation, ICLICommandMethod method){
        this.argc = argc;
        this.evaluation = evaluation;
        this.method = method;
    }
}
