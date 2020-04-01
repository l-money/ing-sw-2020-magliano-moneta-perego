package santorini.model;

import java.util.ArrayList;

public class Table {

    private final int width = 5, height = 5; //valori definiti di larghezza e altezza tabella
    private Cell[][] tabella = new Cell[width][height]; //matrice tabella del gioco 5x5


    //costruttore per inizializzazione del gioco
    public Table() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tabella[i][j] = new Cell();
            }
        }
    }
    //costruzione metodo celle adiacenti
    public ArrayList<Cell>(
    int x, int y)

    {


    }

    //metodo movimento tra le celle adiacenti
    public void
}
