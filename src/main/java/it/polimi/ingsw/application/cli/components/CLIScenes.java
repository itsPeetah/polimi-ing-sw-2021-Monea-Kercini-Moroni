package it.polimi.ingsw.application.cli.components;

import it.polimi.ingsw.application.cli.components.scenes.*;
import it.polimi.ingsw.application.common.GameApplication;

/**
 * Collection of CLIScenes.
 */
public enum CLIScenes {

    TITLE(new CLITitle(), "Masters of Renaissance - Welcome"),
    HOME(new CLIHome(), "Masters of Renaissance - Home"),
    STOP(new CLIStop(), "Masters of Renaissance - Thank you for playing"),
    MPLOBBY(new CLILobby(), "Masters of Renaissance - Join or create a game"),
    MPROOM(new CLIRoom(), "Masters of Renaissance - Waiting for game to start"),
    GAME(new CLIGame(), "Masters of Renaissance"),
    WAIT(new CLIWait(), "Masters of Renaissance - Please, wait...");

    private final String title;
    private final CLIScene scene;


    private CLIScenes(CLIScene scene, String title){
        this.scene = scene;
        this.title = title;
    }

    public CLIScene getScene() {
        return scene;
    }
    public String getTitle() {
        return title;
    }

    public static CLIScene getCurrent(){
        switch (GameApplication.getInstance().getApplicationState()){
            case STARTUP:
                // Title screen
                return TITLE.getScene();
            case STARTED:
                // Home (MP, SP, Options, Credits)
                return HOME.getScene();
            case LOBBY:
                return MPLOBBY.getScene();
            case PREGAME:
                return MPROOM.getScene();
            case INGAME:
                return GAME.getScene();
            default:
                return WAIT.getScene();
        }
    }
}
