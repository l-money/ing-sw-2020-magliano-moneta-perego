package santorini;

import santorini.model.*;
import santorini.model.godCards.Athena;
import santorini.model.godCards.Atlas;
import santorini.model.godCards.Pdor;

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
    private int IDP;
    private ArrayList<God> gods;
    private Thread listen;
    private String color;
    private int ID;

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

    /*public void setTable(Table table) {
        this.table = table;
    }*/


    //metodo che stampa la table indicante livello cella e posizione pedina
    public synchronized void printTable(Table table) {
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
                        System.out.print("\u001B[45m" + "\u001B[0m");
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

    public void printGamerInGame(ArrayList<Gamer> gamers) {
        int k = gamers.size();
        for (int i = 0; i < k; i++) {
            System.out.print("Giocatore " + (i + 1) + ": " + gamers.get(i).getName() + "\t\tCarta: " + gamers.get(i).getMyGodCard().getName() + "\t\tColore: ");
            printColor(gamers.get(i).getColorGamer());
        }
        System.out.println();
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
     * NON SERVE
     * giveMeStringCoordinate
     *
     * @param s the input string with coordinate, the correct syntax is: x,y
     * @return the coordinate x = row and y = column
     */
    public int[] giveMeStringCoordinate(String s) {
        int l = s.length();
        int[] cordinate = new int[2];
        cordinate[0] = -1;
        cordinate[1] = -1;
        if (l != 3) {
            System.err.println("Errore: sintassi non corretta");
        } else {
            char[] c = new char[l];
            for (int i = 0; i < l; i++) {
                c[i] = s.charAt(i);
            }
            if (c[1] != ',') {
                System.err.println("Errore: sintassi non corretta");
            } else {
                int x = Character.getNumericValue(c[0]);
                int y = Character.getNumericValue(c[2]);
                if ((x < 0) || (x > 4) || (y < 0) || (y > 4)) {
                    System.err.println("Errore: valori non esistenti");
                } else {
                    cordinate[0] = x;
                    cordinate[1] = y;
                }

            }
        }
        return cordinate;
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
                System.out.println("\u001B[33m" + "**************************************************************" + "\u001B[0m");
                System.out.println(gods.indexOf(g) + ":\t" + "\u001B[34m" + g.getName() + "\u001B[0m");
                System.out.println();
                System.out.println("\u001B[34m" + "**" + "\u001B[0m" + g.getDescription() + "\u001B[34m" + "**" + "\u001B[0m");
            }
            System.out.println("\u001B[33m" + "**************************************************************" + "\u001B[0m");
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
                    //aggiunto Apollo di default
                    if (number == 13) {
                        number = gods.size();
                        gods.add(new Athena());
                        break;
                    }

                    //aggiunto Artemide di default
                    if (number == 12) {
                        number = gods.size();
                        gods.add(new Atlas());
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
     * Requests to user the place where he wants to build
     */
    public synchronized void setNewBuild() {

        String moveBuild;
        int[] positionBuild = new int[2];
        boolean valid = false;
        try {
            do {
                System.out.println("In che cella vuoi costruire [x,y]? ");
                moveBuild = br.readLine();
                //TODO mettere la condizione no
                if ((moveBuild.equals("NO")) || (moveBuild.equals("No")) ||
                        (moveBuild.equals("no")) || (moveBuild.equals("nO"))) {
                    build = new Mossa(Mossa.Action.BUILD, -1, -1, -1);
                    valid = true;
                } else {
                    valid = validaCoordinate(moveBuild);
                    if (valid) {
                        String in[] = moveBuild.split(",");
                        positionBuild[0] = Integer.parseInt(in[0]);
                        positionBuild[1] = Integer.parseInt(in[1]);
                        //mettere anche qui charAt
                        //int b = Integer.parseInt(movePawn);
                        build = new Mossa(Mossa.Action.BUILD, IDP, positionBuild[0], positionBuild[1]);
                    }
                }
            } while (!valid);
            handlerClient.setBuildPawn(build);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Requests to user the place where he wants to move
     */
    public synchronized void setNewMove() {
        String stringMove;
        String[] in;
        int[] coordinateMove = new int[2];
        try {
            boolean inputok = false;
            //System.out.println("E' il tuo turno");
            do {
                System.out.println("Che pedina vuoi muovere? ");
                movePawn = br.readLine();
                if ((movePawn.equals("NO")) || (movePawn.equals("No")) ||
                        (movePawn.equals("no")) || (movePawn.equals("nO"))) {
                    move = new Mossa(Mossa.Action.MOVE, -1, -1, -1);
                    inputok = true;
                } else {
                    // TODO cambiare charAt con Integer.parsInt ??
                    switch (movePawn.charAt(0)) {
                        case '0':
                            do {
                                System.out.println("Inserisci movimento per la pedina 1 [x,y]: ");
                                stringMove = br.readLine();
                                inputok = validaCoordinate(stringMove);
                            } while (!inputok);
                            in = stringMove.split(",");
                            coordinateMove[0] = Integer.parseInt(in[0]);
                            coordinateMove[1] = Integer.parseInt(in[1]);
                            move = new Mossa(Mossa.Action.MOVE, 0, coordinateMove[0], coordinateMove[1]);
                            IDP = 0;
                            break;
                        case '1':
                            do {
                                System.out.println("Inserisci movimento per la pedina 2 [x,y]: ");
                                stringMove = br.readLine();
                                inputok = validaCoordinate(stringMove);
                            } while (!inputok);
                            in = stringMove.split(",");
                            coordinateMove[0] = Integer.parseInt(in[0]);
                            coordinateMove[1] = Integer.parseInt(in[1]);
                            move = new Mossa(Mossa.Action.MOVE, 1, coordinateMove[0], coordinateMove[1]);
                            IDP = 1;
                            break;
                        default:
                            System.err.println("Pedina non valida");
                            inputok = false;
                    }
                }
            } while (!inputok);
            handlerClient.setMovementPawn(move);

        } catch (IOException e) {
            e.printStackTrace();
        }

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
                System.err.println("Formato non valido, verranno impostati 2 giocatori");
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
        String coordPawn0, coordPawn1;
        System.out.println("Inserisci posizioni delle pedine: ");
        boolean valid;
        try {
            do {
                System.out.println("Inserisci cordinata pedina 1 [x,y]: ");
                coordPawn0 = br.readLine();
                valid = validaCoordinate(coordPawn0);
                if (!valid) {
                    System.out.println("Formato coordinate non valido");
                }
            } while (!valid);
            do {
                System.out.println("Inserisci cordinata pedina 2 [x,y]: ");
                coordPawn1 = br.readLine();
                valid = validaCoordinate(coordPawn1);
                if (!valid) {
                    System.out.println("Formato coordinate non valido");
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
    public void setFailed() {
        System.err.println("Errore generale");
            System.out.println(("Nuova istruzione in arrivo: "));
    }


}
