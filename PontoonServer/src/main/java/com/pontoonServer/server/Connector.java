package com.pontoonServer.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Connector {


    ServerSocket serverSocket;
    Set<ConnectedPlayer> connectedPlayers;
    public Connector() throws IOException {
        serverSocket= new ServerSocket(9900);
        connectedPlayers = new HashSet<>();
        ServerAcceptor serverAcceptor = new ServerAcceptor();
        serverAcceptor.run();
    }
    public static void createConnection(){

    }

    private class ServerAcceptor extends Thread {
        @Override
        public void run() {
            super.run();

            Socket socket;
            try {
                socket = serverSocket.accept();
                ConnectedPlayer cPlayer = new ConnectedPlayer(socket);
                cPlayer.start();
                connectedPlayers.add(cPlayer);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


}
