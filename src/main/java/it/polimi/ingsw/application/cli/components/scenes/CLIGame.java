package it.polimi.ingsw.application.cli.components.scenes;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.application.cli.components.scenes.game.*;
import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.controller.view.game.GameController;

public class CLIGame extends CLIScene {

    private CLIBoard board = new CLIBoard();
    private CLIDevCardMarket devCardMarket = new CLIDevCardMarket();
    private CLIFaithTrack faithTrack = new CLIFaithTrack();
    private CLILeadChoice leadChoice = new CLILeadChoice();
    private CLIResourceChoice resourceChoice = new CLIResourceChoice();
    private CLIResourceMarket resourceMarket = new CLIResourceMarket();

    private GameController gameController;
    // TODO Add current and previous state

    public CLIGame() {
        super();
    }

    public void init(){
        // TODO Set game controller
    }

    @Override
    public void update() {
        // TODO Update
    }

    @Override
    public void show() {
        print("========= Masters of Renaissance - In Game =========");

        print("");print("");print("");

        print("====================================================");
    }


}
