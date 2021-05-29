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
    public static final String MAP_DEV_CARDS_IMAGES_PATH = "src/main/resources/cardsimages.json";
    public static final String DEV_CARDS_IMAGES_PATH = "src/main/resources/images/cards/devcards/";
    public static final String LEAD_CARDS_IMAGES_PATH = "src/main/resources/images/cards/leadcards/";
    private static final HashMap<String, Image> devCardsImages = new HashMap<>();
    private static final HashMap<String, Image> leadCardsImages = new HashMap<>();

    // Statically retrieve the images
    static {
        loadDevCardsImages();
        loadLeadCardsImages();
    }

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

        return leadCardList;
    }

    /**
     * Load the map that maps each dev card (by its ID) to the correspondent image.
     */
    private static void loadDevCardsImages() {

        ArrayList<DevCard> devCardList = loadDevCardsFromJson();

        for(DevCard devCard: devCardList) {
            String devCardImagePath = CardManager.DEV_CARDS_IMAGES_PATH + devCard.getColor().toString().toLowerCase() + "/" + devCard.getCardId() + ".png";
            File file = new File(devCardImagePath);
            Image devCardImage = new Image(file.toURI().toString());

            devCardsImages.put(devCard.getCardId(), devCardImage);
        }

    }

    /**
     * Load the map that maps each lead card (by its ID) to the correspondent image.
     */
    private static void loadLeadCardsImages() {

        ArrayList<LeadCard> leadCardsList = loadLeadCardsFromJson();

        for(LeadCard leadCard: leadCardsList) {
            String devCardImagePath = CardManager.LEAD_CARDS_IMAGES_PATH + leadCard.getCardId() + ".png";
            File file = new File(devCardImagePath);
            Image leadCardImage = new Image(file.toURI().toString());

            leadCardsImages.put(leadCard.getCardId(), leadCardImage);
        }

    }

    /**
     * Get image corresponding to a certain card.
     * @param cardID id of the card.
     * @return JavaFX <code>Image</code> of the card.
     */
    public static Image getImage(String cardID) {
        // Get the image
        Image image = devCardsImages.get(cardID);
        if(image == null) image = leadCardsImages.get(cardID);
        return image;
    }
}
