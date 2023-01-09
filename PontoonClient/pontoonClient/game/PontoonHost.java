package game;

import server.ConsoleConnector;
import game.GameManager;
import game.Playable;
import game.Player;
import server.ConsoleConnector;
import server.ConsoleConnector;

import java.io.IOException;
import java.util.ArrayList;

import java.util.ArrayList;

public class PontoonHost {


    ConsoleConnector consoleConnector = new ConsoleConnector();
    ArrayList<Playable> players;

    public PontoonHost() throws IOException {
        players = new ArrayList<>();
    }

    public void startGame() throws IOException {
        System.out.println("Welcome to the pontoon game");
        receiveNames();
        int rounds = inputRounds();
        GameManager gameManager = new GameManager(players, rounds);
        gameManager.initiateRoutine();
    }

    private void receiveNames() throws IOException {
        String res = inputName();
        while(!res.equalsIgnoreCase("continue")){
            res = inputName();
        }
    }
    private String inputName() throws IOException {
        System.out.println("Please specify the name of the player");
        String line = consoleConnector.getCliScanner().readLine();
        Playable player = new Player(line);
        players.add(player);
        System.out.println("Reply ADD to add new player or CONTINUE");
        String res = consoleConnector.getCliScanner().readLine();
        while(!res.equalsIgnoreCase("add") && !res.equalsIgnoreCase("continue")){
            System.out.println("Reply ADD to add new player or CONTINUE");
            res = consoleConnector.getCliScanner().readLine();
        }
        System.out.println();
        return res;
    }

    private int inputRounds() throws IOException {
        System.out.println("Please specify the number of rounds you will like to play.");
        String roundsString = consoleConnector.getCliScanner().readLine();
        int rounds = -1;
        //TODO: add robustness to this rounds stuff
        while(rounds != -1){
            try {
                rounds = Integer.parseInt(roundsString);
            }
            catch (NumberFormatException e){
                System.out.println("Please input an integer");
            }
        }
        return rounds;
    }
}