package game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cardEngine.Card;

/**
 * This class is used to convert objects to json strings
 * and vice versa
 */
public class GsonSerializer {

    public GsonSerializer(){
        gsonB = new GsonBuilder();
        gson = gsonB.create();
    }

    GsonBuilder gsonB;
    Gson gson;

    /**
     * This method is used to create a Card object
     * from a json string.
     * @param cardJson
     * @return
     */
    public Card fromCardJson(String cardJson) {
        return gson.fromJson(cardJson, Card.class);
    }

    /**
     * This method is used to create a Json string
     * from a Move object.
     * @param move
     * @return
     */
    public String createMessage(Move move) {
        return gson.toJson(move);
    }
}
