package com.pontoonClient.game;

public class Move {

    public enum AVAILABLE_MOVES{
        TWIST,
        STICK,
        BUY_CARD,
        SPLIT;
    }

    AVAILABLE_MOVES move;

    public AVAILABLE_MOVES getMove() {
        return move;
    }

    public void setMove(AVAILABLE_MOVES move) {
        this.move = move;
    }

    public int getAdditionalStake() {
        return additionalStake;
    }

    public void setAdditionalStake(int additionalStake) {
        this.additionalStake = additionalStake;
    }

    int additionalStake;
}
