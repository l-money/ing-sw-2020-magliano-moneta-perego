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


    @Override
    public String getName() {
        return "Apollo";
    }

    @Override
    public String getDescription() {
        return "Tuo spostamento: il tuo lavoratore può spostarsi nella casella di un lavoratore avversario\n" +
                "(usando le normali regole di spostamento) e costringerlo ad occupare la casella appena liberata\n " +
                "scambiando le posizioni";
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
     * @param turno the current turn
     */
    public void beforeOwnerMoving(Turno turno) {
        do {
            int i = turno.getMove().getIdPawn();
            int x = turno.getMove().getTargetX();
            int y = turno.getMove().getTargetY();
            end = turno.getTable().getTableCell(x, y);//save end cell
            otherPawn = turno.getTable().getTableCell(x, y).getPawn();//save other pawn
            myPawn = turno.getGamer().getPawn(i);//save myPawn
            start = turno.getTable().getTableCell(myPawn.getRow(), myPawn.getColumn());//save myCell
            apolloEffect = false;
            ArrayList<Cell> nearCells = turno.getTable().searchAdjacentCells(start);

            if ((nearCells.contains(end)) && (!end.isFree()) && (end.getPawn() == otherPawn) && (end.getPawn() != null) &&
                    (otherPawn.getIdGamer() != myPawn.getIdGamer()) &&
                    (end.getLevel() - start.getLevel() <= 1)) {
                turno.getTable().getTableCell(x, y).setFree(true);
                turno.getTable().getTableCell(x, y).setPawn(null);
                apolloEffect = true;
            }
                turno.baseMovement(turno.getMove());
                turno.getValidationMove(turno.isValidationMove());
        } while (!turno.isValidationMove() && turno.getCount() <= 2);
        turno.getGamer().setSteps(0);
    }

    /**
     * Features added by card after its owner does his moves
     *
     * @param turno the current turn
     */
    public void afterOwnerMoving(Turno turno) {

        if ((apolloEffect) && (turno.isValidationMove())) {
            otherPawn.setPastLevel(end.getLevel());
            turno.getTable().setACell(start.getY(), start.getY(), start.getLevel(), false, false, otherPawn);

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

