package com.pontoonClient;

import com.pontoonClient.server.Connector;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        if(args.length == 2){
            String host = args[0];
            int port = Integer.parseInt(args[1]);
            Connector connector = new Connector(host, port);
            return;
        }
        Connector connector = new Connector();
    }
}
