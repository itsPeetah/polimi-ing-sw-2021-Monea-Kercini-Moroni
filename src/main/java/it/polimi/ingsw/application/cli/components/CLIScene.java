package it.polimi.ingsw.application.cli.components;

public abstract class CLIScene {

    protected final String title;

    public CLIScene(String title){
        this.title = title;
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

    public void stdout(String message){
        System.out.println(message);
    }

}
