package it.polimi.ingsw.application.cli.components.scenes;

import it.polimi.ingsw.application.cli.MaestriRinasimentoCLI;
import it.polimi.ingsw.application.cli.components.CLIScene;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class CLIStartup extends CLIScene {

    public CLIStartup(String title) {
        super(title);
    }

    @Override
    public void show() {
        stdout("Maestri del Rinascimento");
        stdout("Loading...");
    }

    @Override
    public void getInput() {
        Scanner in = new Scanner(System.in);
        in.nextLine();
        MaestriRinasimentoCLI.appRunning = false;
    }
}
