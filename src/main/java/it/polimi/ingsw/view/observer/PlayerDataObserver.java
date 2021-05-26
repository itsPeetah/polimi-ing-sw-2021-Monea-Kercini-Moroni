package it.polimi.ingsw.view.observer;

import it.polimi.ingsw.view.observer.common.DevCardMarketObserver;
import it.polimi.ingsw.view.observer.player.*;

public interface PlayerDataObserver extends DevCardsObserver, FaithTrackObserver, PlayerLeadersObserver, StrongboxObserver, WarehouseObserver {
}
