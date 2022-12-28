package com.pontoonServer;

import com.pontoonServer.cardEngine.Card;
import com.pontoonServer.server.Connector;
import com.pontoonServer.server.GsonSerializer;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        if(args.length == 1){
            try{
                int port = Integer.parseInt(args[0]);
                Connector connector = new Connector(port);
                return;
            }
            catch (NumberFormatException e){
                System.out.println("The port argument should be an integer");
            }
        }
        System.out.println("Welcome to the Pont22n server");
        Connector connector = new Connector();
    }
}
