/**
 * @autor Magliano
 */
package santorini.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class Table
 */

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
            }
        }
    }

    /**
     * method getTableCell
     *
     * @param x cell with coordinate x.
     * @param y cell with coordinate y.
     * @return all the cell with specific coordinates.
     */
    public Cell getTableCell(int x, int y) {
        return cells[x][y];
    }


    /**
     * method searchAdjacentCells
     *
     * @param pointCell is the parameter that find where my pawn is in the cell.
     * @return searchCell that is all near cells from cell where my pawn is.
     */
    public ArrayList<Cell> searchAdjacentCells(Cell pointCell) {
        int x;
        int y;
        int i;
        int j;
        ArrayList<Cell> searchCell;
        searchCell = new ArrayList<>();
        x = pointCell.getX();
        y = pointCell.getY();
        for (i = x - 1; i < x + 2; i++) {
            for (j = y - 1; j < y + 2; j++) {
                if (((i != x) || (j != y)) && (i >= 0) && (i <= 4) && (j >= 0) && (j <= 4)) {
                    searchCell.add(getTableCell(i, j));
                }
            }
        }
        return searchCell;
    }

    /**
     * method tablePerimeterCells
     *
     * @param t my table
     * @return perimeterCells, the cells on the perimeter of the table
     */
    public ArrayList<Cell> tablePerimeterCells(Table t) {
        ArrayList<Cell> perimeterCells = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            perimeterCells.add(t.getTableCell(0, i));
            perimeterCells.add(t.getTableCell(4, i));
        }
        for (int i = 1; i < 4; i++) {
            perimeterCells.add(t.getTableCell(i, 0));
            perimeterCells.add(t.getTableCell(i, 4));
        }
        return perimeterCells;
    }

    /**
     * method walkNearCell
     *
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

    /**
     * method setACell
     *
     * @param x        row
     * @param y        column
     * @param level    level
     * @param free     free
     * @param complete complete
     * @param pawn     pawn into cell, possible null
     */
    public void setACell(int x, int y, int level, boolean free, boolean complete, Pawn pawn) {
        getTableCell(x, y).setX(x);
        getTableCell(x, y).setY(y);
        getTableCell(x, y).setFree(free);
        getTableCell(x, y).setPawn(pawn);
        getTableCell(x, y).setComplete(complete);
        getTableCell(x, y).setLevel(level);
        if (getTableCell(x, y).getPawn() != null) {
            getTableCell(x, y).getPawn().setPresentLevel(level);
            getTableCell(x, y).getPawn().setRow(x);
            getTableCell(x, y).getPawn().setColumn(y);
        }
    }

    /**
     * method iCanMove
     * control my pawn can move
     *
     * @param myCell the position of my pawn
     * @return true if the pawn can move, else return false
     */
    public boolean iCanMove(Cell myCell) {
        int k = 0;
        int free = 0;
        ArrayList<Cell> nearCells;
        //create an ArrayList of near cells
        nearCells = searchAdjacentCells(myCell);
        //save the size of the nearCells
        int l = nearCells.size();
        //for each nearCell control I can move on it
        for (int i = 0; i < l; i++) {
            Cell index = nearCells.get(i);
            k = index.getLevel() - myCell.getLevel();
            if ((k <= 1) && (index.isFree()) && (!index.isComplete()) && (index.getPawn() == null)) {
                free = free + 1;
            }
        }
        //If nearCellsFree is not empty, the pawn can move
        if (free > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * method iCanBuild
     * control my pawn can build
     *
     * @param myCell the position of my pawn
     * @return true if the pawn can build, else return false
     */
    public boolean iCanBuild(Cell myCell) {
        ArrayList<Cell> nearCells = searchAdjacentCells(myCell);
        int free = 0;
        int l = nearCells.size();
        for (int i = 0; i < l; i++) {
            Cell index = nearCells.get(i);
            if ((index.isFree()) && (!index.isComplete()) && (index.getLevel() >= 0) && (index.getLevel() <= 3) &&
                    (index.getPawn() == null)) {
                free = free + 1;
            }
        }
        if (free > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * method controlBaseMovement
     * control the pawn can do the move respecting the rules
     *
     * @param start the position of the pawn
     * @param end   the destination of the movement
     * @return true if the pawn can do the movement respecting the rules, else return false
     */
    public boolean controlBaseMovement(Cell start, Cell end) {
        int k = end.getLevel() - start.getLevel();
        ArrayList<Cell> nearCells = new ArrayList<>();
        nearCells = searchAdjacentCells(start);
        if (!nearCells.contains(end)) {
            //not near cells
            return false;
        } else {
            if (end.isComplete()) {
                //is complete
                return false;
            } else {
                if (end.getPawn() != null || !end.isFree()) {
                    //not null
                    return false;
                } else {
                    if (k <= 1) {
                        return true;
                    } else {
                        //level up more
                        return false;
                    }
                }
            }
        }
    }

    /**
     * method controlBaseBuild
     * control the pawn can build into a cell respecting the rules
     *
     * @param start the position of the pawn
     * @param end   the destination of the building
     * @return true if the pawn can build  respecting the rules, else return false
     */
    public boolean controlBaseBuilding(Cell start, Cell end) {
        ArrayList<Cell> nearCells = searchAdjacentCells(start);
        if (!nearCells.contains(end)) {
            //not near cells
            return false;
        } else {
            if (!end.isFree() || (end.getPawn() != null)) {
                //not null
                return false;
            } else {
                if (end.isComplete()) {
                    //is complete
                    return false;
                } else {
                    if ((end.getPawn() == null) && (end.getLevel() >= 0) && (end.getLevel() <= 3)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
    }

    /**
     * method build
     *
     * @param cell in which my pawns builds
     */
    public void build(Cell cell) {
        int myLevel = cell.getLevel();
        cell.setLevel(myLevel + 1);
        int newLevel = cell.getLevel();
        if (newLevel > 3) {
            cell.setLevel(3);
            cell.setComplete(true);
        } else {
            cell.setLevel(newLevel);
        }
    }

    /**
     * Finds all cells around a specified cell in a square 3x3
     *
     * @param x x coord of specified cell
     * @param y y  coord of specified cell
     * @return a 3x3 array with all cells around
     */
    public Cell[][] getAroundCells(int x, int y) {
        Cell[][] around = new Cell[3][3];
        int ix = x - 1, jy = y - 1;
        for (int i = 0; i < 3; i++, ix++) {
            jy = y - 1;
            for (int j = 0; j < 3; j++, jy++) {
                if (ix == x && jy == y) {
                    around[i][j] = null;
                } else if (ix >= 0 && jy >= 0 && ix < 5 && jy < 5) {
                    around[i][j] = cells[ix][jy];
                }
            }
        }
        return around;
    }

    /**
     * Looks for a specified client's pawn
     *
     * @param player player ID
     * @param pawnID pawn ID
     * @param X      coordinate type. true: X false: Y
     * @return X or Y coordinate of pawn depending by X boolean
     */
    public int getXYPawn(int player, int pawnID, boolean X) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (cells[i][j].getPawn() != null && cells[i][j].getPawn().getIdGamer() == player && cells[i][j].getPawn().getIdPawn() == pawnID) {
                    if (X) {
                        return i;
                    } else {
                        return j;
                    }
                }
            }
        }
        return -1;
    }

}
