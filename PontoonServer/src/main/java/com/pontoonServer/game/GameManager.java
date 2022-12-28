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
        countDownToStart();;
    }

    /**
    *This method is meant to create a
    * thread that waits for one minute
    * and then start the game.
     */
    private void countDownToStart(){
        Thread sleeper = new Thread(() -> {
            while(players.size() < 2){
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < rounds; i++) {
                start();
                announceAll("announceThe game has ended");
            }
        });
        sleeper.start();
    }

    /**
    *This method is meant to initiate the game.
    * Is sets hasStarted to true and initiate the
    * game routine.
     */
    private void start(){
        hasStarted = true;
        PontoonHost.pendingGames.remove(this);

        for (Player player :
                players) {
            player.getConnector().sendMessage("announceYour game have started");
        }
        initiateRoutine();
    }

    /**
    *This method manages the routine of the game.
    * It follows the stepwise procedure of the
    * game from the beginning to end.
     */
    private void initiateRoutine() {
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
    *This method is used to add a new player to the
    * before the game starts.
     */
    public boolean addPlayer(Player player){
        if(!hasStarted && players.size() < 4){
            System.out.println(players.size());
            players.add(player);
            return true;
        }
        return false;
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
            announceAll("announceThe dealer is the winner");
            return;
        }
        if(drawList.size() == 1)
            announceAll("announce" + drawList.get(0).name + " is the winner");
        else{
            for (Player player :
                    drawList) {
                System.out.print(player.name + ", ");
            }
            System.out.println("are the winners");
        }
    }

}
