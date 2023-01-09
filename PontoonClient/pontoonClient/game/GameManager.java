package com.pontoonServer.game;

import com.pontoonServer.cardEngine.Deck;
import java.util.ArrayList;
import java.util.List;

public class GameManager {

    List<Player> players;
    Boolean hasStarted;
    Deck deck;
    Player bank;
    int rounds;

    public GameManager(Player player, int rounds){
        this.rounds = rounds;
        this.deck = new Deck();
        if(player == null)
            throw new IllegalArgumentException("The player is null");
        players = new ArrayList<>();
        players.add(player);
        hasStarted = false;
    }


    /**
    *This method manages the routine of the game.
    * It follows the stepwise procedure of the
    * game from the beginning to end.
     */
    public void initiateRoutine() {
        Bank bank = new Bank();
        this.bank = bank;
        bank.makeBank();
        bank.dealOutCards(players);
        bank.dealOutCards(players);
        players.forEach(player -> {
            if (player.isBank)
                return;
            player.start();
            bank.engageMiniGame(player);

        });
        while(bank.getValue() <= 16){
            bank.dealSelf();
        }
        if(bank.getValue() > 21){
            announceAll("announceThe bank is higher than 21, the bank lost.");
            players.forEach(player -> player.end());
            return;
        }
        chooseWinner();
        players.forEach(player -> player.end());
    }

    /**
    *This method is used to announce a statement to the
    * clients playing this particular game.
     */
    public void announceAll(String message){
        for(Player player: players){
            if(bank == player){
                return;
            }
            player.getConnector().sendMessage(message);
        }
    }

    /**
     *This method calculates who the winner is and
     * send it out to all players.
     */
    public void chooseWinner(){
        List<Player> drawList = new ArrayList<>();
        int high = 0;
        for (Player player :
                players) {
            if(player.isBank)
                continue;
            if (player.getValue() > 21)
                continue;
            if (player.getValue() > high){
                drawList.clear();
                drawList.remove(0);
                drawList.add(player);
            }
            if (player.getValue() == high)
                drawList.add(player);
        }
        if(bank.getValue() >= high){
            System.out.println("The dealer is the winner");
            return;
        }
        if(drawList.size() == 1)
            System.out.println(drawList.get(0).name + " is the winner");
        else{
            for (Player player :
                    drawList) {
                System.out.print(player.name + ", ");
            }
            System.out.println("are the winners");
        }
    }

}
