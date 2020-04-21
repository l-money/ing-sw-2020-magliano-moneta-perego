package santorini;

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

    /**
     * Initialize a new connection with a game server to join in a new game
     *
     * @param address server address
     * @param name    player name
     */
    public NetworkHandlerClient(String address, String name) {
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
                    ArrayList<God> gods = (ArrayList<God>) o;
                    /*Scelta della carta*/
                    int card = 0;
                    int k = chooseCard(card);
                    God g = gods.get(k);
                    outputStream.writeObject(g);
                    outputStream.flush();
                } else if (o instanceof Table) {
                    Table t = (Table) o;
                    updateField(t);
                } else if (o instanceof Parameters.command) {
                    Parameters.command cmd = (Parameters.command) o;
                    switch (cmd) {
                        case BUILD:
                            /*Chiedi una nuova myBuilding*/
                            int bC = 0;
                            int c = 0;
                            int d = 0;

                            int build = insertLevel(bC);
                            int row2 = insertRow(c);
                            int column2 = insertColumn(d);
                            outputStream.writeObject(build);
                            outputStream.writeObject(row2);
                            outputStream.writeObject(column2);
                            break;
                        case MOVE:
                            /*Chiedi una nuova myMovement*/
                            int cP = 0;
                            int a = 0; //riga
                            int b = 0; //colonna

                            int pawn = choosePawn(cP);
                            int row1 = insertRow(a);
                            int column1 = insertColumn(b);
                            outputStream.writeObject(pawn);
                            outputStream.writeObject(row1);
                            outputStream.writeObject(column1);
                            break;
                        case SET_PLAYERS_NUMBER:
                            /*Chiedi il numero di giocatori*/
                            int n = 0;
                            int number = numberPlayer(n);
                            outputStream.writeObject(number);
                            outputStream.flush();
                            break;
                        case INITIALIZE_PAWNS:
                            /*Chiedi le coordinate iniziali delle pedine*/
                            int x = 0;
                            int y = 0;
                            int row = insertRow(x);
                            int column = insertColumn(y);
                            outputStream.writeObject(row);
                            outputStream.writeObject(column);
                            break;
                        case FAILED:
                            /*Invia errore al giocatore e resta subito pronto
                             * per una nuova richiesta di istruzioni dal server*/
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

    /**
     * Method that take the user's input level
     *
     * @param l is the level that user inputs
     * @return the level that the user wants to build
     */
    private int insertLevel(int l) {
        int livello = 0;
        System.out.println("Che livello vuoi costruire?");
        Scanner input = new Scanner(System.in);
        l = input.nextInt();

        if (l == 1 | l == 2 | l == 3 | l == 4) {
            livello = l;
        } else {
            System.out.println("Livello inserito non corretto");
        }
        return livello;
    }


    //creo una classe che mi faccia scegliere una carta e mi restituisce il valore immesso
    //pensiamo che la scelta avvenga attraverso 3 carte quindi

    /**
     * Method where user can choose the card
     *
     * @param i is the card that user can choose
     * @return the card that user choose
     */
    private int chooseCard(int i) {
        int cartaScelta = 0;
        System.out.println("Inserisci la carta che vuoi scegliere: ");
        Scanner input = new Scanner(System.in);
        i = input.nextInt();

        if (i == 1 | i == 2 | i == 3) {
            cartaScelta = i;
        } else {
            System.out.println("The integer is not correct");
        }
        return cartaScelta;
    }

    //creo una classe nuova mossa che mi permette di scegliere la pedina che voglio utilizzare
    //per la mossa

    /**
     * Method where the user can choose pawn to position at start or to move to near cell
     *
     * @param p is the pawn that user want to use to position at start or to move
     * @return the pawn number that user want to position at start or to move
     */
    private int choosePawn(int p) {
        int pedina = 0;
        System.out.println("Che pedina vuoi scegliere? ");
        Scanner input = new Scanner(System.in);
        p = input.nextInt();

        if (p == 1 | p == 2) {
            pedina = p;
        } else {
            System.out.println("Pedina non scelta. Numero errato.");
        }
        return pedina;

    }


    //creo una classe inserimento di giocatori
    //ritornerà un intero che è il numero dei giocatori che partecipano

    /**
     * Method that ask to one user how many players want to play
     *
     * @param a is the number that user insert to start playing
     * @return the number of players
     */
    private int numberPlayer(int a) {
        int numeroGiocatori = 0;
        System.out.println("Numero di giocatori partecipanti: ");
        Scanner input = new Scanner(System.in);
        a = input.nextInt();

        if (a == 2 | a == 3) {
            numeroGiocatori = a;
        } else {
            System.out.println("Errore inserimento");
        }
        return numeroGiocatori;
    }

    /*creo una classe che permette all'utente di inserire le cordinate sia di posizionamento
    della pedina sia di scelta della casella per la mossa*/

    /**
     * Method where user insert the cell's row where want to move or position
     *
     * @param i is the row that user want to insert
     * @return the row insert from user
     */

    //metodo riga
    private int insertRow(int i) {
        int riga = 0;
        System.out.println("Inserisci riga: ");
        Scanner input = new Scanner(System.in);
        i = input.nextInt();

        if (i >= 0 && i <= 4) {
            riga = i;
        } else {
            System.out.println("Errore inserimento");
        }
        return riga;
    }

    /**
     * Method where user insert the cell's column where want to move or position
     *
     * @param j is the column that user want to insert
     * @return the column insert from user
     */
    //metodo colonna
    private int insertColumn(int j) {
        int colonna = 0;
        System.out.println("Inserisci colonna: ");
        Scanner input = new Scanner(System.in);
        j = input.nextInt();

        if (j >= 0 && j <= 4) {
            colonna = j;
        } else {
            System.out.println("Errore inserimento");
        }
        return colonna;
    }


}
