package com.pontoonClient.game;

import com.pontoonClient.server.Connector;

public class GameManager {

    CliAnnouncer announce;
    Connector connector;
    public GameManager(){
        startGame();
    }

    private void startGame() {
        announce.output.println("Welcome to Pont22n");
        connector.sendMessage("getMessages");
    }

    public void getGames{
        connector.sendMessage("getgroup");
    }


}
