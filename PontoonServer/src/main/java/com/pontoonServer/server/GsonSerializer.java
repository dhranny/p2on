package com.pontoonServer.server;

import com.google.gson.Gson;
import com.pontoonServer.cardEngine.Card;
import com.pontoonServer.game.Move;
import com.pontoonServer.game.PontoonHost;

public class GsonSerializer {

    Gson gson;
    public String createMessage(Card card){
        gson = new Gson();
        Gson gson1 = gson.newBuilder().create();
        return gson1.toJson(card);
    }

    public Move fromMoveJson(String moveJson) {
        gson = new Gson();
        return gson.fromJson(moveJson, Move.class);
    }
}
