package com.pontoonServer.game;

import com.pontoonServer.cardEngine.Hand;
import com.pontoonServer.server.ConnectedPlayer;

import java.util.ArrayList;
import java.util.List;

public class Player {

    List<Hand> hands;
    int value;
    public String name;
    public boolean isBank;
    Player presentEngagement;
    public Move nextMove;

    public List<Hand> getHands() {
        return hands;
    }

    public ConnectedPlayer getConnector() {
        return connection;
    }

    private ConnectedPlayer connection;

    public Player(ConnectedPlayer connection){
        hands = new ArrayList<>();
        hands.add(new Hand());
        this.connection = connection;
    }

    public Player(){
        hands = new ArrayList<>();
        hands.add(new Hand());
    }



    /**
     * This method deals out a card to each
     * player given as a parameter.
     * @param players
     */


    /**
     * This method set up a player to
     * start a game.
     */
    public void start(){
        connection.presentPlayer = this;
    }


    /**
     * This is the getter method for value
     * @return int
     */
    public int getValue(){
        return value;
    }

    /**
     * This method is used to request for
     * the name of the player.
     * @return
     */
    public String getName(){
        connection.sendMessage("getname");
        synchronized (connection){
            try {
                connection.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return name;
    }



    /**
     * This method is used to get the next
     * move from the player
     * @param player
     * @return
     */
    protected Move getNextMove(Player player) {
        player.connection.sendMessage("getmove");
        synchronized (player.connection){
            try {
                player.connection.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return presentEngagement.nextMove;
    }

    /**
     * This is the setter method for name parameter.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    public void end() {
        value = 0;
        connection.sendMessage("end");
    }

}
