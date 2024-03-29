package game;

import java.io.Serializable;

public class Move implements Serializable {

    public enum AVAILABLE_MOVES{
        DEAL,
        HOLD,
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
