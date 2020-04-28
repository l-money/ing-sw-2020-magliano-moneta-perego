package santorini;

import santorini.model.Gamer;
import santorini.model.God;
import santorini.model.Mossa;
import santorini.model.Mossa.Action;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class NetworkHandlerServer implements Runnable {
    private ServerSocket serverSocket;
    private ArrayList<Gamer> players = new ArrayList<Gamer>();
    private Game game;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public NetworkHandlerServer() throws IOException {
        serverSocket = new ServerSocket(Parameters.PORT);
    }

    /**
     * Starts listening for clients
     * after 30*1000 milliseconds starts game
     */
    public void run() {
        initGameConnections();
    }

    /**
     * Listens to connections from 2 or 3 clients
     * Any gamer object will be created with
     * - A reference to its socket
     * - A name
     * - A progress id (created from i)
     */
    private void initGameConnections() {
        try {
            int i = 0;
            Socket s = serverSocket.accept();
            inputStream = new ObjectInputStream(s.getInputStream());
            outputStream = new ObjectOutputStream(s.getOutputStream());
            players.add(new Gamer(s, inputStream.readObject().toString(), i, inputStream, outputStream));
            i++;
            outputStream.writeObject(Parameters.command.SET_PLAYERS_NUMBER);
            outputStream.flush();
            int max = Integer.parseInt((String) inputStream.readObject());
            for (; i < max; i++) {
                s = serverSocket.accept();
                inputStream = new ObjectInputStream(s.getInputStream());
                outputStream = new ObjectOutputStream(s.getOutputStream());
                players.add(new Gamer(s, inputStream.readObject().toString(), i, inputStream, outputStream));
            }
            startGame();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Starts a new game and becomes handler for socket and
     * network data transmissions
     */
    public void startGame() {
        if (players.size() == 2 || players.size() == 3) {
            game = new Game(players, this);
            new Thread(game).start();
        }
    }

    /**
     * Sends field status to a specific player
     *
     * @param gamer player to send field
     */
    public void updateField(Gamer gamer) {
        outputStream = gamer.getOutputStream();
        try {
            outputStream.writeObject(game.getTable());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public God chooseCard(ArrayList<God> cards, Gamer player) throws IOException, ClassNotFoundException {
        outputStream = player.getOutputStream();
        outputStream.writeObject(cards);
        outputStream.flush();
        inputStream = player.getInputStream();
        God g = (God) inputStream.readObject();
        return g;
    }

    public Mossa richiediMossa(Action tipo, Gamer gamer) throws IOException, ClassNotFoundException {
        outputStream = gamer.getOutputStream();
        Parameters.command command = null;
        switch (tipo) {
            case MOVE:
                command = Parameters.command.MOVE;
                break;
            case BUILD:
                command = Parameters.command.BUILD;
                break;
            /**default:
             command = Parameters.command.FAILED;
             break;*/
        }
        outputStream.writeObject(command);
        outputStream.flush();
        inputStream = gamer.getInputStream();
        Mossa mossa = (Mossa) inputStream.readObject();
        return mossa;
    }

    public void sendFailed(Gamer gamer) {
        outputStream = gamer.getOutputStream();
        try {
            outputStream.writeObject(Parameters.command.FAILED);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
