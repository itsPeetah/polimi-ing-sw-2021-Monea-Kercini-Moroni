package it.polimi.ingsw.model.cards;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CardManager {
    /* PATH CONSTANTS */
    public static final String DEV_CARDS_PATH = "src/main/resources/devcards.json";
    public static final int DEV_CARDS_SIZE = 48;
    public static final String LEAD_CARDS_PATH = "src/main/resources/leadcards.json";
    public static final int LEAD_CARDS_SIZE = 16;

    /* JAVA FX CONSTANTS */
    public static final String MAP_CARDS_IMAGES_PATH = "src/main/resources/cardsimages.json";
    public static final String DEV_CARDS_IMAGES_PATH = "src/main/resources/images/cards/devcards/";
    public static final String LEAD_CARDS_IMAGES_PATH = "src/main/resources/images/cards/leadcards/";
    private static HashMap<String, Image> devCardsImages;

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
        ArrayList<DevCard> devCardList = gson.fromJson(jsonFile, new TypeToken<ArrayList<DevCard>>(){}.getType());

        try {
            jsonFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return devCardList;
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
        ArrayList<LeadCard> leadCardList = gson.fromJson(jsonFile, new TypeToken<ArrayList<LeadCard>>(){}.getType());

        try {
            jsonFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Read and return the cards
        return leadCardList;
    }

    /**
     * Load the map that maps each dev card (by its ID) to the correspondent image.
     */
    private static HashMap<String, Image> loadDevCardsImages() {
        HashMap<String, Image> imageHashMap = new HashMap<>();

        // Initialize GSON
        Gson gson = new Gson();

        // Initialize reader
        JsonReader jsonFile;

        try {
            // Read image paths file
            jsonFile = new JsonReader(new FileReader(MAP_CARDS_IMAGES_PATH));
            HashMap<String, String> pathMap = gson.fromJson(jsonFile, new TypeToken<HashMap<String, String>>() {}.getType());

            // For each card ID
            for(String cardID: pathMap.keySet()) {
                // Get the path of the image
                String cardImagePath = pathMap.get(cardID);
                System.out.println(cardImagePath);
                // Load the image
                File file = new File(cardImagePath);
                Image i = new Image(file.toURI().toString());

                // Put the image in the map
                imageHashMap.put(cardID, i);
            }

            return imageHashMap;
        } catch(FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the map that maps each dev card (by its ID) to the correspondent image.
     */
    public static HashMap<String, Image> getDevCardsImages() {
        if(devCardsImages == null) devCardsImages = loadDevCardsImages();
        return devCardsImages;
    }
}
