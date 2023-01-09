import pontoonClient.server.ConsoleConnector;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Playable
        System.out.println("Welcome to the pontoon game");
        System.out.println("Please specify the name of the player");
        System.out.println("Reply ADD to add new player or CONTINUE");
        pontoonClient.server.ConsoleConnector consoleConnector = new ConsoleConnector();
        String line = consoleConnector.getCliScanner().readLine();

    }
}
