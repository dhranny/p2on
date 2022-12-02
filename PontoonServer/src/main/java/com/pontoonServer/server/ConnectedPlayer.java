package com.pontoonServer.server;

import com.pontoonServer.game.Player;
import com.pontoonServer.game.PontoonHost;

import java.io.*;
import java.net.Socket;

public class ConnectedPlayer extends Thread {
    Socket socket;
    BufferedReader inputStream;
    BufferedWriter outputStream;
    public Player presentPlayer;
    GsonSerializer gsonSerializer;

    ConnectedPlayer (Socket socket){
        System.out.println("A connection just came in");
        this.presentPlayer = new Player(this);
        this.socket = socket;
        gsonSerializer = new GsonSerializer();
    }
    @Override
    public void run() {
        super.run();
        try {
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            waitNewMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void sendMessage(String message){
        try {
            outputStream.write(message + "\n");
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void waitNewMessage(){
        Thread waiter = new Thread(() ->{
            while (true){
                try {
                    if(inputStream.ready()){
                        String message = readLine();
                        manageMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        waiter.start();
    }

    private String readLine() throws IOException {
        return inputStream.readLine();
    }

    private void manageMessage(String message){
        String messagen = message.strip();
        if(messagen.startsWith("newgame")){
            PontoonHost.newGame(presentPlayer);
        }
        if (messagen.startsWith("getgroups")){
            if(PontoonHost.pendingGames.isEmpty()) {
                PontoonHost.newGame(presentPlayer);
                sendMessage("announceYou have being added to a game, waiting for the game to start");
                return;
            }
            while(PontoonHost.getRandomGames().addPlayer(presentPlayer) == false);
            sendMessage("announceYou have being added to a game, waiting for the game to start");
        }

        if(messagen.strip().startsWith("value")){
            presentPlayer.setStake(Integer.parseInt(messagen.strip().substring(5)));
            synchronized (this){
                this.notifyAll();
            }
            System.out.println(presentPlayer.name +"'s value is " + Integer.parseInt(messagen.strip().substring(5)));
        }

        if(messagen.strip().startsWith("name")){
            presentPlayer.setName(messagen.strip().substring(4));
            synchronized (this){
                this.notifyAll();
            }
        }

        if(messagen.strip().startsWith("card")){
            presentPlayer.dealPresent(messagen);
            synchronized (this){
                this.notifyAll();
            }
        }

        if(messagen.strip().startsWith("cardreceived")){
            System.out.println("pp");

        }

        if(messagen.strip().startsWith("move")){
            presentPlayer.nextMove = gsonSerializer.fromMoveJson(messagen.strip().substring(4));
            synchronized (this){
                this.notifyAll();
            }
        }
    }
}