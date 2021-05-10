package it.polimi.ingsw.application.cli.components;

public abstract class CLIScene {

    protected final String title;

    public CLIScene(String title){
        this.title = title;
    }

    public void show(){

    }

    public void getInput(){

    }

    public void stdout(String message){
        System.out.println(message);
    }

}
