package game;

import com.google.gson.Gson;
import cardEngine.Card;
import cardEngine.Deck;

import java.io.IOException;
import java.util.List;

public class Bank extends Player{
    private Deck deck;
    Playable presentEngagement;
    public String name = "The Bank";

    public Bank() throws IOException {
        deck = new Deck();
    }

    @Override
    public String getName() {
        return "The Dealer";
    }

    /**
     * This method is used that the bank can deal out
     * cards to each player.
     * @param players
     */
    public void dealOutCards(List<Playable> players) {
        for (Playable player :
                players) {
            if (player.isBank)
                continue;
            dealCard(player);
        }
    }

    /**
     * This method engages a player in a mini game
     * of dealing until the player requests a hold.
     * @param player
     */
    public void engageMiniGame(Playable player) {
        presentEngagement = player;
        System.out.println(player.getName() + " is the present player now");
        Move.AVAILABLE_MOVES move = player.getNextMove().move;
        while (move != Move.AVAILABLE_MOVES.HOLD) {

            switch (move) {
                case DEAL:
                    dealCard(player);
                    break;
                default:
                    System.out.println("Wrong move");
            }
            if(player.getValue() > 21){
                return;
            }
            move = player.getNextMove().getMove();
        }
    }

    /**
     * When the player is a bank, the method
     * is used to deal a card to one of the
     * connected players
     * @param player
     */
    private void dealCard(Playable player) {
        presentEngagement = player;
        Card card = deck.deal();
        Gson gson1 = (new Gson()).newBuilder().create();
        String cardJson = gson1.toJson(card);
        System.out.println(player.getName() + " received " + cardJson);
        player.getHand().receiveCard(card);
    }

    /**
     * This helps a player to deal itself a
     * card.
     */
    public void dealSelf(){
        Card card = deck.deal();
        value += card.value;
    }

    /**
     * This method sets a player as the bank
     */
    public void makeBank() {
        isBank = true;
    }

}
