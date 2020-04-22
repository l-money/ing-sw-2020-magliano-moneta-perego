package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Cell;
import santorini.model.Gamer;
import santorini.model.God;
import santorini.model.Pawn;

import java.util.ArrayList;

public class Minotaur extends God {
    Cell start;
    Cell end;
    Cell nextCell;
    Pawn myPawn;
    Pawn otherPawn;
    boolean minoEffect;
    /**
     * Initialize player variables with card
     *
     * @param g player owner of card
     */
    public void initializeOwner(Gamer g) {

    }

    /**
     * Features added by card before its owner does his moves
     *
     * @param turno
     */
    public void beforeOwnerMoving(Turno turno) {
        end = turno.getTable().getTableCell(end.getX(), end.getY());
        myPawn = turno.getGamer().getPawn(turno.getMove().getIdPawn());
        otherPawn = turno.getTable().getTableCell(end.getX(), end.getY()).getPawn();
        start = turno.getTable().getTableCell(myPawn.getRow(), myPawn.getColumn());
        do {
            turno.getTable().getTableCell(end.getX(), end.getY()).setFree(false);
            turno.getTable().getTableCell(end.getX(), end.getY()).setPawn(otherPawn);
            minoEffect = false;
            ArrayList<Cell> nearCells = turno.getTable().searchAdjacentCells(start);
            if (!nearCells.contains(end)) {
                minoEffect = false;
            } else {
                if (end.isFree() || end.isComplete()) {
                    minoEffect = false;
                } else {
                    if ((otherPawn.getIdGamer() == turno.getGamer().getIdGamer())) {
                        minoEffect = false;
                    } else {
                        int[] k = coordinateNextCell(start, end);
                        if ((!possiblePush(k))) {
                            minoEffect = false;
                        } else {
                            nextCell = turno.getTable().getTableCell(k[0], k[1]);
                            if (controlIsFree(nextCell)) {
                                minoEffect = true;
                            } else {
                                minoEffect = false;
                            }
                        }
                    }
                }
            }
            if (minoEffect) {
                turno.getTable().getTableCell(end.getX(), end.getY()).setFree(true);
                turno.getTable().getTableCell(end.getX(), end.getY()).setPawn(null);
                turno.baseMovement(turno.getMove());
                turno.getValidationMove();
                minoEffect = turno.isValidationMove();
            } else {
                turno.baseMovement(turno.getMove());
                turno.getValidationMove();
            }
        } while (!turno.isValidationMove());
        turno.getGamer().setSteps(0);
    }

    /**
     * Features added by card after its owner does his moves
     *
     * @param turno
     */
    public void afterOwnerMoving(Turno turno) {
        if (minoEffect && turno.isValidationMove()) {
            int x = nextCell.getX();
            int y = nextCell.getY();
            otherPawn.setPastLevel(end.getLevel());
            otherPawn.setPresentLevel(nextCell.getLevel());
            otherPawn.setRow(x);
            otherPawn.setColumn(y);
            turno.getTable().getTableCell(x, y).setFree(false);
            turno.getTable().getTableCell(x, y).setPawn(otherPawn);
        }

    }

    /**
     * Features added by card before its owner starts building
     *
     * @param turno
     */
    public void beforeOwnerBuilding(Turno turno) {

    }

    /**
     * Features added by card after its owner starts building
     *
     * @param turno
     */
    public void afterOwnerBuilding(Turno turno) {
        turno.getGamer().setSteps(1);
    }

    /**
     * Features added by card before other player does his moves
     *
     * @param other player to customize
     */
    public void beforeOtherMoving(Gamer other) {

    }

    /**
     * Features added by card after other player does his moves
     *
     * @param other player to customize
     */
    public void afterOtherMoving(Gamer other) {

    }

    /**
     * Features added by card before other player starts building
     *
     * @param other player to customize
     */
    public void beforeOtherBuilding(Gamer other) {

    }

    /**
     * Features added by card before other player starts building
     *
     * @param other player to customize
     */
    public void afterOtherBuilding(Gamer other) {

    }

    /**
     * method controlIsFree
     *
     * @param cell my cell
     * @return true if is free and not complete
     */
    public boolean controlIsFree(Cell cell) {
        if ((cell.isFree()) && (!cell.isComplete())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * method existCoordinate
     *
     * @param x my coordinate
     * @return true if 0<=x<=4, or return false
     */
    public boolean existCoordinate(int x) {
        if ((x >= -1) && (x <= 4)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * method coordinateNextCell: for the Minotaur Effect
     *
     * @param start my position
     * @param end   my destination
     * @return the coordinate, if they exist, of the next cell of end in the same direction of start
     */
    public int[] coordinateNextCell(Cell start, Cell end) {
        int[] coordinateNext = new int[2];
        int column = end.getY() - start.getY();
        int row = end.getX() - start.getX();

        if ((row == 0) && (column == 0)) {
            //start cell
            coordinateNext[0] = -10;
            coordinateNext[1] = -10;
            return coordinateNext;
        } else {
            if ((row == 0)) {
                //same row, control the column
                if ((end.getY() + column >= 0) && (end.getY() + column <= 4)) {
                    coordinateNext[0] = end.getX();
                    coordinateNext[1] = end.getY() + column;
                    return coordinateNext;
                } else {
                    coordinateNext[0] = -10;
                    coordinateNext[1] = -10;
                    return coordinateNext;
                }
            } else {
                if ((column == 0)) {
                    //same column, control the row
                    if ((end.getX() + row >= 0) && (end.getX() + row <= 4)) {
                        coordinateNext[0] = end.getX() + row;
                        coordinateNext[1] = end.getY();
                        return coordinateNext;
                    } else {
                        coordinateNext[0] = -10;
                        coordinateNext[1] = -10;
                        return coordinateNext;
                    }
                } else {
                    if ((row == column)) {
                        //movement on the \diagonal
                        int i = end.getX() + row;
                        int j = end.getY() + column;
                        if ((i == j) && (i >= 0) && (i <= 4)) {
                            coordinateNext[0] = i;
                            coordinateNext[1] = j;
                            return coordinateNext;
                        } else {
                            coordinateNext[0] = -10;
                            coordinateNext[1] = -10;
                            return coordinateNext;
                        }
                    } else {
                        if ((row + column == 0)) {
                            //movement on the /diagonal
                            int i = end.getX() + row;
                            int j = end.getY() + column;
                            if ((i >= 0) && (i <= 4) && (j >= 0) && (j <= 4)) {
                                coordinateNext[0] = i;
                                coordinateNext[1] = j;
                                return coordinateNext;
                            } else {
                                coordinateNext[0] = -10;
                                coordinateNext[1] = -10;
                                return coordinateNext;
                            }
                        } else {
                            //the movement is impossible
                            coordinateNext[0] = -10;
                            coordinateNext[1] = -10;
                            return coordinateNext;
                        }
                    }
                }
            }
        }
    }

    /**
     * @param x the two coordinate of the next cell
     * @return true if: the next cell has row and column and, the next cell exists; or return false
     */
    public boolean possiblePush(int[] x) {
        if ((x.length == 2) && (x[0] != -10) && (x[1] != -10)) {
            return true;
        } else {
            return false;
        }
    }
}
