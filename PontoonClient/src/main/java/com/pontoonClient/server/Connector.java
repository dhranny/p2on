package com.pontoonClient.server;

import com.pontoonClient.cardEngine.Card;
import com.pontoonClient.cardEngine.Hand;
import com.pontoonClient.game.GameManager;
import com.pontoonClient.game.GsonSerializer;
import com.pontoonClient.game.Move;

import java.io.*;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLOutput;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

public class Connector {

    private BufferedReader inputStream;
    private BufferedWriter outputStream;
    private BufferedReader cliScanner;
    GameManager gameManager;
    GsonSerializer gson;

    public Connector() throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost().getHostName(), 9900);
        cliScanner = new BufferedReader(new InputStreamReader(System.in));
        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        gameManager = new GameManager(this);
        gson = new GsonSerializer();
        getResponse();
    }

    public void sendMessage(String message){
        try {
            outputStream.write(message + "\n");
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getResponse(){
        Thread responseWaiter = new Thread(() -> {while(true){
            try {
                if(inputStream.ready()){
                    String message = inputStream.readLine();
                    manageResponse(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }});
        responseWaiter.start();
    }

    private void manageResponse(String message){
        if(message.strip().startsWith("announce")){
            System.out.println(message.strip().substring(8));
        }
        if(message.strip().startsWith("getstake")){
            System.out.println("Please input your stake within 100-200 pontoon credits");
            try {
                int stake = Integer.parseInt(cliScanner.readLine());
                System.out.println(stake);
                if (stake > 100 && stake < 200) {
                    sendMessage("stake" + stake);
                    gameManager.initialStake = stake;
                }
            }
            catch (IOException | NumberFormatException e){
                e.printStackTrace(System.out);
            }
        }
        if (message.strip().equalsIgnoreCase("dealcard")){
            gameManager.dealCard();
        }

        if (message.strip().equalsIgnoreCase("getmove")){
            System.out.println("These are your available moves");
            Set<Move.AVAILABLE_MOVES> moves = gameManager.getAvailableMoves();
            for (Move.AVAILABLE_MOVES move:
                 moves) {
                System.out.print("  " + move.toString() + "  ");
            }
            System.out.println();
            try {
                String cliResponse = cliScanner.readLine();
                cliResponse = cliResponse.toLowerCase().strip();
                if(cliResponse.equals("twist") && moves.contains(Move.AVAILABLE_MOVES.TWIST)){
                    Move move = new Move();
                    move.setMove(Move.AVAILABLE_MOVES.TWIST);
                    sendMessage("move" + gson.createMessage(move));
                }

                if(cliResponse.equals("buy card") || cliResponse.equals("buy_card") || cliResponse.equals("buycard") && moves.contains(Move.AVAILABLE_MOVES.BUY_CARD)){
                    Move move = new Move();
                    move.setMove(Move.AVAILABLE_MOVES.BUY_CARD);
                    move.setAdditionalStake(gameManager.initialStake);
                    System.out.println("You requested for a paid card");
                    sendMessage("move" + gson.createMessage(move));
                }

                if(cliResponse.equals("stick") && moves.contains(Move.AVAILABLE_MOVES.STICK)){
                    Move move = new Move();
                    move.setMove(Move.AVAILABLE_MOVES.STICK);
                    System.out.println("You requested for a free card");
                    sendMessage("move" + gson.createMessage(move));
                }

                if(cliResponse.equals("split") && moves.contains(Move.AVAILABLE_MOVES.SPLIT)){
                    Move move = new Move();
                    move.setMove(Move.AVAILABLE_MOVES.SPLIT);
                    Hand newHand = gameManager.presentHand.split();
                    gameManager.hands.add(newHand);
                    System.out.println("A new hand has been created");
                    sendMessage("move" + gson.createMessage(move));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        if (message.strip().startsWith("card")){

            System.out.println("You received this card " + message.substring(4));
            Card card = gson.fromCardJson(message.substring(4));
            gameManager.placeCard(card);
        }
    }
}
