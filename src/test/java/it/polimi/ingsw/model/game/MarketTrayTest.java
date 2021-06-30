package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.game.util.GameSettingsLevel;
import it.polimi.ingsw.model.game.util.MarketTrayFactory;
import it.polimi.ingsw.model.general.Resources;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MarketTrayTest {



    @Test
    public void testPickRow0(){
        MarketTray mt = MarketTrayFactory.BuildMarketTray(GameSettingsLevel.LOW);
        Resources r;
        for(int i = 0; i <= mt.getColumns(); i++) {
            try {
                r = mt.pickRow(0);
                assertTrue(r.getTotalAmount() == mt.getColumns());
            } catch (MarketTrayException e){
                fail();
            }
        }
    }

    @Test
    public void testPickColumn0(){
        MarketTray mt = MarketTrayFactory.BuildMarketTray(GameSettingsLevel.LOW);
        Resources r;
        for(int i = 0; i <= mt.getRows(); i++) {
            try {
                r = mt.pickColumn(0);
                assertTrue(r.getTotalAmount() == mt.getRows());
            } catch (MarketTrayException e){
                fail();
            }
        }
    }

}