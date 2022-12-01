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
        Player bank = players.get((int)Math.random()* players.size());
        bank.makeBank();
        this.bank = bank;
        bank.dealOutCards(players);
        for (Player player :
                players) {
            player.getStake(player);
        }
        bank.dealOutCards(players);
        for (Player player :
                players) {
            if (player.isBank)
                continue;
            player.start();
            bank.engageMiniGame(player);
        }

        for (Player player :
                players) {
            bank.shareStake(player);
        }

    }

    public boolean addPlayer(Player player){
        if(!hasStarted){
            players.add(player);
            return true;
        }
        return false;
    }
}
