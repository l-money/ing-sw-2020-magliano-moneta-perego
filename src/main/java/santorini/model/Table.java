package santorini.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Table implements Serializable {
    private int row = 5;
    private int column = 5;
    private Cell[][] cells;

    /**
     * Table builder for all 25 cells in the table.
     */
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

    /**
     * @param x cell with coordinate x.
     * @param y cell with coordinate y.
     * @return all the cell with specific coordinates.
     */
    public Cell getTableCell(int x, int y) {
        return cells[x][y];
    }

    /**
     * @param pointCell is the parameter that find where my pawn is in the cell.
     * @return searchCell that is all near cells from cell where my pawn is.
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

    /**
     * @param c1 is the cell c1(x1,y1).
     * @param c2 is the cell c2(x2,y2).
     * @return the max module between the difference of every coordinate (x,y) when i must
     * move my pawn.
     */
    public int walkNearCell(Cell c1, Cell c2) {
        int x1 = c1.getX();
        int y1 = c1.getY();
        int x2 = c2.getX();
        int y2 = c2.getY();
        return Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));
    }

}
