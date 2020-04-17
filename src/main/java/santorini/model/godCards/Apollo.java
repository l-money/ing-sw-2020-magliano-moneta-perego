package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Cell;
import santorini.model.Gamer;
import santorini.model.God;
import santorini.model.Pawn;

import java.util.ArrayList;

public class Apollo extends God {
    Cell start;
    Cell end;
    Pawn myPawn;
    Pawn otherPawn;
    boolean apolloEffect;


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
     * @param turno the current turn
     */
    public void beforeOwnerMoving(Turno turno) {
        apolloEffect = false;
        //Prendo in ingresso mossa con un movimento
        myPawn = turno.getMove().getPawn();
        start = turno.getTable().getTableCell(myPawn.getRow(), myPawn.getColumn());
        end = turno.getMove().getTarget();
        otherPawn = turno.getTable().getTableCell(end.getX(), end.getY()).getPawn();
        ArrayList<Cell> nearCells = turno.getTable().searchAdjacentCells(start);
        if ((nearCells.contains(end)) && (otherPawn.getIdGamer() != myPawn.getIdGamer())) {
            end.setPawn(null);
            end.setFree(true);
            apolloEffect = true;
        } else {
            apolloEffect = false;
        }

    }

    /**
     * Features added by card after its owner does his moves
     *
     * @param turno the current turn
     */
    public void afterOwnerMoving(Turno turno) {
        if (!apolloEffect) {
        } else {
            int x = start.getX();
            int y = start.getY();
            turno.getTable().getTableCell(x, y).setFree(false);
            otherPawn.setRow(x);
            otherPawn.setColumn(y);
            otherPawn.setPastLevel(start.getLevel());
            otherPawn.setPresentLevel(end.getLevel());
            turno.getTable().getTableCell(x, y).setPawn(otherPawn);
            apolloEffect = false;
        }
    }

    /**
     * Features added by card before its owner starts building
     *
     * @param turno the current turn
     */
    public void beforeOwnerBuilding(Turno turno) {

    }

    /**
     * Features added by card after its owner starts building
     *
     * @param turno the current turn
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
}
