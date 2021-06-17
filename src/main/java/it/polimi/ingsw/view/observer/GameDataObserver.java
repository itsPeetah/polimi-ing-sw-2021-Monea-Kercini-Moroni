package it.polimi.ingsw.view.observer;

public interface GameDataObserver extends CommonDataObserver, PlayerDataObserver {
    void onPlayerTableChange();

    void onCurrentPlayerChange();
}
