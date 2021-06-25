package it.polimi.ingsw.util;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * GSON wrapper class
 */
public class JSONUtility {

    // Static Gson object instance
    public static final Gson gson = new Gson();

    /**
     * Serialize an object to json
     * @param src the object to serialize
     * @param typeOfSrc the class of the object to serialize
     * @return the json representation of the serialized object
     */
    public static String toJson(Object src, Type typeOfSrc){
        return gson.toJson(src, typeOfSrc);
    }

    /**
     * Deserialize an object from json
     * @param json the json representation to deserialize
     * @param typeOfT the class of the object to deserialize
     * @param <T> the type (class name) of the object to deserialize
     * @return the deserialized object
     * @throws JsonSyntaxException if the json string is not formatted correctly
     */
    public static <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
        return gson.fromJson(json, typeOfT);
    }

}
