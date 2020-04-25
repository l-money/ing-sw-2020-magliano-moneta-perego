package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Gamer;
import santorini.model.God;
import santorini.model.Mossa;

import java.io.IOException;

public class Artemis extends God {
    private int startX;
    private int startY;
    private boolean artemisValidation;
    private boolean artemisEffect;
    private Mossa move2;

    @Override
    public String getName() {
        return "Artemis";
    }

    @Override
    public String getDescription() {
        return "Tuo spostamento: il tuo lavoratore può spostarsi una volta in più\n" +
                "ma non può tornare alla casella da cui è partito";
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
     * @param turno
     */
    public void beforeOwnerMoving(Turno turno) {
        startX = turno.getGamer().getPawn(turno.getIdStartPawn()).getRow();
        startY = turno.getGamer().getPawn(turno.getIdStartPawn()).getColumn();
    }

    /**
     * Features added by card after its owner does his moves
     *
     * @param turno the current turn
     */
    public void afterOwnerMoving(Turno turno) {
        turno.getGamer().setSteps(1);
        do {
            artemisEffect = false;
            artemisValidation = false;
            try {
                move2 = turno.getGameHandler().richiediMossa(Mossa.Action.MOVE, getOwner());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if ((startX == move2.getTargetX()) &&
                    (startY == move2.getTargetY())) { //if the pawn comes back to the first position, starts artemis effect
                artemisEffect = true;
            } else {
                artemisEffect = false;
            }
            if ((move2.getTargetX() < 0) || (move2.getTargetY() < 0)) {
                artemisValidation = true;
            } else {
                if (!artemisEffect) {
                    turno.baseMovement(move2);
                    turno.getValidationMove();
                    artemisValidation = turno.isValidationMove();
                }
            }
        } while (!artemisValidation);
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
