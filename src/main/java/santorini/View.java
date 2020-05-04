package santorini;

import santorini.model.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class View {

    private Table table;
    private BufferedReader br;
    private NetworkHandlerClient handlerClient;
    private Gamer gamer;
    private Turno turno;
    private Mossa move;
    private Mossa build;
    private String movePawn;
    private ArrayList<God> gods;

    public View() {
        table = new Table();
        gamer = new Gamer(null, "Prova", 0, null, null);
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void setHandlerClient(NetworkHandlerClient handlerClient) {
        this.handlerClient = handlerClient;
    }

    //metodo che stampa la table indicante livello cella e posizione pedina
    public void printTable(Table table) {
        //printGamerInGame(gamers);
        System.out.print("\t\t\t\t\t\t[colonna]\n\t\t*\t 0 \t *\t 1 \t *\t 2 \t *\t 3 \t *\t 4 \t *\n");
        System.out.print("[riga]\t------------------------------------------\n");
        for (int i = 0; i <= 4; i++) {
            System.out.print("* " + i + " *\t");
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
     * Method that let user choose cards at start of the game.
     * Every cards is a god with name and description about their skills.
     *
     * @param gods are the cards that server send to client
     */

    public void chooseCards(ArrayList<God> gods) {
        new Thread(() -> {
            String card = "1";
            God chooseCard = null;
            int size = gods.size();
            int number;
            for (God g : gods) {
                System.out.println(g.getName());
                System.out.println(g.getDescription());
            }
            System.out.println("Il numero di carte che puoi scegliere sono " + gods.size());
            try {
                do {
                    System.out.println("Che carta vuoi scegliere?");
                    if (size == 3) {
                        System.out.println("Premi [1] per scegliere la prima carta");
                        System.out.println("Premi [2] per scegliere la seconda carta");
                        System.out.println("Premi [3] per scegliere la terza carta");
                    } else if (size == 2) {
                        System.out.println("Premi [1] per scegliere la prima carta");
                        System.out.println("Premi [2] per scegliere la seconda carta");

                    } else if (size == 1) {
                        System.out.println("Premi [1] per scegliere la prima carta");
                    }
                    card = br.readLine();
                    number = Integer.parseInt(card);
                    if (size == 3) {
                        switch (number) {
                            case 1:
                                chooseCard = gods.get(0);
                                break;
                            case 2:
                                chooseCard = gods.get(1);
                                break;
                            case 3:
                                chooseCard = gods.get(2);
                                break;
                            default:
                                System.err.println("Errore nella scelta della carta");
                        }
                    } else if (size == 2) {
                        switch (number) {
                            case 1:
                                chooseCard = gods.get(0);
                                break;
                            case 2:
                                chooseCard = gods.get(1);
                                break;
                            default:
                                System.err.println("Errore inserimento carta");
                        }
                    } else if (size == 1) {
                        switch (number) {
                            case 1:
                                chooseCard = gods.get(0);
                                break;
                            default:
                                System.err.println("Errore inserimento carta");
                        }
                    }
                } while (number < 1 || number > 3);
                handlerClient.setCard(chooseCard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * method that let user build inside a cell of the table
     */
    public void setNewBuild() {
        new Thread(() -> {
            String moveBuild;
            int[] positionBuild = new int[2];
            System.out.println("In che cella vuoi costruire [x,y]? ");
            try {
                do {
                    moveBuild = br.readLine();
                    positionBuild = giveMeStringCoordinate(moveBuild);
                    int b = Integer.parseInt(movePawn);
                    build = new Mossa(Mossa.Action.BUILD, b, positionBuild[0], positionBuild[1]);
                } while (positionBuild[0] < 0 || positionBuild[0] > 4 || positionBuild[1] < 0 || positionBuild[1] > 4);
                handlerClient.setBuildPawn(positionBuild);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }

    /**
     * Method that let user choose pawn that want to move inside the table
     */
    public void setNewMove() {
        new Thread(() -> {
            String stringMove;
            int choosePawn;
            int[] coordinateMove = new int[2];
            try {
                do {
                    System.out.println("Che pedina vuoi muovere? ");
                    movePawn = br.readLine();
                    choosePawn = Integer.parseInt(movePawn);
                    switch (choosePawn) {
                        case 0:
                            do {
                                System.out.println("Inserisci movimento per la pedina 1 [x,y]: ");
                                stringMove = br.readLine();
                                coordinateMove = giveMeStringCoordinate(stringMove);
                                move = new Mossa(Mossa.Action.MOVE, 0, coordinateMove[0], coordinateMove[1]);
                            } while (coordinateMove[0] < 0 || coordinateMove[0] > 4 || coordinateMove[1] < 0 || coordinateMove[1] > 4);
                            break;
                        case 1:
                            do {
                                System.out.println("Inserisci movimento per la pedina 2 [x,y]: ");
                                stringMove = br.readLine();
                                coordinateMove = giveMeStringCoordinate(stringMove);
                                move = new Mossa(Mossa.Action.MOVE, 1, coordinateMove[0], coordinateMove[1]);
                            } while (coordinateMove[0] < 0 || coordinateMove[0] > 4 || coordinateMove[1] < 0 || coordinateMove[1] > 4);
                            break;
                        default:
                            System.out.println("Numero pedina inserita non corretta!");
                    }
                } while (choosePawn < 0 || choosePawn > 1);
                handlerClient.setMovementPawn(coordinateMove);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }


    /**
     * method that initialize player's number at start of game
     */
    public void setNumeroGiocatori() {
        new Thread(() -> {
            String partecipanti = "2";
            int players;
            try {
                do {
                    System.out.print("Scegli numero partecipanti [Default 2]: ");
                    partecipanti = br.readLine();
                    players = Integer.parseInt(partecipanti);
                } while (players < 2 || players > 3);
                handlerClient.setPartecipanti(players);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * method that initialize pawns inside the table at start of the game
     */
    public void setInitializePawn() {
        new Thread(() -> {
            String coordPawn0, coordPawn1;
            int[] coordinate;
            System.out.println("Inserisci posizioni delle pedine: ");
            try {
                do {
                    System.out.println("Inserisci cordinata pedina 1 [x,y]: ");
                    coordPawn0 = br.readLine();
                    coordinate = giveMeStringCoordinate(coordPawn0);
                    gamer.setAPawn(0, coordinate[0], coordinate[1], 0, 0);
                    table.setACell(coordinate[0], coordinate[1], 0, false, false, gamer.getPawn(0));

                    System.out.println("Inserisci cordinata pedina 2 [x,y]: ");
                    coordPawn1 = br.readLine();
                    coordinate = giveMeStringCoordinate(coordPawn1);
                    gamer.setAPawn(1, coordinate[0], coordinate[1], 0, 0);
                    table.setACell(coordinate[0], coordinate[1], 0, false, false, gamer.getPawn(1));
                } while (coordinate[0] < 0 || coordinate[0] > 4 || coordinate[1] < 0 || coordinate[1] > 4);
                handlerClient.setCordinata(coordinate);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * method that send an error massage
     */
    public void setFailed() {
        new Thread(() -> {

            System.out.println("Errore generale\n");
            System.out.println(("Nuova istruzione in arrivo --> "));

        }).start();
    }

}

