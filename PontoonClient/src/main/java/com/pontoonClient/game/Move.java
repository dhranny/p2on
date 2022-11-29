package com.pontoonClient.game;

public class Move {

    public enum AVAILABLE_MOVES{
        TWIST,
        STICK,
        BUY_CARD,
        SPLIT;
    }

    AVAILABLE_MOVES move;

    int additionalStake;
}
