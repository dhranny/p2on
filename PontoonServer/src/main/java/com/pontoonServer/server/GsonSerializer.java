package com.pontoonServer.server;

import com.google.gson.Gson;
import com.pontoonServer.cardEngine.Card;
import com.pontoonServer.game.Move;
import com.pontoonServer.game.PontoonHost;

public class GsonSerializer {

    Gson gson;

    /**
     * This method is used to create a Json file
     * from a Card object.
     * @param card
     * @return
     */
    public String createMessage(Card card){
        gson = new Gson();
        Gson gson1 = gson.newBuilder().create();
        return gson1.toJson(card);
    }

    /**
     * THis method is used to convert Json to Move
     * object.
     * @param moveJson
     * @return
     */
    public Move fromMoveJson(String moveJson) {
        gson = new Gson();
        return gson.fromJson(moveJson, Move.class);
    }
}
