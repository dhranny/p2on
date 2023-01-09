package server;

import game.GsonSerializer;
import game.GameManager;
import java.io.*;

/**
 * This is the class that manages the connection of
 * this client to the server. It manages the exchange
 * of data.
 */
public class ConsoleConnector {

    private BufferedReader inputStream;
    private BufferedReader cliScanner;
    GameManager gameManager;
    GsonSerializer gson;

    public ConsoleConnector() throws IOException {
        cliScanner = new BufferedReader(new InputStreamReader(System.in));
        //gameManager = new GameManager(this);
    }


    private BufferedWriter outputStream;

    public BufferedReader getCliScanner() {
        return cliScanner;
    }

    /**
     * This method creates a new thread that waits
     * for a new message to arrive from the server
     */
    public void getResponse() {
        Thread responseWaiter = new Thread(() -> {
            while (true) {
                try {
                    if (inputStream.ready()) {
                        String message = inputStream.readLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        responseWaiter.start();
    }
}

