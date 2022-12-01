package com.pontoonClient.game;

import com.pontoonClient.cardEngine.Hand;

import java.util.HashSet;
import java.util.Set;

public class StateManager {
    Set<Move.AVAILABLE_MOVES> moves;
    CliAnnouncer cliAnnouncer = new CliAnnouncer();

    public StateManager(){
        moves = new HashSet<Move.AVAILABLE_MOVES>();
        moves.add(Move.AVAILABLE_MOVES.TWIST);
        moves.add(Move.AVAILABLE_MOVES.STICK);
        moves.add(Move.AVAILABLE_MOVES.BUY_CARD);
    }

    public void newState(Hand.HAND_STATE handState){
        if(handState == Hand.HAND_STATE.SPLITABLE)
            addSplit();
        if(handState != Hand.HAND_STATE.SPLITABLE)
            removeSplit();
        if(handState == Hand.HAND_STATE.PONTOON) {
            finisher();
            System.out.println("You've gotten a pontoon");
        }
        if(handState == Hand.HAND_STATE.BUST) {
            finisher();
            System.out.println("You've busted");
        }
        if(handState == Hand.HAND_STATE.TWENTY_ONE_WITH_FIVE) {
            finisher();
            System.out.println("You have 21 with five cards");;
        }
    }

    private void finisher(){
        moves.clear();
    }

    public Set<Move.AVAILABLE_MOVES> addSplit(){
        moves.add(Move.AVAILABLE_MOVES.SPLIT);
        return moves;
    }

    public Set<Move.AVAILABLE_MOVES> removeSplit(){
        moves.remove(Move.AVAILABLE_MOVES.SPLIT);
        return moves;
    }

    public Set<Move.AVAILABLE_MOVES> getMoves(){
        return moves;
    }


}
