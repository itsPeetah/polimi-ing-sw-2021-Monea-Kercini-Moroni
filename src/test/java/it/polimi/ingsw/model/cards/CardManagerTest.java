package it.polimi.ingsw.model.cards;

import com.google.gson.Gson;
import it.polimi.ingsw.model.general.Color;
import it.polimi.ingsw.model.general.Level;
import it.polimi.ingsw.model.general.Production;
import it.polimi.ingsw.model.general.Resources;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CardManagerTest {

    @Test
    public void loadDevCardFromJsonTest() throws IOException {
        /*
        //Generating devcard

        Gson gson = new Gson();
        Resources expRes = new Resources();
        Level expLev = Level.values()[0];
        Color expCol = Color.values()[0];

        DevCard devCard = new DevCard(0, "0", expLev, expCol, expRes, new Production(expRes, expRes));
        FileWriter file = new FileWriter("src/main/resources/devcards.json");
        ArrayList<DevCard> list = new ArrayList<DevCard>();
        list.add(devCard);
        gson.toJson(list , file);
        file.flush();
        file.close();*/

        assertFalse(CardManager.loadDevCardFromJson().isEmpty());
    }
}
