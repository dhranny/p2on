import game.PontoonHost;

import java.io.IOException;

public class Main {
    static PontoonHost pontoonHost;

    static {
        try {
            pontoonHost = new PontoonHost();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        pontoonHost.startGame();
    }
}
