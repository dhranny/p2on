package com.pontoonServer.cardEngine;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    public List<Card> cards;
    public Deck (){
        cards = create();
        shuffle();
    }
    private List<Card> create(){
        List<Card> cards = new ArrayList();
        for (Card.CardColor color : Card.CardColor.values()) {
            for (int value = 1; value <= 13; value++) {
                cards.add(new Card(color, value));
            }
        }
        return cards;
    }

    public void shuffle(){
        Card temp;
        for (int i =0; i < cards.size(); i++) {
            int randomNumber = (int)Math.random() * cards.size();
            temp = cards.get(i);
            cards.remove(i);
            cards.add(i, cards.get(randomNumber));
            cards.remove(randomNumber);
            cards.add(randomNumber, temp);
        }
    }

    public Card deal(){
        Card dealedCard = cards.get(1);
        cards.remove(1);
        return dealedCard;
    }
}
