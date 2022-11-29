package com.pontoonClient.server;

import java.io.*;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Connector {

    private BufferedReader inputStream;
    private BufferedWriter outputStream;
    private Scanner cliScanner;

    public Connector() throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost().getHostName(), 9900);
        sa
        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void sendMessage(String message){
        try {
            outputStream.write(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getResponse(){
        while(true){
            try {
                if(inputStream.ready()){
                    String message = inputStream.readLine();
                    if(message.strip().startsWith("groups")){
                        if(message.strip().startsWith("groupst")){
                            System.out.println(message.strip().substring(7));
                            cl
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
