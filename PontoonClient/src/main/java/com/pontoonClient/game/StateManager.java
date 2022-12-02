package com.pontoonClient.game;

import com.pontoonClient.cardEngine.Hand;
import com.pontoonClient.server.Connector;

import java.util.HashSet;
import java.util.Set;

public class StateManager {
    Set<Move.AVAILABLE_MOVES> moves;
    CliAnnouncer cliAnnouncer = new CliAnnouncer();
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

    private void finisher(){
        moves.clear();
    }

    public Set<Move.AVAILABLE_MOVES> getMoves(){
        return moves;
    }


}
