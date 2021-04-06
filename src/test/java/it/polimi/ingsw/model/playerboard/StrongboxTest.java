package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import org.junit.Test;

import static org.junit.Assert.*;

public class StrongboxTest {

    @Test
    public void deposit() {
        Strongbox sb = new Strongbox();
        Resources res = new Resources();
        res.add(ResourceType.STONES, 3);
        sb.deposit(res);
        assertEquals(sb.getResourcesAvailable(), res);
    }

    @Test
    public void withdraw() {
    }

    @Test
    public void getResourceAmountStrongbox() {
    }

    @Test
    public void getResourcesAvailable() {
    }
}