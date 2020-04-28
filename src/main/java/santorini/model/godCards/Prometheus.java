package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Gamer;
import santorini.model.God;
import santorini.model.Mossa;

import java.io.IOException;

public class Prometheus extends God {
    private Mossa build1;
    private boolean building;
    private boolean promValidation;

    @Override
    public String getName() {
        return "Prometheus";
    }

    @Override
    public String getDescription() {
        return "Tuo turno: se il tuo lavoratore\n" +
                "non sale di livello, allora puoi\n" +
                "costruire sia prima, sia dopo aver mosso\n" +
                "il tuo lavoratore";
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
        building = false;
        if (turno.isPromEffect()) {
            build1 = turno.getMove();
            if (build1.getIdPawn() == -1) {// the gamer doesn't want to build-move-build
                building = false;
            } else {
                building = true;
            }
        }
        if (!building) {
            promValidation = false;
            do {
                try {
                    turno.setMove(turno.getGameHandler().richiediMossa(Mossa.Action.MOVE, getOwner()));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                turno.baseMovement(turno.getMove());
                turno.getValidationMove();
                promValidation = turno.isValidationMove();
            } while (!promValidation);
            turno.getGamer().setSteps(0);
        } else {
            promValidation = false;
            do {
                turno.baseBuilding(turno.getMove());
                turno.getValidationMove();
                promValidation = turno.isValidationMove();
            } while (!promValidation);
            turno.getGamer().setLevelsUp(0);
        }
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

    }

    /**
     * Features added by card after its owner starts building
     *
     * @param turno
     */
    public void afterOwnerBuilding(Turno turno) {
        turno.getGamer().setLevelsUp(1);
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
