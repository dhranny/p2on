package com.pontoonClient.game;

import com.pontoonClient.server.Connector;

import java.io.IOException;

public class GameManager {

    CliAnnouncer announce;
    Connector connector;
    public GameManager(Connector connector){
        announce = new CliAnnouncer();
        this.connector = connector;
        startGame();
    }

    private void startGame() {
        announce.output.println("Welcome to Pont22n");
        connector.sendMessage("getgroups");
    }

    public void getGames(){
        connector.sendMessage("getgroup");
    }


}
