package cardEngine;

import game.StateManager;

import java.util.LinkedList;
import java.util.List;

/**
 * This is the class that manages the Hand
 * that hold the Cards for the player.
 */
public class Hand {
    List<Card> cards;
    public StateManager stateManager;

    public enum HAND_STATE {
        BUST,
        LESSER_THAN_TWENTY_ONE;
    }
    public Hand(){
        cards = new LinkedList<>();
        stateManager = new StateManager();
    }

    /**
     * This method is used to get the summation
     * of the value of the card.
     * @return
     */
    public int getValue(){
        int value = 0;
        for (Card card: cards) {
            value += card.value;
        }
        return value;
    }

    private HAND_STATE getState(){
        if (getValue() > 21)
            return HAND_STATE.BUST;
        return HAND_STATE.LESSER_THAN_TWENTY_ONE;
    }

    /**
     * This method is used to get the Value based on
     * the rules of the game.
     * @param card
     * @return
     */
    public void receiveCard(Card card){
        cards.add(card);
        stateManager.newState(getState());
    }

    public StateManager getStateManager(){
        return this.stateManager;
    }

}
