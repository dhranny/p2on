package com.pontoonServer.server;

import com.google.gson.Gson;
import com.pontoonServer.cardEngine.Card;
import com.pontoonServer.game.PontoonHost;

public class GsonSerializer {

    Gson gson;
    public String createMessage(Card card){
        gson = new Gson();
        Gson gson1 = gson.newBuilder().create();
        return gson1.toJson(card);
    }
}
