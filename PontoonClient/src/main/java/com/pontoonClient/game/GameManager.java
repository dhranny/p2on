package com.pontoonClient.game;

import com.google.gson.Gson;
import com.pontoonClient.cardEngine.Card;
import com.pontoonClient.cardEngine.Deck;
import com.pontoonClient.cardEngine.Hand;
import com.pontoonClient.server.Connector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GameManager {

    public int initialStake;
    public int sumedStake;
    CliAnnouncer announce;
    Connector connector;
    boolean isBank;
    Deck deck;
    Gson gson;
    public Hand presentHand;
    public List<Hand> hands;

    public GameManager(Connector connector){
        announce = new CliAnnouncer();
        this.connector = connector;
        deck = new Deck();
        gson = new Gson();
        Hand nHand = new Hand();
        presentHand = nHand;
        hands = new ArrayList<>();
        startGame();
    }

    private void startGame() {
        announce.output.println("Welcome to Pont22n");
        connector.sendMessage("getgroups");
    }

    public void dealCard(){
        Card card = deck.deal();
        Gson gson1 = gson.newBuilder().create();
        String cardJson = gson1.toJson(card);
        connector.sendMessage("card" + cardJson);
    }

    public void placeCard(Card card){
        presentHand.receiveCard(card);
        System.out.println("The present value of your hand is " + presentHand.getValue());
    }

    public Set<Move.AVAILABLE_MOVES> getAvailableMoves(){
        return presentHand.getStateManager().getMoves();
    }


}
