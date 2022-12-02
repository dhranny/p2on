package com.pontoonServer.game;

import com.pontoonServer.cardEngine.Deck;

import javax.lang.model.type.NullType;
import java.util.ArrayList;
import java.util.List;

public class GameManager {

    List<Player> players;
    Boolean hasStarted;
    Deck deck;
    Player bank;

    public GameManager(Player player){
        this.deck = new Deck();
        if(player == null)
            throw new IllegalArgumentException("The player is null");
        players = new ArrayList<>();
        players.add(player);
        hasStarted = false;
        countDownToStart();;
    }

    private void countDownToStart(){
        Thread sleeper = new Thread(() -> {
            while(players.size() < 2){
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            start();
        });
        sleeper.start();
    }

    private void start(){
        hasStarted = true;
        PontoonHost.pendingGames.remove(this);

        for (Player player :
                players) {
            player.getConnection().sendMessage("announceYour game has started");
        }
        initiateRoutine();
    }

    private void initiateRoutine() {
        Player bank = players.get((int)(Math.random()* (players.size() - 1)));
        bank.makeBank();
        this.bank = bank;
        players.parallelStream().forEach(player -> {
            player.getName();
        });
        bank.dealOutCards(players);
        bank.dealOutCards(players);
        for (Player player :
                players) {
            if (player.isBank)
                continue;
            player.start();
            bank.engageMiniGame(player);
        }
        while(bank.getValue() <= 16){
            bank.dealSelf();
        }
        System.out.println("This place");
        if(bank.getValue() > 21){
            announceAll("announceThe bank is higher than 21, the bank lost.");
            return;
        }
        System.out.println("harem");
        chooseWinner();
    }

    public void announceAll(String message){
        for(Player player: players){
            player.getConnection().sendMessage(message);
        }
    }
    public boolean addPlayer(Player player){
        if(!hasStarted){
            players.add(player);
            return true;
        }
        return false;
    }
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
                drawList.add(player);
            }
            if (player.getValue() == high)
                drawList.add(player);
        }
        if(bank.getValue() >= high){
            drawList.clear();
            drawList.add(bank);
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
