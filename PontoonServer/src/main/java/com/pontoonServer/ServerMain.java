package com.pontoonServer;

import com.pontoonServer.cardEngine.Card;
import com.pontoonServer.server.Connector;
import com.pontoonServer.server.GsonSerializer;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        Connector connector = new Connector();
        GsonSerializer gs = new GsonSerializer();
        Card cd = new Card(Card.CardColor.CLUB, 12);
        System.out.println(gs.createMessage(cd));
    }
}
