package santorini.model.godCards;

import santorini.Turno;
import santorini.model.*;

import java.util.ArrayList;

public class Apollo extends God {
    Cell start;
    Cell end;
    Pawn myPawn;
    Pawn otherPawn;
    boolean apolloEffect;
    boolean validationApolloMove = false;


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
        validationApolloMove = false;
        int x2 = turno.getMove().getTargetX();
        int y2 = turno.getMove().getTargetY();
        int idP = turno.getMove().getIdPawn();
        end = turno.getTable().getTableCell(x2, y2);
        otherPawn = turno.getTable().getTableCell(x2, y2).getPawn();
        myPawn = turno.getGamer().getPawn(idP);
        start = turno.getTable().getTableCell(myPawn.getRow(), myPawn.getColumn());
        ArrayList<Cell> nearCells = turno.getTable().searchAdjacentCells(start);
        if ((nearCells.contains(end)) &&
                (turno.getTable().getTableCell(x2, y2).getPawn() != null) &&
                (otherPawn.getIdGamer() != turno.getGamer().getIdGamer())) {
            turno.getTable().getTableCell(x2, y2).setFree(true);
            turno.getTable().getTableCell(x2, y2).setPawn(null);
            apolloEffect = true;
        } else {
            apolloEffect = false;
        }
        if (apolloEffect) {
            do {
                turno.baseMovement(turno.getMove());
                turno.getValidationMove();
            } while (!turno.isValidationMove()); //theoretically baseMovement accepts the movement at first strike,so I can remove the do-while
            turno.getGamer().setSteps(0);
        }
    }

    /**
     * Features added by card after its owner does his moves
     *
     * @param turno the current turn
     */
    public void afterOwnerMoving(Turno turno) {
        if ((apolloEffect) && (turno.isValidationMove())) {
            turno.getTable().getTableCell(start.getX(), start.getY()).setPawn(otherPawn);
            turno.getTable().getTableCell(start.getX(), start.getY()).setFree(false);
            turno.getTable().getTableCell(start.getX(), start.getY()).getPawn().setRow(start.getX());
            turno.getTable().getTableCell(start.getX(), start.getY()).getPawn().setColumn(start.getY());
            turno.getTable().getTableCell(start.getX(), start.getY()).getPawn().setPastLevel(end.getLevel());
            turno.getTable().getTableCell(start.getX(), start.getY()).getPawn().setPastLevel(start.getLevel());
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
}
