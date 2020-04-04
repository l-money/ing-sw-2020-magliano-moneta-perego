package santorini.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
     * @return searchCell
     */
    //metodo per riconoscere le celle adiacenti
    public List<Cell> searchCells(Cell pointCell) {
        int x, y, i, j;
        List<Cell> searchCell = new ArrayList<Cell>();
        x = pointCell.getX();
        y = pointCell.getY();
        for (i = x - 1; i < x + 2; i++) {
            for (j = y - 1; j < y + 2; j++) {
                if ((i != x) && (j != y) && (i >= 0) && (i <= 4) && (j >= 0) && (j <= 4)) {
                    searchCell.add(getTableCell(i, j));
                }
            }
        }
        return searchCell;
    }


}
