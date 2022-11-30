package com.pontoonServer.server;

import com.pontoonServer.game.Player;
import com.pontoonServer.game.PontoonHost;

import java.io.*;
import java.net.Socket;

public class ConnectedPlayer extends Thread {
    Socket socket;
    BufferedReader inputStream;
    BufferedWriter outputStream;
    Player player;
    GsonSerializer gsonSerializer;

    ConnectedPlayer (Socket socket){
        System.out.println("A connection just came in");
        this.player = new Player(this);
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
                        player.getHands().notify();
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
            PontoonHost.newGame(player);
        }
        if (messagen.startsWith("getgroups")){
            if(PontoonHost.pendingGames.isEmpty()) {
                PontoonHost.newGame(player);
                sendMessage("announceYou have being added to a game, waiting for the game to start");
                return;
            }
            while(PontoonHost.getRandomGames().addPlayer(player) == false);
            sendMessage("announceYou have being added to a game, waiting for the game to start");
        }
        if(messagen.strip().startsWith("stake")){
            player.setStake(Integer.parseInt(messagen.strip().substring(5));
        }
    }
}