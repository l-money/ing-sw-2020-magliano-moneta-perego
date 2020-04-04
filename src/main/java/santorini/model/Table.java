package santorini.model;

import java.io.Serializable;
import java.util.ArrayList;

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
                cells[i][j].startCell();
            }
        }
    }

    public Cell getTableCell(int x, int y) {
        return cells[x][y];
    }

    /**
     * @param pointCell
     * @return
     */
    //metodo per riconoscere le celle adiacenti
    public Cell[] nearCell(Cell pointCell) {
        int k = 0;
        int x, y, i, j;
        Cell[] searchCell;
        searchCell = new Cell[k];
        x = pointCell.getX();
        y = pointCell.getY();
        for (i = x - 1; i < x + 2; i++) {
            for (j = y - 1; j < y + 2; j++) {
                if ((i != x) && (j != y) || (i >= 0) || (i <= 4) || (j >= 0) || (j <= 4)) {
                    searchCell[k] = getTableCell(i, j);
                } else {
                    searchCell[k] = null;
                }
                k++;
            }
        }
        return searchCell;
    }


}
