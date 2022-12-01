package com.pontoonServer.cardEngine;

import com.pontoonServer.game.Move;
import java.util.LinkedList;
import java.util.List;

public class Hand {
    List<Card> cards;


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


}
