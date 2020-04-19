package santorini;

import santorini.model.Extraction;
import santorini.model.Gamer;
import santorini.model.God;
import santorini.model.Table;

import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;

public class Game implements Runnable {
    private ArrayList<Gamer> giocatori;
    /*god cards sono quelle attive nella partita
     * passa una copia di questa lista al turno
     * rifai la classe extraction con la shuffle della collection*/
    private ArrayList<God> godCards;
    private Table table = new Table();
    private NetworkHandlerServer handler;

    /**
     * Game initialization
     *
     * @param gamers player list.  Number handled by connection manager
     */
    public Game(ArrayList<Gamer> gamers, NetworkHandlerServer networkHandlerServer) {
        giocatori = gamers;
        this.handler = networkHandlerServer;
    }

    /**
     * Generates a new game in a new Thread
     */
    public void run() {
        cardChoice();
        placePawns();
        partita();
    }

    public void placePawns() {

    }

    public void cardChoice() {
        Extraction ex = new Extraction();
        godCards = ex.extractionGods(giocatori.size());
        ArrayList<God> cards = new ArrayList<>();
        cards.addAll(godCards);
        for (Gamer g : giocatori) {
            try {
                God god = this.handler.chooseCard(cards, g);
                g.setGod(god);
                cards.remove(god);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("problema con cast della carta o con la trasmissione");
                e.printStackTrace();
            }
        }
    }

    public Table getTable() {
        return table;
    }

    public void partita() {
        while (true) {
            for (Gamer g : giocatori) {
                ArrayList<God> gods = new ArrayList<God>();
                gods.addAll(godCards);
                Thread t = new Thread(new Turno(gods, g, table, handler));
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

    public void updateField() {
        for (Gamer g : giocatori) {
            handler.updateField(g);
        }
    }
}
