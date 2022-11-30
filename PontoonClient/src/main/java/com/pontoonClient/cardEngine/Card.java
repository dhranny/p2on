package com.pontoonClient.cardEngine;

import java.util.Objects;

public class Card {

    public Card (CardColor color, int value){
        this.color = color;
        this.value = value;
    }
    public CardColor color;
    public int value;

    public enum CardColor{
        SPADES,
        DIAMOND,
        HEART,
        CLUB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value == card.value && color == card.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, value);
    }


}