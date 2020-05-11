package santorini.model.godCards;

import santorini.Turno;
import santorini.model.*;

import java.util.ArrayList;
//FINITA
public class Apollo extends God {
    private Cell start;
    private Cell end;
    private Pawn myPawn;
    private Pawn otherPawn;
    private boolean apolloEffect;

    public Apollo() {
        super("Apollo", "Tuo spostamento: il tuo lavoratore può spostarsi nella casella di un lavoratore avversario\n" +
                "(usando le normali regole di spostamento) e costringerlo ad occupare la casella appena liberata\n " +
                "scambiando le posizioni");
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
        apolloEffect = false;
            int i = turno.getMove().getIdPawn();
            int x = turno.getMove().getTargetX();
            int y = turno.getMove().getTargetY();
            end = turno.getTable().getTableCell(x, y);//save end cell
            otherPawn = turno.getTable().getTableCell(x, y).getPawn();//save other pawn
            myPawn = turno.getGamer().getPawn(i);//save myPawn
            start = turno.getTable().getTableCell(myPawn.getRow(), myPawn.getColumn());//save myCell
            ArrayList<Cell> nearCells = turno.getTable().searchAdjacentCells(start);
            if ((nearCells.contains(end)) && (!end.isFree()) && (end.getPawn() == otherPawn) && (end.getPawn() != null) &&
                    (otherPawn.getIdGamer() != myPawn.getIdGamer()) &&
                    (end.getLevel() - start.getLevel() <= 1)) {
                if ((end.getLevel() - start.getLevel() == 1) && turno.getGamer().getLevelsUp() == 0) {
                    apolloEffect = false;
                } else {
                    turno.getTable().setACell(x, y, end.getLevel(), true, end.isComplete(), null);
                    apolloEffect = true;
                }
            }
    }

    /**
     * Features added by card after its owner does his moves
     *
     * @param turno the current turn
     */
    public void afterOwnerMoving(Turno turno) {
        if ((apolloEffect) && (turno.isValidationMove())) {
            otherPawn.setPastLevel(end.getLevel());
            turno.getTable().setACell(start.getX(), start.getY(), start.getLevel(), false, false, otherPawn);
            //broadcast message of movement
            turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha mosso: " + turno.getMove().getIdPawn() +
                    " in [" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
            //print status of the table
            turno.printTableStatusTurn(turno.isValidationMove());
        }
        if (!turno.isValidationMove() && apolloEffect) {
            turno.getTable().setACell(end.getX(), end.getY(), end.getLevel(), false, end.isComplete(), otherPawn);
        }
        if ((!apolloEffect) && (turno.isValidationMove())) {
            //broadcast message of movement
            turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha mosso: " + turno.getMove().getIdPawn() +
                    " in [" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
            //print status of the table
            turno.printTableStatusTurn(turno.isValidationMove());
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
        if (turno.isValidationBuild()) {
            turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha costruito in: " +
                    "[" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
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

