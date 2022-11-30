package com.pontoonServer.game;

import java.util.ArrayList;
import java.util.List;

public class PontoonHost {

    static public List<GameManager> pendingGames = new ArrayList<>();
    static List<GameManager> runningGames = new ArrayList<>();

    static public void newGame(Player player){
        pendingGames.add(new GameManager(player));
    }

    static public GameManager getRandomGames(){
        return pendingGames.get((int)Math.random()* pendingGames.size());
    }


}
