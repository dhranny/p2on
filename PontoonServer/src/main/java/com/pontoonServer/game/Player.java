package com.pontoonServer.game;

import com.google.gson.GsonBuilder;
import com.pontoonServer.cardEngine.Card;
import com.pontoonServer.cardEngine.Hand;
import com.pontoonServer.server.ConnectedPlayer;

import java.util.ArrayList;
import java.util.List;

public class Player {

    List<Hand> hands;
    int value;
    public String name;
    public boolean isBank;
    Player presentEngagement;
    public Move nextMove;

    public List<Hand> getHands() {
        return hands;
    }

    public ConnectedPlayer getConnection() {
        return connection;
    }

    private ConnectedPlayer connection;

    public Player(ConnectedPlayer connection){
        hands = new ArrayList<>();
        hands.add(new Hand());
        this.connection = connection;
    }

    public void makeBank() {
        connection.sendMessage("announceYou are now the bank");
        isBank = true;
    }

    public void dealOutCards(List<Player> players) {
        for (Player player :
                players) {
            if (player.isBank)
                continue;
            dealCard(player);
        }
    }

    public void engageMiniGame(Player player) {
        presentEngagement = player;
        for (Hand hand : hands) {
            Move.AVAILABLE_MOVES move = getNextMove(player).getMove();
            while (move != Move.AVAILABLE_MOVES.HOLD) {

                switch (move) {
                    case DEAL:
                        dealCard(player);
                        break;
                    default:
                        System.out.println("Wrong move");
                }
                move = getNextMove(player).getMove();
            }
        }
    }

    public void start(){
        connection.presentPlayer = this;
    }

    public void dealSelf(){
        dealCard(this);
    }

    public int getValue(){
        //connection.sendMessage("getvalue");
        //synchronized (connection){
        //    try {
        //        connection.wait();
        //    } catch (InterruptedException e) {
        //        e.printStackTrace();
        //    }
        //}
        return value;
    }

    public String getName(){
        connection.sendMessage("getname");
        synchronized (connection){
            try {
                connection.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return name;
    }

    private void dealCard(Player player) {
        presentEngagement = player;
        connection.sendMessage("dealcard");
        synchronized (connection){
            try {
                connection.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Move getNextMove(Player player) {
        player.connection.sendMessage("getmove");
        synchronized (player.connection){
            try {
                player.connection.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return presentEngagement.nextMove;
    }

    public void shareStake(Player player) {
        //TODO: flesh out stake sharing
    }

    public void getStake(Player player) {
        player.connection.sendMessage("getstake");
        synchronized (player.connection){
            try {
                player.connection.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setStake(int stake) {
        this.value = stake;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void dealPresent(String message) {
        GsonBuilder gb = new GsonBuilder();
        System.out.println(message);
        presentEngagement.value += gb.create().fromJson(message.strip().substring(4), Card.class).value;
        presentEngagement.connection.sendMessage(message);
    }
}
