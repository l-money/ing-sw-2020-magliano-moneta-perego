package santorini;

import santorini.model.God;
import santorini.model.Mossa;
import santorini.model.Table;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkHandlerClient implements Runnable {
    private Socket server;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private View view;

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
            int id = Integer.parseInt(inputStream.readObject().toString());
            view.setID(id);
        } catch (IOException | ClassNotFoundException e) {
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
                    ArrayList<God> gods = (ArrayList<God>) o;
                    /*Scelta della carta*/
                    view.chooseCards(gods);
                } else if (o instanceof Table) {
                    Table t = (Table) o;
                    updateField(t);
                } else if (o instanceof Parameters.command) {
                    Parameters.command cmd = (Parameters.command) o;
                    switch (cmd) {
                        case BUILD:
                            /*Chiedi una nuova myBuilding*/
                            new Thread(() -> view.setNewBuild()).start();
                            break;
                        case MOVE:
                            /*Chiedi una nuova myMovement*/
                            new Thread(() -> view.setNewMove()).start();
                            break;
                        case SET_PLAYERS_NUMBER:
                            /*Chiedi il numero di giocatori*/
                            view.setNumeroGiocatori();
                            break;
                        case INITIALIZE_PAWNS:
                            /*Chiedi le coordinate iniziali delle pedine*/
                            new Thread(() -> view.setInitializePawn()).start();
                            break;
                        case FAILED:
                            /*Invia errore al giocatore e resta subito pronto
                             * per una nuova richiesta di istruzioni dal server*/
                            new Thread(() -> view.setFailed()).start();
                            break;
                        default:
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
            view.printTable(table);
        }).start();
    }


    /**
     * Sends choosen god card to server
     *
     * @param chooseCard choosen card
     * @throws IOException
     */
    public void setCard(God chooseCard) throws IOException {
        outputStream.writeObject(chooseCard);
        outputStream.flush();
    }

    /**
     * Sends number of players to server
     *
     * @param players number of players
     * @throws IOException
     */
    public void setPartecipanti(int players) throws IOException {
        outputStream.writeObject(players + "");
        outputStream.flush();
    }

    /**
     * Sends initial coordinates of player pawns to server
     *
     * @param coordinate initial coordinates in format x1,y1,x2,y2
     * @throws IOException
     */
    public void initializePawns(String coordinate) throws IOException {
        outputStream.writeObject(coordinate);
        outputStream.flush();
    }

    /**
     * Sends a new move to server
     *
     * @param move
     * @throws IOException
     */
    public void setMovementPawn(Mossa move) throws IOException {
        outputStream.reset();
        outputStream.writeObject(move);
        outputStream.flush();
    }

    /**
     * Sends a new move to server
     *
     * @param mossa
     * @throws IOException
     */
    public void setBuildPawn(Mossa mossa) throws IOException {
        outputStream.reset();
        outputStream.writeObject(mossa);
        outputStream.flush();
    }


}