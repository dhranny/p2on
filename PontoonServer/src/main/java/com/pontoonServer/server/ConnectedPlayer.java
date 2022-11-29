package com.pontoonServer.server;

import com.pontoonServer.game.Player;
import com.pontoonServer.game.PontoonHost;

import java.io.*;
import java.net.Socket;

class ConnectedPlayer extends Thread {
    Socket socket;
    BufferedReader inputStream;
    BufferedWriter outputStream;
    Player player;
    ConnectedPlayer (Socket socket){
        System.out.println("A connection just came in");
        this.player = new Player();
        this.socket = socket;
    }
    @Override
    public void run() {
        super.run();
        try {
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println(inputStream.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitNewMessage(){
        Thread waiter = new Thread(() ->{
            while (true){
                try {
                    if(inputStream.ready()){
                        String message = inputStream.readLine();
                        manageMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void manageMessage(String message){
        String messagen = message.strip();
        if(messagen.startsWith("newgame")){
            PontoonHost.newGame(player);
        }
    }
}