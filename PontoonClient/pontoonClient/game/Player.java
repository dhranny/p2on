package game;

import cardEngine.Hand;
import server.ConsoleConnector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Player implements Playable{

    Hand hand;
    int value;
    public String name;
    public boolean isBank;
    public Move nextMove;
    private ConsoleConnector connector = new ConsoleConnector();

    public Hand getHand() {
        return hand;
    }

    public Player(String name) throws IOException {
        this.name = name;
        hand = new Hand();
    }

    public Player() throws IOException {

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
        return name;
    }

    public void end(){
        //TODO: do what is neccessary for player to end
    }



    /**
     * This method is used to get the next
     * move from the player
     * @return
     */
    public Move getNextMove() {
        System.out.println("These are your available moves");
        Set<Move.AVAILABLE_MOVES> moves = hand.stateManager.getMoves();
        for (Move.AVAILABLE_MOVES move:
                moves) {
            System.out.print("  " + move.toString() + "  ");
        }
        System.out.println();
        try {
            String cliResponse = connector.getCliScanner().readLine();
            cliResponse = cliResponse.toLowerCase().strip();
            if(cliResponse.equals("deal") && moves.contains(Move.AVAILABLE_MOVES.DEAL)){
                Move move = new Move();
                move.setMove(Move.AVAILABLE_MOVES.DEAL);
                return move;
            }
            if(cliResponse.equals("hold") && moves.contains(Move.AVAILABLE_MOVES.HOLD)){
                Move move = new Move();
                move.setMove(Move.AVAILABLE_MOVES.HOLD);
                return move;
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
