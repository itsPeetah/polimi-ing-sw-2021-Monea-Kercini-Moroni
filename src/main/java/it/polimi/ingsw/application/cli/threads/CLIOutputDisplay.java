package it.polimi.ingsw.application.cli.threads;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.components.CLIScenes;
import it.polimi.ingsw.application.cli.components.scenes.CLIGame;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;

import java.io.PrintWriter;

/**
 * Runnable class for handling CLI output/scenes
 */
public class CLIOutputDisplay implements Runnable{

    private PrintWriter out;

    public CLIOutputDisplay(){
        out = new PrintWriter(System.out);
    }


    @Override
    public void run() {

        GameApplication gameApplication = GameApplication.getInstance();
        CLIScene currentScene = CLIScenes.TITLE.getScene();
        GameApplicationState currentState = gameApplication.getApplicationState();
        GameApplicationState previousState = currentState;

        currentScene.show();

        while(true){
            currentState = gameApplication.getApplicationState();

            // Change the scene
            if(currentState != previousState) {
                currentScene = CLIScenes.getCurrent();

                if(currentState == GameApplicationState.INGAME)
                    ((CLIGame)CLIScenes.GAME.getScene()).init();

                currentScene.show();
            }

            if(currentState == GameApplicationState.INGAME){
                currentScene.update();
            }

            previousState = currentState;

            try {
                Thread.sleep(30);
            } catch (InterruptedException ex){
                System.out.println(ex.getMessage());
            }
        }

    }
}
