package santorini;

import santorini.model.Extraction;
import santorini.model.Gamer;
import santorini.model.God;
import santorini.model.Table;

import java.io.IOException;
import java.util.ArrayList;

public class Game implements Runnable {
    private final ArrayList<Gamer> giocatori;
    /*god cards sono quelle attive nella partita
     * passa una copia di questa lista al turno
     * rifai la classe extraction con la shuffle della collection*/
    private ArrayList<God> godCards;
    private Table table = new Table();
    private final NetworkHandlerServer handler;

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

    /**
     * Requests to all clients to place their pawns
     */
    public void placePawns() {
        for (Gamer g : giocatori) {
            boolean done = false;
            do {
                try {
                    String posizioni = handler.placePawns(g);
                    String pos[] = posizioni.split(","); //x1,y1,x2,y2
                    int x1 = Integer.parseInt(pos[0]);
                    int y1 = Integer.parseInt(pos[1]);
                    int x2 = Integer.parseInt(pos[2]);
                    int y2 = Integer.parseInt(pos[3]);
                    if (!table.getTableCell(x1, y1).isFree() || !table.getTableCell(x2, y2).isFree()) {
                        handler.sendFailed(g, "Posizione già occupata");
                        done = false;
                    } else {
                        done = true;
                        table.setACell(x1, y1, 0, false, false, g.getPawn(0));
                        table.setACell(x2, y2, 0, false, false, g.getPawn(1));
                        g.setAPawn(0, x1, y1, 0, 0);
                        g.setAPawn(1, x2, y2, 0, 0);
                        /**
                         g.getPawn(0).setRow(x1);
                         g.getPawn(0).setColumn(y1);
                         g.getPawn(1).setRow(x2);
                         g.getPawn(1).setColumn(x2); //TODO ERRORE!!!
                         table.getTableCell(x1, y1).setFree(false);
                         table.getTableCell(x1, y1).setPawn(g.getPawn(0));
                         table.getTableCell(x2, y2).setFree(false);
                         table.getTableCell(x2, y2).setPawn(g.getPawn(1));
                         */
                        updateField();
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } while (!done);
        }
    }

    /**
     * Extracts number of players of random cards and requests
     * to clients in order to choose one
     */
    public void cardChoice() {
        Extraction ex = new Extraction();
        godCards = ex.extractionGods(giocatori.size());
        ArrayList<God> cards = new ArrayList<>();
        cards.addAll(godCards);
        for (Gamer g : giocatori) {
            try {
                God god = this.handler.chooseCard(cards, g);
                g.setMyGodCard(god);
                god.setOwner(g);
                god.setOthers(giocatori);
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

    /**
     * Game loop.  Each cicle ask to a player to do his moves
     * Cicle continue until someone wins
     */
    public void partita() {
        while (true) {
            for (Gamer g : giocatori) {
                ArrayList<God> gods = new ArrayList<God>(godCards);
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

    /**
     * Sends to all clients the game field status
     */
    public void updateField() {
        for (Gamer g : giocatori) {
            handler.updateField(g);
        }
    }

    /**
     * Sends to all player who is the winner
     *
     * @param winner winner player
     */
    public void setWinner(Gamer winner) {
        for (Gamer g : giocatori) {
            try {
                handler.winner(g, winner);
                g.getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Notify to all clients that one user has disconnected
     *
     * @param disconnected disconnected player
     */
    public void networkError(Gamer disconnected) {
        giocatori.remove(disconnected);
        for (Gamer g : giocatori) {
            try {
                handler.notifyNetworkError(g, disconnected);
                g.getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.exit(1);
    }

}
