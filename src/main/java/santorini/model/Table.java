/**
 * @autor Magliano
 */
package santorini.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Table implements Serializable {
    private int row = 5;
    private int column = 5;
    private Cell[][] cells;
    private Bag bag = new Bag();

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
     * method getBag
     *
     * @return the bag of the table
     */
    public Bag getBag() {
        return bag;
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
    public ArrayList<Cell> searchAdjacentCells(Cell pointCell) {
        int x;
        int y;
        int i;
        int j;
        ArrayList<Cell> searchCell;
        searchCell = new ArrayList<Cell>();
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
     * method setACell for set a cell
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
     * method iCanMove : control my pawn can move
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
     * method iCanBuild : control my pawn can build
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
                    (index.getPawn()==null)) {
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
     * method controlBaseMovement : control the pawn can do the move respecting the rules
     *
     * @param start the position of the pawn
     * @param end   the destination of the movement
     * @return true if the pawn can do the movement respecting the rules, else return false
     */
    public boolean controlBaseMovement(Cell start, Cell end) {
        int k = end.getLevel() - start.getLevel();
        ArrayList<Cell> nearCells = new ArrayList<>();
        nearCells = searchAdjacentCells(start);
        //
        System.out.println("Number of near cells : " + nearCells.size());
        //
        if (!nearCells.contains(end)) {
            System.out.println("not nearcells");
            return false;
        } else {
            if (end.isComplete()) {
                System.out.println("is complete");
                return false;
            } else {
                if (end.getPawn() != null || !end.isFree()) {
                    System.out.println("not null");
                    return false;
                } else {
                    if (k <= 1) {
                        return true;
                    } else {
                        System.out.println("salire di più livelli");
                        return false;
                    }
                }
            }
        }
    }

    /**
     * method controlBaseBuild : control the pawn can build into a cell respecting the rules
     * @param start the position of the pawn
     * @param end the destination of the building
     * @return true if the pawn can build  respecting the rules, else return false
     */
    public boolean controlBaseBuilding(Cell start, Cell end) {
        ArrayList<Cell> nearCells = searchAdjacentCells(start);
        if (!nearCells.contains(end)) {
            return false;
        } else {
            if (!end.isFree() || (end.getPawn() != null)) {
                return false;
            } else {
                if (end.isComplete()) {
                    return false;
                } else {
                    if ((end.getPawn() == null) && (end.getLevel() >= 0) && (end.getLevel() <= 3) &&
                            (getBag().controlExistBrick(end.getLevel() + 1))) {
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
     * @param cell in which my pawns builds
     * @return true if the pawn builds correctly
     */
    public boolean build(Cell cell) {
        int myLevel = cell.getLevel();
        if (getBag().controlExistBrick(myLevel + 1)) {
            cell.setLevel(myLevel + 1);
            int newLevel = cell.getLevel();
            if (newLevel > 3) {
                cell.setLevel(3);
                cell.setComplete(true);
            } else {
                cell.setLevel(newLevel);
            }
            getBag().extractionBrick(myLevel + 1);
            return true;
        }else {return false;}
    }
}
