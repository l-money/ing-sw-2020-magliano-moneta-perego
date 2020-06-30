package santorini.controller;

import santorini.network.NetworkHandlerServer;
import santorini.model.Color;
import santorini.model.Extraction;
import santorini.model.Gamer;
import santorini.model.Table;
import santorini.model.godCards.God;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class Game
 */

/**
 * Implementation choices
 * <p>
 * The client has 3 attempts for doing a correct action in game (especially in CLI).
 * During the choice of the cards: if client ends his attempts, controller chooses a card for him.
 * During the placement of the pawns: the client has 3 attempts for each pawns,
 * if client ends the attempts the controller places the pawns in a random position of the table.
 * During the game: if client ends his attempts the controller sets loser the client who is out of the game.
 */

public class Game implements Runnable {
    private final ArrayList<Gamer> playersInGame;
    private ArrayList<God> godCards;
    private Table table = new Table();
    private final NetworkHandlerServer handler;
    private Thread currentTurno;

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
        doubleName(playersInGame);
        cardChoice();
        placePawns();
        matchGame();
    }

    /**
     * method doubleName
     * announces the name is already present and it's changed
     *
     * @param playersInGame players in game
     */
    private void doubleName(ArrayList<Gamer> playersInGame) {
        String n = playersInGame.get(0).getName();
        int l = playersInGame.size();
        for (int i = 1; i < l; i++) {
            Gamer g = playersInGame.get(i);
            if ((g.getName().contains(n)) && (g.getName().contains("-1") || (g.getName().contains("-2")))) {
                handler.sendMessage(g, "\u001B[31m" + "Il nome che hai scelto\nè già stato utilizzato!!\nIl tuo nuovo nome è: \u001B[0m" + g.getName());
            }
        }
        broadcastMessage("\n" + "\u001B[33m" + "SELEZIONE CARTE" + "\u001B[0m");
    }

    /**
     * method placePawns
     * requests to all clients to place their pawns
     */
    private void placePawns() {
        handler.updateField(playersInGame.get(0));
        broadcastMessage("\n" + "\u001B[33m" + "POSIZIONAMENTO PEDINE" + "\u001B[0m");
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
     * method cardChoice
     * extracts number of players of random cards and requests
     * to clients in order to choose one
     */
    public void cardChoice() {
        Extraction ex = new Extraction();
        godCards = ex.extractionGods(playersInGame.size());
        ArrayList<God> cards = new ArrayList<>();
        cards.addAll(godCards);
        for (Gamer g : playersInGame) {
            for (Gamer gamer : playersInGame) {
                if (gamer != g) {
                    handler.sendMessage(gamer, g.getName() + " sta scegliendo la carta...\n");
                }
            }
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
            for (Gamer gamer : playersInGame) {
                if (gamer != g) {
                    handler.sendMessage(gamer, g.getName() + " ha scelto:\n" + "\u001B[34m" + g.getMyGodCard().getName() + "\u001B[0m" + "\n\u001B[33m" + g.getMyGodCard().getDescription() + "\u001B[0m\n");
                } else {
                    handler.sendMessage(gamer, "\n" + "Hai scelto:\n" + "\u001B[34m" + g.getMyGodCard().getName() + "\u001B[0m\n");
                }
            }

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
     * method matchGame
     * game loop.
     * each cicle ask to a player to do his moves
     * cicle continue until someone wins
     */
    public void matchGame() {
        broadcastMessage("\n" + "\u001B[33m" + "INIZIO PARTITA" + "\u001B[0m\n");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playersInMatch(playersInGame);
        while (true) {
            for (Gamer g : playersInGame) {
                ArrayList<God> gods = new ArrayList<God>(godCards);
                currentTurno = new Thread(new Turno(gods, g, table, handler));
                currentTurno.start();
                try {
                    currentTurno.join();
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
     * method updateField
     * sends to all clients the game field status
     */
    public void updateField() {
        for (Gamer g : playersInGame) {
            handler.updateField(g);
        }
    }

    /**
     * method setWinner
     * sends to all player who is the winner
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
     * method networkError
     * notify to all clients that one user has disconnected
     *
     * @param disconnected disconnected player
     */
    public void networkError(Gamer disconnected) {
        playersInGame.remove(disconnected);
        try {
            for (Gamer g : playersInGame) {
                try {
                    handler.notifyNetworkError(g, disconnected);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            handler.getPartita().interrupt();
            currentTurno.interrupt();
        }
    }

    /**
     * method broadcastMessage
     * sends a message to all players connected in this match
     *
     * @param message message to send
     */
    public void broadcastMessage(String message) {
        for (Gamer g : playersInGame) {
            handler.sendMessage(g, message);
        }
    }

    /**
     * method printColor
     *
     * @param color .
     * @return string name of the color
     */
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

    /**
     * method playersInMatch
     *
     * @param gamers the players in the game
     */
    private void playersInMatch(ArrayList<Gamer> gamers) {
        for (Gamer g : gamers) {
            broadcastMessage("Giocatore : " + g.getName() + "\t\t\t" +
                    "Carta : " + g.getMyGodCard().getName() + "\t\t\tColore : " + printColor(g.getColorGamer()));
        }
    }


}
