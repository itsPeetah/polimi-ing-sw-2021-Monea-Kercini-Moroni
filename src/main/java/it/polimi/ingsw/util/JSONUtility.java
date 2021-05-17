package it.polimi.ingsw.util;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JSONUtility {

    public static final Gson gson = new Gson();

    public static String toJson(Object src, Type typeOfSrc){
        return gson.toJson(src, typeOfSrc);
    }

    public static <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
        return gson.fromJson(json, typeOfT);
    }

}
