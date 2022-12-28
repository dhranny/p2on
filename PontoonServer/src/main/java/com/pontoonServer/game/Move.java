package com.pontoonServer.game;

public class Move {

    public enum AVAILABLE_MOVES{
        DEAL,
        HOLD;
    }

    /**
     * The setter method for move.
     * @param move
     */
    public void setMove(AVAILABLE_MOVES move) {
        this.move = move;
    }

    private AVAILABLE_MOVES move;

    /**
     * The getter method for move
     * @return Move
     */
    public AVAILABLE_MOVES getMove() {
        return move;
    }
}
