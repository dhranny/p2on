package com.pontoonServer.game;

import com.pontoonServer.cardEngine.Hand;
import com.pontoonServer.server.Connector;

import java.util.ArrayList;
import java.util.List;

public class Player {

    List<Hand> hands;

    public Player(){
        hands = new ArrayList<>();
        hands.add(new Hand());
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
        return null;
    }

    public void shareStake(Player player) {
        //TODO: flesh out stake sharing
    }

    public void getStake() {
        //TODO: flesh out getting stake from for each player.
    }
}
