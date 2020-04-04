package santorini.model;

import java.io.Serializable;

public class Table implements Serializable {
    private int row = 5;
    private int column = 5;
    private Cell[][] cells;


    public Table() {
        //Start set of the table
        cells = new Cell[row][column];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                cells[i][j] = new Cell();
                cells[i][j].setX(i);
                cells[i][j].setY(j);
                cells[i][j].setLevel(0);
                cells[i][j].setFree(true);
                cells[i][j].setComplete(false);
            }
        }
    }

    public Cell getTableCell(int x, int y) {
        return cells[x][y];
    }
}
