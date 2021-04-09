package it.polimi.ingsw.model.cards;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class CardManager {
    public static final String devCardsPath = "src/main/resources/devcards.json";

    public static ArrayList<DevCard> loadDevCardFromJson() {
        // Initialize GSON
        Gson gson = new Gson();

        // Initialize reader
        JsonReader jsonFile;
        try {
            jsonFile = new JsonReader(new FileReader(devCardsPath));
        } catch(FileNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        // Read and return the cards
        Type collectionDevCard = new TypeToken<ArrayList<DevCard>>(){}.getType();
        return gson.fromJson(jsonFile, collectionDevCard);
    }
}
