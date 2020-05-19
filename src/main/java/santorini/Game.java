package santorini;

import javafx.scene.paint.Color;
import santorini.model.Extraction;
import santorini.model.Gamer;
import santorini.model.godCards.God;
import santorini.model.Table;

import java.io.IOException;
import java.util.ArrayList;

public class Game implements Runnable {
    private final ArrayList<Gamer> playersInGame;
    /*god cards sono quelle attive nella partita
     * passa una copia di questa lista al turno
     * rifai la classe extraction con la shuffle della collection*/
    private ArrayList<God> godCards;
    private Table table = new Table();
    private final NetworkHandlerServer handler;

    /**
     * method getPlayersInGame
     *
     * @return the players in game
     */
    public ArrayList<Gamer> getPlayersInGame() {
        return playersInGame;
    }

    /**
     * Game initialization
     *
     * @param gamers player list.  Number handled by connection manager
     */
    public Game(ArrayList<Gamer> gamers, NetworkHandlerServer networkHandlerServer) {
        playersInGame = gamers;
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
        handler.updateField(playersInGame.get(0));
        for (Gamer g : playersInGame) {
            broadcastMessage(g.getName() + " sta posizionando le sue pedine");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
                        updateField();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    networkError(g);
                } catch (ClassNotFoundException e) {
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
        godCards = ex.extractionGods(playersInGame.size());
        ArrayList<God> cards = new ArrayList<>();
        cards.addAll(godCards);
        for (Gamer g : playersInGame) {
            broadcastMessage(g.getName() + " sta scegliendo la carta");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                God god = this.handler.chooseCard(cards, g);
                g.setMyGodCard(god);
                god.setOwner(g);
                god.setOthers(playersInGame);
                cards.remove(god);
            } catch (IOException e) {
                networkError(g);
            } catch (ClassNotFoundException e) {
                System.out.println("problema con cast della carta");
                e.printStackTrace();
            }
            broadcastMessage(g.getName() + " ha scelto: " + "\u001B[34m" + g.getMyGodCard().getName() + "\u001B[0m");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
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
        broadcastMessage("\nINIZIO PARTITA");
        playersInMatch(playersInGame);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        broadcastMessage("\n");
        while (true) {
            for (Gamer g : playersInGame) {
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
        for (Gamer g : playersInGame) {
            handler.updateField(g);
        }
    }

    /**
     * Sends to all player who is the winner
     *
     * @param winner winner player
     */
    public void setWinner(Gamer winner) {
        for (Gamer g : playersInGame) {
            try {
                handler.updateField(g);
                handler.winner(g, winner);
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
        playersInGame.remove(disconnected);
        for (Gamer g : playersInGame) {
            try {
                handler.notifyNetworkError(g, disconnected);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.exit(1);
    }

    /**
     * Sends a message to all players connected in this match
     *
     * @param message message to send
     */
    public void broadcastMessage(String message) {
        for (Gamer g : playersInGame) {
            handler.sendMessage(g, message);
        }
    }

    private String printColor(Color color) {
        if (color == Color.YELLOW) {
            return "Giallo";
        } else {
            if (color == Color.RED) {
                return "Rosso";
            } else {
                if (color == Color.BLUE) {
                    return "Blu";
                } else {
                    return "No color";
                }
            }
        }
    }

    private void playersInMatch(ArrayList<Gamer> gamers) {
        for (Gamer g : gamers) {
            broadcastMessage("Giocatore : " + g.getName() + "\t\t\t" +
                    "Carta : " + g.getMyGodCard().getName() + "\t\t\tColore : " + printColor(g.getColorGamer()));
        }
    }


}
