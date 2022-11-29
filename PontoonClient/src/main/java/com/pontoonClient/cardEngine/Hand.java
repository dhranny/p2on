package com.pontoonClient.cardEngine;

import com.pontoonClient.game.StateManager;

import java.util.LinkedList;
import java.util.List;

public class Hand {
    List<Card> cards;
    StateManager stateManager;

    public enum HAND_STATE {
        BUST,
        PONTOON,
        SPLITABLE,
        LESSER_THAN_FIFTEEN,
        TWENTY_ONE_WITH_FIVE,
        LESSER_THAN_TWENTY_ONE,
        TWENTY_ONE_WITH_FOUR_OR_LESS;
    }
    public Hand(){
        cards = new LinkedList<>();
    }

    public int getValue(){
        int value = 0;
        for (Card card: cards) {
            value += cardValue(card);
        }
        return value;
    }

    private int cardValue(Card card){
        if(card.value == 1)
            return 11;
        if(card.value >= 10)
            return 10;
        return card.value;
    }

    private HAND_STATE getState(){
        if(getValue() == 21){
            if(cards.size() == 2)
                return HAND_STATE.PONTOON;
            if(cards.size() == 5)
                return HAND_STATE.TWENTY_ONE_WITH_FIVE;
            if(cards.size() < 5)
                return HAND_STATE.TWENTY_ONE_WITH_FOUR_OR_LESS;
        }
        if(getValue() < 21){
            if(getValue() < 15)
                return HAND_STATE.LESSER_THAN_FIFTEEN;
            return HAND_STATE.LESSER_THAN_TWENTY_ONE;
        }
        if(cards.size() == 2 && cardValue(cards.get(0)) == cardValue(cards.get(1))){
            return HAND_STATE.SPLITABLE;
        }
        return HAND_STATE.BUST;
    }

    public void receiveCard(Card card){
        cards.add(card);
        stateManager.newState(getState());
    }
}
