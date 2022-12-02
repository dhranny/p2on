package com.pontoonClient.server;

import com.pontoonClient.cardEngine.Card;
import com.pontoonClient.game.GameManager;
import com.pontoonClient.game.GsonSerializer;
import com.pontoonClient.game.Move;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
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

        if(message.strip().startsWith("getvalue")){
            sendMessage("value" + gameManager.presentHand.getValue());
        }

        if(message.strip().startsWith("getname")){
            System.out.println("Please input your name");
            String name = null ;
            try {
                name = cliScanner.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sendMessage("name" + name);
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
                if(cliResponse.equals("deal") && moves.contains(Move.AVAILABLE_MOVES.DEAL)){
                    Move move = new Move();
                    move.setMove(Move.AVAILABLE_MOVES.DEAL);
                    sendMessage("move" + gson.createMessage(move));
                }

                if(cliResponse.equals("hold") && moves.contains(Move.AVAILABLE_MOVES.HOLD)){
                    tellServer();
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

    public void tellServer(){
        Move move = new Move();
        move.setMove(Move.AVAILABLE_MOVES.HOLD);
        sendMessage("move" + gson.createMessage(move));
    }
}
