package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Gamer;
import santorini.model.God;
import santorini.model.Mossa;

import java.io.IOException;

public class Demeter extends God {
    private int startX;
    private int startY;
    private boolean demeterValidation;
    private boolean demeterEffect;
    private Mossa move2;

    @Override
    public String getName() {
        return "Demeter";
    }

    @Override
    public String getDescription() {
        return "Tua costruzione: il tuo lavoratore\npuò costruire una volta in più\nma non nella stessa cella";
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
        startX = turno.getGamer().getPawn(turno.getIdStartPawn()).getRow();
        startY = turno.getGamer().getPawn(turno.getIdStartPawn()).getColumn();
    }

    /**
     * Features added by card after its owner starts building
     *
     * @param turno
     */
    public void afterOwnerBuilding(Turno turno) {
        turno.getGamer().setBuilds(1);
        do {
            demeterEffect = true;
            demeterValidation = false;
            try {
                move2 = turno.getGameHandler().richiediMossa(Mossa.Action.MOVE, getOwner());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (move2.getIdPawn() != turno.getIdStartPawn()) {
                demeterEffect = false;
            } else {
                if ((move2.getTargetX() < 0) || (move2.getTargetY() < 0)) {
                    demeterEffect = false;
                } else {
                    if ((startX == move2.getTargetX()) &&
                            (startY == move2.getTargetY())) { //if the pawn comes back to the first position, starts artemis effect
                        demeterEffect = false;
                    } else {
                        demeterEffect = true;
                    }
                }
            }
            if (demeterEffect) {
                turno.baseBuilding(move2);
                turno.getValidationBuild();
                demeterValidation = turno.isValidationBuild();
            }
        } while (!demeterValidation);
        turno.getGamer().setBuilds(1);
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
