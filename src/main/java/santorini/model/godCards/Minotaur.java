package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Cell;
import santorini.model.Gamer;
import santorini.model.God;
import santorini.model.Pawn;

import java.util.ArrayList;
//P
public class Minotaur extends God {
    Cell start;
    Cell end;
    Cell nextCell;
    Pawn myPawn;
    Pawn otherPawn;
    boolean minoEffect;
    boolean printerStatus = true;

    public Minotaur() {
        super("Minotaur", "Tuo spostamento: il tuo lavoratore\npuò spostarsi nella casella di un lavoratore avversario\n" +
                "(in base alle normali regole di spostamento)\n" +
                "se la casella successiva nella stessa direzione è libera.\n" +
                "Il lavoratore avversario è costretto a spostarsi in quella casella\n" +
                "(indipendentemente dal livello.");
    }


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
     * @param turno current turn
     */
    public void beforeOwnerMoving(Turno turno) {
        printerStatus = true;
        int x = turno.getMove().getTargetX();
        int y = turno.getMove().getTargetY();
        int i = turno.getMove().getIdPawn();
        int idG = turno.getGamer().getIdGamer();
        end = turno.getTable().getTableCell(x, y);
        myPawn = turno.getGamer().getPawn(i);
        otherPawn = turno.getTable().getTableCell(x, y).getPawn();
        //int idO = otherPawn.getIdGamer();
        start = turno.getTable().getTableCell(myPawn.getRow(), myPawn.getColumn());
        ArrayList<Cell> nearCells = turno.getTable().searchAdjacentCells(start);
            minoEffect = false;
        if (!nearCells.contains(end)) {
            minoEffect = false;
        } else {
            if (end.isComplete() || end.getPawn() == null) {
                minoEffect = false;
            } else {
                if (otherPawn.getIdGamer() == idG &&
                        turno.getGamer().getColorGamer().equals(otherPawn.getColorPawn())) {
                    minoEffect = false;
                } else {
                    if (end.getLevel() - start.getLevel() > 1) {
                        minoEffect = false;
                    } else {
                        if ((end.getLevel() - start.getLevel() == 1) && turno.getGamer().getLevelsUp() == 0) {
                            minoEffect = false;
                        } else {
                            int[] k = coordinateNextCell(start, end);
                            if ((!possiblePush(k))) {
                                minoEffect = false;
                            } else {
                                nextCell = turno.getTable().getTableCell(k[0], k[1]);
                                if (controlIsFree(nextCell)) {
                                    turno.getTable().getTableCell(x, y).setFree(true);
                                    turno.getTable().getTableCell(x, y).setPawn(null);
                                    minoEffect = true;
                                } else {
                                    minoEffect = false;
                                }
                            }
                        }
                    }
                }
            }
        }
        printerStatus = minoEffect;
            }

    /**
     * Features added by card after its owner does his moves
     *
     * @param turno current turn
     */
    public void afterOwnerMoving(Turno turno) {
        if (minoEffect && turno.isValidationMove()) {
            int x = nextCell.getX();
            int y = nextCell.getY();
            otherPawn.setPastLevel(end.getLevel());
            turno.getTable().setACell(x, y, nextCell.getLevel(), false, nextCell.isComplete(), otherPawn);
            turno.printTableStatusTurn(true);
            printerStatus = false;
        }
        if (!minoEffect && turno.isValidationMove()) {
            turno.printTableStatusTurn(true);
        }
    }

    /**
     * Features added by card before its owner starts building
     *
     * @param turno current turn
     */
    public void beforeOwnerBuilding(Turno turno) {

    }

    /**
     * Features added by card after its owner starts building
     *
     * @param turno current turn
     */
    public void afterOwnerBuilding(Turno turno) {
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
                //same row, printerStatus the column
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
                    //same column, printerStatus the row
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
