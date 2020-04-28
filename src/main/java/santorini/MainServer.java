package santorini;

import java.io.IOException;

public class MainServer {
    public static void main(String[] args) {
        try {
            System.out.println("Starting server...");
            new Thread(new NetworkHandlerServer()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
