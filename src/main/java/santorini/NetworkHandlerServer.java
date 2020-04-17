/**package santorini;

import santorini.model.Gamer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class NetworkHandlerServer implements Runnable {
    private ServerSocket serverSocket;
    private final int PORT = 3467;
    private ArrayList<Gamer> players = new ArrayList<Gamer>();
    private Game game;

    public NetworkHandlerServer() throws IOException {
        serverSocket = new ServerSocket(PORT);
    }

    /**
     * Starts listening for clients
     * after 30*1000 milliseconds starts game
     */

/**
 public void run() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                startGame();
            }
        }, 30 * 1000);
        initGameConnections();
    }

    /**
     * Listens to connections from 2 or 3 clients
     * Any gamer object will be created with
     * - A reference to its socket
     * - A name
     * - A progress id (created from i)
     */

/**
 private void initGameConnections() {
        for (int i = 0; i < 3; i++) {
            try {
                Socket s = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                players.add(new Gamer(s, ois.readObject().toString(), i));
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    /**
     * Starts a new game and becomes handler for socket and
     * network data transmissions
     */

/**
 public void startGame() {
 if (players.size() == 2 || players.size() == 3) {
 game = new Game(players, this);
 }
 }

 /**
 * Sends field status to all players as Object Stream
 */

/**
 public void updateField() {

 }
}
 */