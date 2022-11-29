package com.pontoonServer.game;

import java.util.ArrayList;
import java.util.List;

public class PontoonHost {

    static List<GameManager> pendingGames = new ArrayList<>();
    static List<GameManager> runningGames = new ArrayList<>();

    static public void newGame(Player player){
        pendingGames.add(new GameManager(player));
    }
}
