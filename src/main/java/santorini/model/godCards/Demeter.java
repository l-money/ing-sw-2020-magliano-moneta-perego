package santorini.model.godCards;

import santorini.Turno;
import santorini.model.*;

import java.util.ArrayList;

public class Demeter extends God {
    Cell myPosition;
    Cell myBuildDestination;
    int levelDemeterEffect = 0;
    boolean demeterEffect = false;

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
        demeterEffect = false;
        turno.getGamer().setBuilds(1);

    }

    /**
     * Features added by card after its owner does his moves
     *
     * @param turno
     */
    public void afterOwnerMoving(Turno turno) {

    }

    /**
     * Features added by card before its owner starts building
     *
     * @param turno
     */
    public void beforeOwnerBuilding(Turno turno) {
        Pawn myPawn;
        myPawn = turno.getMove().getPawn();
        myPosition = turno.getTable().getTableCell(myPawn.getRow(), myPawn.getColumn());
        myBuildDestination = turno.getMove().getTarget();
        if (turno.getMove().getAction() == Mossa.Azione.BUILD) {
            ArrayList<Cell> nearCells = turno.getTable().searchAdjacentCells(myPosition);
            for (Cell nearCell : nearCells) {
                if ((myBuildDestination == nearCell) &&
                        (nearCell.isFree()) &&
                        (!nearCell.isComplete())) {
                    turno.getTable().getTableCell(nearCell.getX(), nearCell.getY()).build();
                    levelDemeterEffect = turno.getTable().getTableCell(nearCell.getX(), nearCell.getY()).getLevel();
                    demeterEffect = true;
                }
            }
        }
    }

    /**
     * Features added by card after its owner starts building
     *
     * @param turno
     */
    public void afterOwnerBuilding(Turno turno) {
        if ((demeterEffect) && (turno.getGamer().getBuilds() == 0) &&
                ((turno.getTable().getTableCell(turno.getMove().getTarget().getX(), turno.getMove().getTarget().getY()).getLevel())
                        == levelDemeterEffect + 1)
        ) {
            turno.getTable().getTableCell(turno.getMove().getTarget().getX(), turno.getMove().getTarget().getY()).setLevel(
                    (turno.getTable().getTableCell(turno.getMove().getTarget().getX(), turno.getMove().getTarget().getY()).getLevel() - 1));
        }

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
