package it.polimi.ingsw.application.cli.components;

import it.polimi.ingsw.application.cli.util.ANSIColor;

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

    public void execute(String command, String[] arguments){
        // Override
    }

    public void help(){
        println("The help view is not implemented for this scene.");
    }

    public void println(String message){
        System.out.println(message);
    }

    public void print(String message){
        System.out.print(message);
    }

    public void print(char character){
        System.out.print(character);
    }

    public void error(String errorMessage){
        println(ANSIColor.RED + errorMessage + ANSIColor.RESET);
    }

    public final static void clearConsole()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
