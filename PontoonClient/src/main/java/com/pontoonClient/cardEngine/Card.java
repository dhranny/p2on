package com.pontoonClient.cardEngine;

import java.util.Objects;

/**
 * Class for the specification of a card object.
 */
public class Card {

    public Card (CardColor color, int value, CardType type){
        this.color = color;
        this.value = value;
        this.type = type;
    }
    public CardColor color;
    public int value;
    public CardType type;

    public enum CardColor{
        SPADES,
        DIAMOND,
        HEART,
        CLUB;
    }

    public enum CardType{
        PIP,
        JACK,
        QUEEN,
        KING;
    }

    /**
     * This is a custom equals method for checking equality.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value == card.value && color == card.color && type == this.type;
    }

    /**
     * This is a custom method for creating hashcode for the object
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(color, value);
    }


}
