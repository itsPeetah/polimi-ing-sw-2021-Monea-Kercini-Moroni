package it.polimi.ingsw.application.cli.components.scenes.game;

import it.polimi.ingsw.view.data.GameData;

public interface ICLIGameSubScene {
    void update(GameData data);
    void update();
    void show();
    void getInput();
    void help();
}
