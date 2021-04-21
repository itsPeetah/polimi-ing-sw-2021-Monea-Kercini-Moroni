package it.polimi.ingsw.model.cards;

import com.google.gson.Gson;
import it.polimi.ingsw.model.general.*;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class CardManagerTest {

    // TODO remove comments and test more after all cards are present

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
        //System.out.println(gson.toJson(cards.get(13)));
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
        colorsReq.put(Color.PURPLE, 2);
        HashMap<Color, Level> levelsReq = new HashMap<>();
        levelsReq.put(Color.PURPLE, Level.LOW);

        LeadCardAbility ability1 = new LeadCardAbility(nonEmptyRes, nonEmptyRes, ResourceType.STONES, new Production(nonEmptyRes, nonEmptyRes));
        LeadCardAbility ability2 = new LeadCardAbility(nonEmptyRes, nonEmptyRes, ResourceType.STONES, new Production(emptyRes, emptyRes));
        LeadCardRequirements req1 = new LeadCardRequirements(colorsReq, new HashMap<Color, Level>(), nonEmptyRes);
        LeadCardRequirements req2 = new LeadCardRequirements(new HashMap<Color, Integer>(), levelsReq, nonEmptyRes);


        LeadCard lc1 = new LeadCard(0, "testLead", req1, ability1);
        LeadCard lc2 = new LeadCard(0, "testLead", req2, ability2);

        FileWriter file = new FileWriter(CardManager.LEAD_CARDS_PATH);
        ArrayList<LeadCard> list = new ArrayList<>();
        list.add(lc1);
        list.add(lc2);
        gson.toJson(list , file);
        file.flush();
        file.close();*/

        ArrayList<LeadCard> cards = CardManager.loadLeadCardsFromJson();
        assertFalse(cards.isEmpty());
        for(LeadCard card: cards) {
            assertTrue(card.getAbility()!=null && card.getAbility().getProduction()!=null && card.getAbility().getWhiteMarbleReplacement()!=null && card.getAbility().getExtraWarehouseSpace()!=null && card.getAbility().getResourceDiscount()!=null);

        }
        Gson gson = new Gson();
        //System.out.println(gson.toJson(cards.get(0)));
    }
}
