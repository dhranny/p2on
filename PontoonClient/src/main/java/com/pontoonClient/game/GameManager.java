package com.pontoonClient.game;

import com.google.gson.Gson;
import com.pontoonClient.cardEngine.Card;
import com.pontoonClient.cardEngine.Deck;
import com.pontoonClient.cardEngine.Hand;
import com.pontoonClient.server.Connector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This method manages the procedure for
 * the user playing the game.
 * This is the main controller for the in
 * the client side.
 */
public class GameManager {

    public int initialStake;
    public int sumedStake;
    Connector connector;
    boolean isBank;
    Deck deck;
    Gson gson;
    public Hand presentHand;
    public List<Hand> hands;

    public GameManager(Connector connector){
        this.connector = connector;
        deck = new Deck();
        gson = new Gson();
        Hand nHand = new Hand();
        presentHand = nHand;
        nHand.stateManager.connection = connector;
        hands = new ArrayList<>();
        startGame();
    }

    /**
     * This method is used to  the game.
     * It starts the routine of the game client.
     */
    private void startGame() {
        System.out.println("Welcome to Pont22n");
        putName();
        joinOrStart();
    }

    /**
     * This is the method that asks for the name of the client.
     * The client asks for the name of the user.
     */
    private void putName(){
        System.out.println("Please input your name");
        String name = null ;
        try {
            name = connector.getCliScanner().readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connector.sendMessage("name" + name);
    }

    /**
     * This method is used to ask the user to
     * either join or start a new game.
     */
    public void joinOrStart(){
        System.out.println("Do you want to join a game or start: to join put   JOIN   to start put   START");
        String response = null ;
        boolean responseIsWell = false;
        while(!responseIsWell){
            try {
                response = connector.getCliScanner().readLine().strip();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(response.equalsIgnoreCase("JOIN")){
                connector.sendMessage("join");
                responseIsWell = true;
            }
            else if(response.equalsIgnoreCase("START")){
                System.out.println("How many rounds do you wish to play");
                boolean roundIsWell = false;
                while (!roundIsWell){
                    try {
                        int rounds = Integer.parseInt(connector.getCliScanner().readLine());
                        connector.sendMessage("round" + rounds);
                        roundIsWell = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NumberFormatException e){
                        System.out.println("The rounds shoulxxzxx`d be a whole number");
                    }
                }
                responseIsWell = true;
            }
            else {
                System.out.println("Your response should be JOIN or START");
            }
        }
    }

    /**
     * This method is the do correct works when the
     * client receives a card from the client.
     * @param card
     */
    public void placeCard(Card card){
        presentHand.receiveCard(card);
        System.out.println("The present value of your hand is " + presentHand.getValue());
    }

        /**
         * This method is used to clear the end
         * of a game.
         */
    public void end(){
        presentHand = new Hand();
    }

    /**
     * This is the getter method for available moves.
     * @return Set<Move.AVAILABLE_MOVES>
     */
    public Set<Move.AVAILABLE_MOVES> getAvailableMoves(){
        return presentHand.getStateManager().getMoves();
    }


}
