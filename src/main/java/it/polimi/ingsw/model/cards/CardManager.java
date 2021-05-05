package it.polimi.ingsw.model.cards;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class CardManager {
    /* PATH CONSTANTS */
    public static final String DEV_CARDS_PATH = "src/main/resources/devcards.json";
    public static final int DEV_CARDS_SIZE = 48;
    public static final String LEAD_CARDS_PATH = "src/main/resources/leadcards.json";
    public static final int LEAD_CARDS_SIZE = 16;

    /**
     * @return List containing all the dev cards in the JSON file.
     */
    public static ArrayList<DevCard> loadDevCardsFromJson() {
        // Initialize GSON
        Gson gson = new Gson();

        // Initialize reader
        JsonReader jsonFile;
        try {
            jsonFile = new JsonReader(new FileReader(DEV_CARDS_PATH));
        } catch(FileNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        // Read and return the cards
        return gson.fromJson(jsonFile, new TypeToken<ArrayList<DevCard>>(){}.getType());
    }

    /**
     * @return List containing all the lead cards in the JSON file.
     */
    public static ArrayList<LeadCard> loadLeadCardsFromJson() {
        // Initialize GSON
        Gson gson = new Gson();

        // Initialize reader
        JsonReader jsonFile;
        try {
            jsonFile = new JsonReader(new FileReader(LEAD_CARDS_PATH));
        } catch(FileNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        // Read and return the cards
        return gson.fromJson(jsonFile, new TypeToken<ArrayList<LeadCard>>(){}.getType());
    }
}
