package com.pontoonClient.game;

import com.pontoonClient.cardEngine.Hand;

import java.io.PrintStream;

public class CliAnnouncer {

    PrintStream output;

    public CliAnnouncer(){
        this.output = System.out;
    }

    public CliAnnouncer(PrintStream output){
        this.output = System.out;
    }

    public void newGame(){
        output.println("You created a new game");
    }

    public void joinedGame(){
        output.println("You joined a new game");
    }

    public void announceState(Hand.HAND_STATE state){
        String stateStr = state.toString().toLowerCase().replace('_', ' ');
        output.println("The state of your hand is " + stateStr);
    }

    public void announceMove(Move.AVAILABLE_MOVES move){
        output.println("You asked for a " + move.toString());
    }

    public void loseMoney(int value){
        output.println("You lost " + value +" to the bank");
    }

    public void madeMoney(int value){
        output.println("You made " + value +" from the bank");
    }

}