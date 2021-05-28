package it.polimi.ingsw.application.cli.components;

import it.polimi.ingsw.application.cli.util.ANSIColor;

import java.util.Scanner;

/**
 * Command line scene. Defines what is seen by the users and the admitted actions.
 */
public class CLIScene {

    protected final Scanner input;

    public CLIScene(){
        this.input = new Scanner(System.in);
    }

    /**
     * Update internal scene data
     */
    public void update(){
        // Override...
    }

    /**
     * Show the scene on scene
     */
    public void show(){
        // Override...
    }

    /**
     * Execute a command on the scene
     * @param command command to be executed
     * @param arguments command arguments
     */
    public void execute(String command, String[] arguments){
        // Override
    }

    /**
     * Print scene instructions
     */
    public void help(){
        println("The help view is not implemented for this scene.");
    }

    /**
     * Wrapper for System.out.println(String)
     */
    public void println(String message){
        System.out.println(message);
    }
    /**
     * Wrapper for System.out.print(String)
     */
    public void print(String message){
        System.out.print(message);
    }

    /**
     * Wrapper for System.out.print(char)
     */
    public void print(char character){
        System.out.print(character);
    }

    /**
     * Wrapper for println but in red, used to communicate input errors to the user.
     */
    public void error(String errorMessage){
        println(ANSIColor.RED + errorMessage + ANSIColor.RESET);
    }

    /**
     * Clears the console.
     */
    public final static void clearConsole()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
