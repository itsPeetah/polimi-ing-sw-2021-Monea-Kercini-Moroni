package it.polimi.ingsw.view.observer;

import it.polimi.ingsw.view.observer.common.DevCardMarketObserver;
import it.polimi.ingsw.view.observer.player.FaithTrackObserver;
import it.polimi.ingsw.view.observer.player.PlayerLeadersObserver;
import it.polimi.ingsw.view.observer.player.StrongboxObserver;
import it.polimi.ingsw.view.observer.player.WarehouseObserver;

public interface PlayerDataObserver extends DevCardMarketObserver, FaithTrackObserver, PlayerLeadersObserver, StrongboxObserver, WarehouseObserver {
}
