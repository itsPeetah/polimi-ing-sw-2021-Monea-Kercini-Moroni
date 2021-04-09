package it.polimi.ingsw.model.cards;

import com.google.gson.Gson;
import it.polimi.ingsw.model.general.*;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class CardManagerTest {

    @Test
    public void loadDevCardsFromJsonTest() {
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

        ArrayList<DevCard> cards = CardManager.loadDevCardsFromJson();
        assertFalse(cards.isEmpty());
        Gson gson = new Gson();
        System.out.println(gson.toJson(cards.get(13)));
    }

    @Test
    public void loadLeadCardsFromJsonTest() throws IOException {
        /*
        //Generating leadcard

        Gson gson = new Gson();
        // Create lead card
        Resources emptyRes = new Resources();
        Resources nonEmptyRes = new Resources();
        nonEmptyRes.add(ResourceType.SHIELDS, 1);
        HashMap<Color, Integer> colorsReq = new HashMap<>();
        colorsReq.put(Color.RED, 2);
        HashMap<Level, Integer> levelsReq = new HashMap<>();
        levelsReq.put(Level.LOW, 2);

        LeadCardAbility ability = new LeadCardAbility(nonEmptyRes, nonEmptyRes, nonEmptyRes, new Production(nonEmptyRes, nonEmptyRes));
        LeadCardRequirements req = new LeadCardRequirements(colorsReq, levelsReq, nonEmptyRes);

        LeadCard lc = new LeadCard(0, "testLead", req, ability);

        FileWriter file = new FileWriter(CardManager.LEAD_CARDS_PATH);
        ArrayList<LeadCard> list = new ArrayList<>();
        list.add(lc);
        gson.toJson(list , file);
        file.flush();
        file.close();*/

        ArrayList<LeadCard> cards = CardManager.loadLeadCardsFromJson();
        assertFalse(cards.isEmpty());
        // Create lead card
        Resources nonEmptyRes = new Resources();
        nonEmptyRes.add(ResourceType.SHIELDS, 1);
        HashMap<Color, Integer> colorsReq = new HashMap<>();
        colorsReq.put(Color.PURPLE, 2);
        HashMap<Level, Integer> levelsReq = new HashMap<>();
        levelsReq.put(Level.LOW, 2);

        LeadCardAbility ability = new LeadCardAbility(nonEmptyRes, nonEmptyRes, nonEmptyRes, new Production(nonEmptyRes, nonEmptyRes));
        LeadCardRequirements req = new LeadCardRequirements(colorsReq, levelsReq, nonEmptyRes);

        LeadCard lc = new LeadCard(0, "testLead", req, ability);
        assertEquals(lc.getAbility().getProduction().getOutput().getAmountOf(ResourceType.SHIELDS), cards.get(0).getAbility().getProduction().getOutput().getAmountOf(ResourceType.SHIELDS));
        System.out.println(cards.get(0));

    }
}
