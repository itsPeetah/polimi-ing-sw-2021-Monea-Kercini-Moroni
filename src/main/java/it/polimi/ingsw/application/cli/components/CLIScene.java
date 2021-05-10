package it.polimi.ingsw.application.cli.components;

import javax.xml.stream.events.StartDocument;
import java.util.Scanner;

public abstract class CLIScene {

    protected final String title;
    protected final Scanner input;

    public CLIScene(String title){
        this.title = title;
        this.input = new Scanner(System.in);
    }

    public void update(){
        // Override
    }

    public void show(){
        // Override
    }

    public void getInput(){
        // Override
    }

    public void help(){
        // Override
    }

    public void stdout(String message){
        System.out.println(message);
    }

    public final static void clearConsole()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
