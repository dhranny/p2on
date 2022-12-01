package com.pontoonClient.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pontoonClient.cardEngine.Card;

public class GsonSerializer {

    public GsonSerializer(){
        gsonB = new GsonBuilder();
        gson = gsonB.create();
    }

    GsonBuilder gsonB;
    Gson gson;
    public String createMessage(Card card){

        return gson.toJson(card);
    }

    public Move fromMoveJson(String moveJson) {
        gson = new Gson();
        Gson gson1 = gson.newBuilder().create();
        return gson.fromJson(moveJson, Move.class);
    }

    public Card fromCardJson(String cardJson) {
        return gson.fromJson(cardJson, Card.class);
    }

    public String createMessage(Move move) {
        return gson.toJson(move);
    }
}
