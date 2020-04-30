package santorini;

import santorini.model.Gamer;
import santorini.model.Mossa;
import santorini.model.Pawn;
import santorini.model.Table;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static java.awt.Color.*;

public class View {

    private Table table;
    private BufferedReader br;
    private NetworkHandlerClient handlerClient;

    public View() {
        table = new Table();
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public Table getTable() {
        return table;
    }

    public BufferedReader getBr() {
        return br;
    }

    public NetworkHandlerClient getHandlerClient() {
        return handlerClient;
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

    //metodo che ricerca in quali celle hanno la cupola
    public void searchLevelMax(Table table) {
        for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 4; j++) {

            }
        }
    }


    public void colorCellPawn(Pawn pawn) {
        if (pawn.getColorPawn().equals(YELLOW)) {
            System.out.print("\u001B[34m" + "\u001B[43m" + "(" + pawn.getIdPawn() + ")" + "\u001B[0m");
        } else {
            if (pawn.getColorPawn().equals(BLUE)) {
                System.out.print("\u001B[46m" + "(" + pawn.getIdPawn() + ")" + "\u001B[0m");

            } else {
                if (pawn.getColorPawn().equals(RED)) {
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
        if (color == YELLOW) {
            System.out.println("Giallo");
        } else {
            if (color == RED) {
                System.out.println("Rosso");
            } else {
                if (color == BLUE) {
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


}


