package it.polimi.ingsw.application.cli.threads;

import it.polimi.ingsw.application.cli.components.CLIScenes;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;

import java.util.Scanner;

public class CLIInputReader implements Runnable {

    private Scanner in;

    public CLIInputReader() {
        in = new Scanner(System.in);
    }

    @Override
    public void run() {

        String[] input;
        String command;
        String[] args;
        while (true) {
            input = in.nextLine().split(" ", 2);
            command = input[0];
            args = input.length < 2 ? null : input[1].split(" ");

            if (command.equals("q"))
                GameApplication.getInstance().setApplicationState(GameApplicationState.STOPPED);
            else
                CLIScenes.getCurrent().execute(command, args);
        }
    }
}
