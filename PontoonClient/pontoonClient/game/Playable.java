package game;

import cardEngine.Hand;

public interface Playable {
    boolean isBank = false;
    Move getNextMove();
    int getValue();
    void end();
    String getName();
    Hand getHand();
}
