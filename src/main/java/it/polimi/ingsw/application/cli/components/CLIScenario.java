package it.polimi.ingsw.application.cli.components;

import it.polimi.ingsw.application.cli.components.scenes.CLILobby;
import it.polimi.ingsw.application.cli.components.scenes.CLIRoom;
import it.polimi.ingsw.application.cli.components.scenes.CLITitle;

public enum CLIScenario {

    TITLE(new CLITitle(), "Masters of Renaissance - Title Screen"),
    MPLOBBY(new CLILobby(), "Masters of Renaissance - Join or create a game"),
    MPROOM(new CLIRoom(), "Masters of Renaissance - Waiting for game to start");

    private final String title;
    private final CLIScene scene;


    private CLIScenario(CLIScene scene, String title){
        this.scene = scene;
        this.title = title;
    }

    public CLIScene getScene() {
        return scene;
    }
    public String getTitle() {
        return title;
    }
}
