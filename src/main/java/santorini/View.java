package santorini;

import santorini.model.*;
import santorini.model.godCards.Hephaestus;
import santorini.model.godCards.Pdor;
import santorini.model.godCards.Prometheus;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class View {

    private Table table;
    private BufferedReader br;
    private NetworkHandlerClient handlerClient;
    private Mossa move;
    private Mossa build;
    private String movePawn;
    private ArrayList<God> gods;
    private Thread listen;
    private String color;
    private int ID, currentPawn, counter = 0;
    private boolean inTurno = false;


    /**
     * method setID
     *
     * @param ID curretn ID pawn
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * method setHandlerClient
     *
     * @param handlerClient .
     */
    public void setHandlerClient(NetworkHandlerClient handlerClient) {
        this.handlerClient = handlerClient;
    }

    /**
     * Initializes a new view that creates a networkhandler
     *
     * @param address address to connect network handler socket
     * @param name    player name in game
     */
    public View(String address, String name) {
        table = new Table();
        br = new BufferedReader(new InputStreamReader(System.in));
        handlerClient = new NetworkHandlerClient(address, name, this);
        listen = new Thread(handlerClient);
        listen.start();
    }

    public void setColor(String color) {
        this.color = color;
    }


    public Table getTable() {
        return table;
    }


    public synchronized void setTable(Table table) {
        this.table = table;
        counter++;
        if (counter > 2) {
            inTurno = false;
        }

    }

    /**
     * Prints field status on CLI
     */
    public synchronized void printTable() {
        //this.table = table;
        //printGamerInGame(gamers);

        System.out.print("\t\t\t\t\t\t[colonna]\n" + "\u001B[34m" + "\t\t*\t 0 \t *\t 1 \t *\t 2 \t *\t 3 \t *\t 4 \t *\n" + "\u001B[0m");
        System.out.print("[riga]\t------------------------------------------\n");
        for (int i = 0; i <= 4; i++) {
            System.out.print("\u001B[34m" + "* " + i + " *\t" + "\u001B[0m");
            for (int j = 0; j <= 4; j++) {
                if (table.getTableCell(i, j).getPawn() != null) {
                    System.out.print(" |");
                    colorCellPawn(table.getTableCell(i, j).getPawn());
                } else {
                    System.out.print(" |\t");
                    if (table.getTableCell(i, j).isComplete()) {
                        System.out.print("\u001B[45m" /*+ "\u001B[0m"*/);
                    } else {
                        System.out.print("\u001B[0m");
                    }
                }
                System.out.print(" " + table.getTableCell(i, j).getLevel() + "\u001B[0m" + "\t");
            }
            System.out.print(" |\n");
            System.out.print("\t\t------------------------------------------");
            System.out.println();
        }
    }

    //metodo che ricerca in quali celle hanno la cupola
    public void searchLevelMax(Table table) {
        for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 4; j++) {

            }
        }
    }


    public void colorCellPawn(Pawn pawn) {
        if (pawn.getColorPawn().equals(Color.YELLOW)) {
            System.out.print("\u001B[34m" + "\u001B[43m" + "(" + pawn.getIdPawn() + ")" + "\u001B[0m");
        } else {
            if (pawn.getColorPawn().equals(Color.BLUE)) {
                System.out.print("\u001B[46m" + "(" + pawn.getIdPawn() + ")" + "\u001B[0m");

            } else {
                if (pawn.getColorPawn().equals(Color.RED)) {
                    System.out.print("\u001B[41m" + "(" + pawn.getIdPawn() + ")" + "\u001B[0m");
                } else {
                    System.out.print("\u001B[0m");
                }
            }
        }
    }


    public void printColor(Color color) {
        if (color == Color.YELLOW) {
            System.out.println("Giallo");
        } else {
            if (color == Color.RED) {
                System.out.println("Rosso");
            } else {
                if (color == Color.BLUE) {
                    System.out.println("Blu");
                } else {
                    System.out.println("No color");
                }
            }
        }
    }


    /**
     * method noGodEffect
     *
     * @param no the input string of the gamer
     * @return the noEffect if the syntax is correct, or return null
     */

    public Mossa noGodEffect(String no) {
        if ((no.equals("NO")) || (no.equals("No")) || (no.equals("no"))) {
            Mossa noEffect;
            noEffect = new Mossa(Mossa.Action.MOVE, -1, -1, -1);
            return noEffect;
        } else {
            return null;
        }
    }

    /**
     * Shows to user available card with description
     * then request a choice
     *
     * @param gods List of available cards
     */
    public void chooseCards(ArrayList<God> gods) {
        new Thread(() -> {
            String card = "1";
            God chooseCard;
            int number = -1;
            System.out.println("\nSCEGLI LA TUA DIVINITA'");
            for (God g : gods) {
                System.out.println("\u001B[33m" + "**********************************************************************" + "\u001B[0m");
                System.out.println(gods.indexOf(g) + ":\t" + "\u001B[34m" + g.getName() + "\u001B[0m");
                System.out.println();
                System.out.println("\u001B[34m" + "**" + "\u001B[0m" + g.getDescription() + "\u001B[34m" + "**" + "\u001B[0m");
            }
            System.out.println("\u001B[33m" + "**********************************************************************" + "\u001B[0m");
            try {
                do {
                    System.out.print("Scegli una carta: ");
                    card = br.readLine();
                    try {
                        number = Integer.parseInt(card);
                    } catch (NumberFormatException ex) {
                        number = -1;
                    }
                    if (number == 8) {
                        number = gods.size();
                        gods.add(new Pdor());
                        break;
                    }

                    if (number == 13) {
                        number = gods.size();
                        gods.add(new Prometheus());
                        break;
                    }


                    if (number == 12) {
                        number = gods.size();
                        gods.add(new Hephaestus());
                        break;
                    }
                    if (number < 0 || number >= gods.size()) {
                        System.err.println("Errore carta scelta!");
                    }
                } while (number < 0 || number >= gods.size());
                System.out.println("Hai scelto " + "\u001B[34m" + gods.get(number).getName() + "\u001B[0m");
                chooseCard = gods.get(number);
                handlerClient.setCard(chooseCard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Requests to user a new action
     *
     * @param action action type BUILD or MOVE
     */
    public void setNewAction(Mossa.Action action) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String stringMove;
        String[] in;
        int[] coordinateMove = new int[2];
        try {
            boolean inputok = false;
            counter = 0;
            if (!inTurno) {
                currentPawn = readPawn();
                inTurno = true;
            }
            stringMove = getCoords(table, table.getXYPawn(ID, currentPawn, true), table.getXYPawn(ID, currentPawn, false), action);
            if (stringMove == null) {
                move = new Mossa(action, -1, -1, -1);
                handlerClient.sendAction(move);
                return;
            }
            in = stringMove.split(",");
            coordinateMove[0] = Integer.parseInt(in[0]);
            coordinateMove[1] = Integer.parseInt(in[1]);
            move = new Mossa(action, currentPawn, coordinateMove[0], coordinateMove[1]);
            handlerClient.sendAction(move);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Requests to user which pawn wants to use in this turn
     *
     * @return pawn id
     */
    private int readPawn() {
        do {
            System.out.print("Che pedina vuoi usare in questo turno? :  ");
            try {
                movePawn = br.readLine();
                switch (movePawn.charAt(0)) {
                    case '0':
                        return 0;
                    case '1':
                        return 1;
                    default:
                        System.err.println("--Pedina non valida--");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (true);
    }

    /**
     * Requests to player how many user has to be in the match
     */
    public void setNumeroGiocatori() {
        new Thread(() -> {
            String partecipanti = null;
            int players = 2;
            System.out.print("Scegli numero partecipanti [Default 2]: ");
            try {
                partecipanti = br.readLine();
                players = Integer.parseInt(partecipanti);//Se scelgo di usare il metodo devo mettere int al posto di string?
                if (players != 2 && players != 3) {
                    throw new NumberFormatException();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException ex) {
                System.err.println("--Formato non valido, verranno impostati 2 giocatori--");
                players = 2;
            }
            try {
                handlerClient.setPartecipanti(players);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Requests init pawn positions before game starts
     */
    public synchronized void setInitializePawn() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String coordPawn0, coordPawn1;
        System.out.println("Inserisci posizioni delle pedine: ");
        boolean valid;
        try {
            do {
                System.out.println("Inserisci cordinata pedina 1 [x,y]: ");
                coordPawn0 = br.readLine();
                valid = validaCoordinate(coordPawn0);
                if (!valid) {
                    System.err.println("--Formato coordinate non valido--");
                }
            } while (!valid);
            do {
                System.out.println("Inserisci cordinata pedina 2 [x,y]: ");
                coordPawn1 = br.readLine();
                valid = validaCoordinate(coordPawn1);
                if (!valid) {
                    System.err.println("--Formato coordinate non valido--");
                }
            } while (!valid);
            handlerClient.initializePawns(coordPawn0 + "," + coordPawn1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method validates an input as String coordinates
     *
     * @param coords coordinates in format x,y
     * @return true if string format is x,y and x,y are int ∈{0,4}; false otherwise
     */
    private boolean validaCoordinate(String coords) {
        String[] c = coords.split(",");
        if (c.length != 2) {
            return false;
        }
        try {
            int i = Integer.parseInt(c[0]);
            if (i < 0 || i > 4) {
                return false;
            }
            i = Integer.parseInt(c[1]);
            if (i < 0 || i > 4) {
                return false;
            }
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * This method is called if server sends an error message
     */
    public void setFailed(String msg) {
        System.err.println("Errore:\n" + msg);
    }

    /**
     * Prints on CLI a generic nofitication message
     *
     * @param msg message text
     */
    public void printMessage(String msg) {
        System.out.println(msg);
    }

    /**
     * Requests to user a directional input centered in choosed pawn
     * Return coordinates of a target cell in format x,y
     *
     * @param t     table play field
     * @param xPawn xcoord of pawn
     * @param yPawn ycoord of pawn
     * @return
     */
    public String getCoords(Table t, int xPawn, int yPawn, Mossa.Action cmd) {
        boolean errato = true;
        Cell c = null;
        do {
            switch (cmd) {
                case MOVE:
                    System.out.println("Nuova mossa:");
                    break;
                case BUILD:
                    System.out.println("Nuova costruzione:");
                    break;
            }
            System.out.println("7: \uD83E\uDC54\t8: \uD83E\uDC51\t9: \uD83E\uDC55");
            System.out.println("4: \uD83E\uDC50\t5: No\t6: \uD83E\uDC52");
            System.out.println("1: \uD83E\uDC57\t2: \uD83E\uDC53\t3: \uD83E\uDC56");
            System.out.print("Inserisci direzione: ");
            Cell[][] target = t.getAroundCells(xPawn, yPawn);
            String s = null;
            try {
                s = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            switch (s.charAt(0)) {
                case '1':
                    errato = false;
                    c = target[2][0];
                    break;
                case '2':
                    errato = false;
                    c = target[2][1];
                    break;
                case '3':
                    errato = false;
                    c = target[2][2];
                    break;
                case '4':
                    errato = false;
                    c = target[1][0];
                    break;
                case '5':
                    return null;
                case '6':
                    errato = false;
                    c = target[1][2];
                    break;
                case '7':
                    errato = false;
                    c = target[0][0];
                    break;
                case '8':
                    errato = false;
                    c = target[0][1];
                    break;
                case '9':
                    errato = false;
                    c = target[0][2];
                    break;
                default:
                    errato = true;
            }
            if (errato || c == null) {
                System.err.println("--Input non corretto--");
                printTable();
                counter = 0;
            }
        } while (errato || c == null);
        return c.getX() + "," + c.getY();
    }

    /**
     * Win notify
     */
    public void vittoria() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printMessage("HAI VINTO!");
        try {
            handlerClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    /**
     * Lose notify
     *
     * @param winner
     */
    public void sconfitta(String winner) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printMessage("HAI PERSO!");
        printMessage("Ha vinto " + winner);
        try {
            handlerClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    /**
     * Network error notify
     *
     * @param player
     */
    public void networkError(String player) {
        printMessage(player + " Si è disconnesso\nFine della partita");
        System.exit(0);
    }

}
