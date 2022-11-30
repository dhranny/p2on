package com.pontoonServer.game;

import com.pontoonServer.cardEngine.Hand;
import com.pontoonServer.server.ConnectedPlayer;

import java.util.ArrayList;
import java.util.List;

public class Player {

    List<Hand> hands;

    int stake;

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
        //TODO: flesh out making bank
    }

    public void dealOutCards(List<Player> players) {
        //TODO: FLESH OUT DEALING OUT CARDS
    }

    public void engageMiniGame(Player player) {
        //TODO: flesh out engage mini game
        for (Hand hand : hands) {
            Move.AVAILABLE_MOVES move = null;
            while (move != Move.AVAILABLE_MOVES.STICK) {
                Move moveObj = hand.getNextMove();

                move = moveObj.getMove();
                switch (moveObj.getMove()) {
                    case SPLIT:
                        player.getNewHand();
                    case TWIST:
                        dealCard(hand);
                    case BUY_CARD:
                        dealCard(hand);
                    default:
                        throw new IllegalArgumentException("Wrong move");
                }
            }
        }
    }

    private void dealCard(Hand hand) {
    }

    private void getNewHand() {
    }

    private Move getNextMove() {
        connection.sendMessage("getmove");
    }

    public void shareStake(Player player) {
        //TODO: flesh out stake sharing
    }

    public void getStake() {
        connection.sendMessage("getstake");
        try {
            hands.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setStake(int stake) {
        this.stake = stake;
    }
}
