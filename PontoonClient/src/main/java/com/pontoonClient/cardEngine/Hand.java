package com.pontoonClient.cardEngine;

import com.pontoonClient.game.StateManager;

import java.util.LinkedList;
import java.util.List;

public class Hand {
    List<Card> cards;
    public StateManager stateManager;

    public enum HAND_STATE {
        BUST,
        LESSER_THAN_TWENTY_ONE;
    }
    public Hand(){
        cards = new LinkedList<>();
        stateManager = new StateManager();
    }

    public int getValue(){
        int value = 0;
        for (Card card: cards) {
            value += card.value;
        }
        return value;
    }

    private HAND_STATE getState(){
        if (getValue() > 21)
            return HAND_STATE.BUST;
        return HAND_STATE.LESSER_THAN_TWENTY_ONE;
    }

    public void receiveCard(Card card){
        cards.add(card);
        stateManager.newState(getState());
    }

    public StateManager getStateManager(){
        return this.stateManager;
    }

}
