package it.polimi.ingsw.application.cli.components.scenes.game;

import it.polimi.ingsw.view.data.GameData;

public interface CLIGameSubScene {
    void update(GameData data);
    void update();
    void show();
    void execute(String command, String[] args);
    void help();
}
