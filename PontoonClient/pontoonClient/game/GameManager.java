package game;

import cardEngine.Deck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameManager {

    List<Playable> players;
    Boolean hasStarted;
    Deck deck;
    Player bank;
    int rounds;

    public GameManager(List<Playable> player, int rounds){
        this.rounds = rounds;
        this.deck = new Deck();
        if(player == null)
            throw new IllegalArgumentException("The player is null");
        players = new ArrayList<>();
        players.addAll(player);
        hasStarted = false;
    }


    /**
    *This method manages the routine of the game.
    * It follows the stepwise procedure of the
    * game from the beginning to end.
     */
    public void initiateRoutine() throws IOException {
        Bank bank = new Bank();
        this.bank = bank;
        bank.makeBank();
        bank.dealOutCards(players);
        bank.dealOutCards(players);
        players.forEach(player -> {
            if (player.isBank)
                return;
            bank.engageMiniGame(player);

        });
        while(bank.getValue() <= 16){
            bank.dealSelf();
        }
        if(bank.getValue() > 21){
            players.forEach(player -> player.end());
            return;
        }
        chooseWinner();
        players.forEach(player -> player.end());
    }


    /**
     *This method calculates who the winner is and
     * send it out to all players.
     */
    public void chooseWinner(){
        List<Playable> drawList = new ArrayList<>();
        int high = 0;
        for (Playable player :
                players) {
            if(player.isBank)
                continue;
            if (player.getValue() > 21)
                continue;
            if (player.getValue() > high){
                drawList.clear();
                drawList.remove(0);
                drawList.add(player);
            }
            if (player.getValue() == high)
                drawList.add(player);
        }
        if(bank.getValue() >= high){
            System.out.println("The dealer is the winner");
            return;
        }
        if(drawList.size() == 1)
            System.out.println(drawList.get(0).getName() + " is the winner");
        else{
            for (Playable player :
                    drawList) {
                System.out.print(player.getName() + ", ");
            }
            System.out.println("are the winners");
        }
    }

}
