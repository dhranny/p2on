package com.pontoonClient.game;

import com.pontoonClient.cardEngine.Hand;
import com.pontoonClient.server.Connector;

import java.util.HashSet;
import java.util.Set;

/**
 * This method manages and control the state
 * of a player's hand.
 */
public class StateManager {
    Set<Move.AVAILABLE_MOVES> moves;
    public Connector connection;

    public StateManager(){
        moves = new HashSet<Move.AVAILABLE_MOVES>();
        moves.add(Move.AVAILABLE_MOVES.DEAL);
        moves.add(Move.AVAILABLE_MOVES.HOLD);
    }

    public void newState(Hand.HAND_STATE handState){

        if(handState == Hand.HAND_STATE.BUST) {
            finisher();
            System.out.println("You've busted");
            connection.tellServer();

        }

    }

    /**
     * This method  is used to clear state when a round of
     * game finishes.
     */
    private void finisher(){
        moves.clear();
    }

    public Set<Move.AVAILABLE_MOVES> getMoves(){
        return moves;
    }


}
