package santorini;

import santorini.model.Gamer;
import santorini.model.God;
import santorini.model.Table;

import java.util.ArrayList;

public class Game implements Runnable {
    private ArrayList<Gamer> giocatori = new ArrayList<Gamer>();
    /*god cards sono quelle attive nella partita
     * passa una copia di questa lista al turno
     * rifai la classe extraction con la shuffle della collection*/
    private ArrayList<God> godCards = new ArrayList<God>();
    private Table table = new Table();
    //private NetworkHandlerServer handler;

    /**
     * Game initialization
     *
     * @param gamers player list.  Number handled by connection manager
     */
    // public Game(ArrayList<Gamer> gamers, NetworkHandlerServer networkHandlerServer) {
    //  giocatori = gamers;
    //  this.handler = networkHandlerServer;
    //}

    /*Inizializzare con estrazione le carte dininità*/


    /**
     * Generates a new game in a new Thread
     */
    public void run() {
        while (true) {
            for (Gamer g : giocatori) {
                ArrayList<God> gods = new ArrayList<God>();
                gods.addAll(godCards);
                Thread t = new Thread(new Turno(gods, g, table));
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (g.isWinner()) {
                    //handle winning
                }
            }
        }
    }
}
