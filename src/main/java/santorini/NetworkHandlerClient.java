package santorini;

import santorini.model.Extraction;
import santorini.model.God;
import santorini.model.Mossa;
import santorini.model.Table;

import java.io.*;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class NetworkHandlerClient implements Runnable {
    private Socket server;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private View view;
    private Extraction e;
    private int[] players1;
    private int[] cards;

    /**
     * Initialize a new connection with a game server to join in a new game
     *
     * @param address server address
     * @param name    player name
     */
    public NetworkHandlerClient(String address, String name, View view) {
        this.view = view;
        try {
            server = new Socket(address, Parameters.PORT);
            outputStream = new ObjectOutputStream(server.getOutputStream());
            inputStream = new ObjectInputStream(server.getInputStream());
            outputStream.writeObject(name);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        listen();
    }

    /**
     * Creates a new loop thread ready for listen to commands
     * from game server
     */
    private void listen() {
        while (true) {
            try {
                Object o = inputStream.readObject();
                if (o instanceof ArrayList) {
                    e = new Extraction();
                    ArrayList<God> gods = (ArrayList<God>) o;
                    int k = players1[0];
                    gods = e.extractionGods(k);
                    /*Scelta della carta*/
                    view.chooseCards();
                    int a = cards[0];
                    God g = gods.get(a);
                    outputStream.writeObject(g);
                    outputStream.flush();
                    gods.remove(gods.get(a));
                } else if (o instanceof Table) {
                    Table t = (Table) o;
                    updateField(t);
                } else if (o instanceof Parameters.command) {
                    Parameters.command cmd = (Parameters.command) o;
                    switch (cmd) {
                        case BUILD:
                            /*Chiedi una nuova myBuilding*/
                            view.setNewBuild();
                            break;
                        case MOVE:
                            /*Chiedi una nuova myMovement*/
                            view.setNewMove();
                            break;
                        case SET_PLAYERS_NUMBER:
                            /*Chiedi il numero di giocatori*/
                            view.setNumeroGiocatori();
                            break;
                        case INITIALIZE_PAWNS:
                            /*Chiedi le coordinate iniziali delle pedine*/
                            view.setInitializePawn();
                            break;
                        case FAILED:
                            /*Invia errore al giocatore e resta subito pronto
                             * per una nuova richiesta di istruzioni dal server*/
                            view.setFailed();
                            break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates a new thread to update the playing field just sent from server
     *
     * @param table playing field just sent
     */
    private void updateField(Table table) {
        new Thread(() -> {
            /*Aggiorna il campo con quello appena arrivato dal server (table)
            Fallo in questo thread*/


        }).start();
    }

    public void setCard(int[] chooseCard) throws IOException {
        cards = chooseCard;
        outputStream.writeObject(chooseCard);
        outputStream.flush();
    }

    public int[] getCard() {
        return cards;
    }


    public void setPartecipanti(int[] players) throws IOException {
        players1 = players;
        outputStream.writeObject(players);
        outputStream.flush();
    }

    public int[] getPartecipanti() {
        return players1;
    }

    public void setCordinata(int[] coordinate) throws IOException {
        outputStream.writeObject(coordinate);
        outputStream.flush();
    }

    public void setMovementPawn(int[] coordinateMove) throws IOException {
        outputStream.writeObject(coordinateMove);
        outputStream.flush();
    }

    public void setBuildPawn(int[] positionBuild) throws IOException {
        outputStream.writeObject(positionBuild);
        outputStream.flush();
    }


}
