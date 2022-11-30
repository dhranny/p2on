package com.pontoonClient.server;

import com.pontoonClient.game.GameManager;

import java.io.*;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Connector {

    private BufferedReader inputStream;
    private BufferedWriter outputStream;
    private BufferedReader cliScanner;
    GameManager gameManager;

    public Connector() throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost().getHostName(), 9900);
        cliScanner = new BufferedReader(new InputStreamReader(System.in));
        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        gameManager = new GameManager(this);
    }

    public void sendMessage(String message){
        try {
            outputStream.write(message + "\n");
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getResponse();

    }

    public void getResponse(){
        while(true){
            try {
                if(inputStream.ready()){
                    String message = inputStream.readLine();
                    manageResponse(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void manageResponse(String message){
        if(message.strip().startsWith("announce")){
            System.out.println(message.strip().substring(7));
            try {
                cliScanner.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(message.strip().startsWith("getstake")){
            System.out.println("Please input your stake within 100-200 pontoon credits");
            try {
                int stake = Integer.parseInt(cliScanner.readLine());
                if (stake < 100 || stake > 200)
                sendMessage("stake" + stake);
            }
            catch (IOException | NumberFormatException e){
                e.printStackTrace(System.out);
            }
        }
    }
}
