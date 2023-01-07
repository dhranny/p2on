package com.pontoonServer.server;

import com.pontoonServer.game.GameManager;
import com.pontoonServer.game.Player;
import com.pontoonServer.game.PontoonHost;

import java.io.*;
import java.net.Socket;

/**
 * This is the class for a connection.
 * This class manages the connection
 * that a client makes with the sever
 */
public class ConnectedPlayer extends Thread implements Connectable{
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

    /**
     * This is the starting point when the connectedplayer
     * thread is started. The method puts all things in place
     * server connection for a player.
     */
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


    /**
     * This method is used to send messages to the client.
     * The method receives a string message relay it to
     * the client owning the connection.
     * @param message
     */
    public void sendMessage(String message){
        try {
            outputStream.write(message + "\n");
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method creates a new thread that waits for new messages
     * from the client that owns the connection.
     */
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

    /**
     * A wrapper function for inputStream.readLine
     * @return
     * @throws IOException
     */
    private String readLine() throws IOException {
        return inputStream.readLine();
    }

    /**
     * This method is used to manage new messages from the
     * client. The method does an assigned task based on
     * message received from the client.
     * @param message
     */
    public void manageMessage(String message){
        String messagen = message.strip();
        if (messagen.startsWith("join")){
            try{
                if(PontoonHost.getRandomGames().addPlayer(presentPlayer) == false)
                    sendMessage("join_fail");
            }
            catch (IndexOutOfBoundsException e){
                sendMessage("join_fail");
            }
            sendMessage("announceYou have been added to a game, waiting for the game to start");
        }
        if(messagen.startsWith("round")) {
            PontoonHost.newGame(presentPlayer, Integer.parseInt(messagen.substring(5)));
            sendMessage("announceYou have been added to a game, waiting for the game to start");
            return;
        }


        if(messagen.strip().startsWith("name")){
            presentPlayer.setName(messagen.strip().substring(4));
            synchronized (this){
                this.notifyAll();
            }
        }

        if(messagen.strip().startsWith("cardreceived")){
            System.out.println("pp");

        }

        if(messagen.strip().startsWith("move")){
            presentPlayer.nextMove = gsonSerializer.fromMoveJson(messagen.strip().substring(4));
            System.out.println(messagen);
            synchronized (this){
                this.notifyAll();
            }
        }
    }
}