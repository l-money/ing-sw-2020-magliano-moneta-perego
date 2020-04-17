package santorini.model.godCards;

import santorini.Turno;
import santorini.model.*;

import java.util.ArrayList;

public class Atlas extends God {
    private boolean atlasEffect;

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
        atlasEffect = false;

    }

    /**
     * Features added by card after its owner does his moves
     *
     * @param turno current turn
     */
    public void afterOwnerMoving(Turno turno) {

    }

    /**
     * Features added by card before its owner starts building
     *
     * @param turno current turn
     */
    public void beforeOwnerBuilding(Turno turno) {
        Pawn myPawn;
        Cell myPosition;
        Cell myDestination;
        myPawn = turno.getMove().getPawn();
        myPosition = turno.getTable().getTableCell(myPawn.getRow(), myPawn.getColumn());
        myDestination = turno.getMove().getTarget();
        if (turno.getMove().getAction() == Mossa.Azione.BUILD) {
            ArrayList<Cell> nearCells = turno.getTable().searchAdjacentCells(myPosition);
            for (Cell nearCell : nearCells) {
                if ((myDestination == nearCell) &&
                        (nearCell.isFree()) &&
                        (nearCell.getLevel() <= 3) &&
                        (nearCell.getLevel() >= 0) &&
                        (!nearCell.isComplete())) {
                    turno.getTable().getTableCell(nearCell.getX(), nearCell.getY()).setComplete(true);
                    turno.getTable().getTableCell(nearCell.getX(), nearCell.getY()).setFree(false);
                    atlasEffect = true;
                    turno.getGamer().setBuilds(0);
                }
            }
        }
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
}
