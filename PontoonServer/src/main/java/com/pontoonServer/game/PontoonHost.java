package com.pontoonServer.game;

import java.util.ArrayList;
import java.util.List;

public class PontoonHost {

    static public List<GameManager> pendingGames = new ArrayList<>();
    static List<GameManager> runningGames = new ArrayList<>();

    /**
     * This method is used to create a new game
     * @param player
     */
    static public void newGame(Player player, int rounds){
        pendingGames.add(new GameManager(player, rounds));
    }

    /**
     * This method is used to get a random game
     * that a client can join.
     * @return
     */
    static public GameManager getRandomGames(){
        if (pendingGames.size() == 0)
            return null;
        return pendingGames.get((int)(Math.random()* pendingGames.size()));
    }


}
