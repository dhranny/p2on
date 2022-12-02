package com.pontoonServer.game;

public class Move {

    public enum AVAILABLE_MOVES{
        DEAL,
        HOLD;
    }

    private int handId;

    public int getHandId() {
        return handId;
    }

    public void setHandId(int handId) {
        this.handId = handId;
    }

    public void setMove(AVAILABLE_MOVES move) {
        this.move = move;
    }

    public void setAdditionalStake(int additionalStake) {
        this.additionalStake = additionalStake;
    }

    public int getAdditionalStake() {
        return additionalStake;
    }

    private AVAILABLE_MOVES move;

    public AVAILABLE_MOVES getMove() {
        return move;
    }

    private int additionalStake;
}
