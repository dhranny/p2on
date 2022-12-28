package com.pontoonServer.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the class that manages creating
 * a channel for connections.
 * This class creates new connections.
 */
public class Connector {


    ServerSocket serverSocket;
    Set<ConnectedPlayer> connectedPlayers;

    /**
     * Constructor for default server port
     * @throws IOException
     */
    public Connector() throws IOException {
        serverSocket= new ServerSocket(9900);
        System.out.println("Connected to the default port");
        connectedPlayers = new HashSet<>();
        ServerAcceptor serverAcceptor = new ServerAcceptor();
        serverAcceptor.run();
    }

    /**
     * Connector for specified server port
     * @param port
     */
    public Connector(int port) {
        try{
            serverSocket= new ServerSocket(port);
            System.out.println("Connected to port " + port);
            connectedPlayers = new HashSet<>();
            ServerAcceptor serverAcceptor = new ServerAcceptor();
            serverAcceptor.run();
        }
        catch (IOException e){
            System.out.println("Invalid port number");
            System.exit(1);
        }
    }

    /**
     * This is an inner class that manages the acceptance
     * of new connections from the user.
     */
    private class ServerAcceptor extends Thread {
        @Override
        public void run() {
            super.run();
            while (true){
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


}
