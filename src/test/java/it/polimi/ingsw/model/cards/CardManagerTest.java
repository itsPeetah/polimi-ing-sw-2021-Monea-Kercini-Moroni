package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.general.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CardManagerTest {

    @Test
    public void loadDevCardFromJsonTest() {
        /*
        //Generating devcard

        Gson gson = new Gson();
        Resources expRes = new Resources();
        expRes.add(ResourceType.SHIELDS, 3);
        Level expLev = Level.values()[0];
        Color expCol = Color.values()[0];

        DevCard devCard = new DevCard(0, "0", expLev, expCol, expRes, new Production(expRes, expRes));
        FileWriter file = new FileWriter("src/main/resources/devcards.json");
        ArrayList<DevCard> list = new ArrayList<DevCard>();
        list.add(devCard);
        gson.toJson(list , file);
        file.flush();
        file.close();*/

        ArrayList<DevCard> cards = CardManager.loadDevCardFromJson();
        assertFalse(cards.isEmpty());
        Resources expRes = new Resources();
        expRes.add(ResourceType.SHIELDS, 3);
        Level expLev = Level.values()[0];
        Color expCol = Color.values()[0];

        DevCard devCard = new DevCard(0, "0", expLev, expCol, expRes, new Production(expRes, expRes));
        assertEquals(devCard.getVictoryPoints(), cards.get(0).getVictoryPoints());
        System.out.println(cards.get(0));
    }
}
