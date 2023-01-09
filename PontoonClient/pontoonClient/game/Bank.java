package com.pontoonServer.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pontoonServer.cardEngine.Card;
import com.pontoonServer.cardEngine.Deck;
import com.pontoonServer.cardEngine.Hand;
import com.pontoonServer.server.ConnectedPlayer;

import java.util.List;

public class Bank extends Player{
    private Deck deck;
    public String name = "The Bank";

    public Bank(ConnectedPlayer connection) {
        super(connection);
    }

    public Bank(){
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
    public void dealOutCards(List<Player> players) {
        for (Player player :
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
    public void engageMiniGame(Player player) {
        presentEngagement = player;
        Move.AVAILABLE_MOVES move = getNextMove(player).getMove();
        while (move != Move.AVAILABLE_MOVES.HOLD) {

            switch (move) {
                case DEAL:
                    dealCard(player);
                    break;
                default:
                    System.out.println("Wrong move");
            }
            if(player.value > 21){
                return;
            }
            move = getNextMove(player).getMove();
        }
    }

    /**
     * When the player is a bank, the method
     * is used to deal a card to one of the
     * connected players
     * @param player
     */
    private void dealCard(Player player) {
        presentEngagement = player;
        Card card = deck.deal();
        Gson gson1 = (new Gson()).newBuilder().create();
        String cardJson = gson1.toJson(card);
        player.getConnector().sendMessage("card" + cardJson);
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
