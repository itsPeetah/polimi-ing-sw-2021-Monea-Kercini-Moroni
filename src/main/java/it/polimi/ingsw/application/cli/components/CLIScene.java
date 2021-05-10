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

    public final static void clearConsole()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        /*try
        {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }*/
    }

}
