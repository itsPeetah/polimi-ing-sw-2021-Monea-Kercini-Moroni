package it.polimi.ingsw.application.cli.components;

import java.util.Scanner;

public class CLIScene {

    protected final Scanner input;

    public CLIScene(){
        this.input = new Scanner(System.in);
    }

    public void update(){
        // Override...
    }

    public void show(){
        // Override...
    }

    public void getInput(){
        // Override...
    }

    protected void help(){
        // Override
    }

    public void print(String message){
        System.out.println(message);
    }

    public final static void clearConsole()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
