package santorini;

import java.io.IOException;

public class MainServer {
    public static void main(String[] args) {
        try {
            System.out.println("Starting server...");
            NetworkHandlerServer handlerServer = new NetworkHandlerServer();
            handlerServer.initGameConnections();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
