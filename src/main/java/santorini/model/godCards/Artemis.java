package santorini.model.godCards;

import santorini.Turno;
import santorini.model.*;

public class Artemis extends God {
    private Cell start;
    private boolean artemisEffect;
    private Mossa effectMove2;

    public Artemis() {
        super("Artemis", "Tuo spostamento: il tuo lavoratore può spostarsi una volta in più\n" +
                "ma non può tornare alla casella da cui è partito");
    }

    @Override
    public Mossa getEffectMove() {
        return effectMove2;
    }

    @Override
    public void setEffectMove(Mossa effectMove) {
        this.effectMove2 = effectMove;
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
        int startX = turno.getGamer().getPawn(turno.getMove().getIdPawn()).getRow();//save start position X
        int startY = turno.getGamer().getPawn(turno.getMove().getIdPawn()).getColumn();//save start position Y
        start = turno.getTable().getTableCell(startX, startY);
    }

    /**
     * Features added by card after its owner does his moves
     *
     * @param turno the current turn
     */
    public void afterOwnerMoving(Turno turno) {
        if (turno.isValidationMove()) {
            int idMove = turno.getMove().getIdPawn();
            turno.getGameHandler().getGame().updateField();
            artemisEffect = false;
            turno.setCount(0);
            turno.getGamer().setSteps(1);
            //request a movement from the gamer
            effectMove2 = turno.moveRequest();
            do {
                if (turno.nullEffectForGodCards(effectMove2)) {
                    artemisEffect = true;
                } else {
                    if (idMove != effectMove2.getIdPawn()) {
                        artemisEffect = false;
                    } else {
                        Cell end = turno.getTable().getTableCell(effectMove2.getTargetX(), effectMove2.getTargetY());
                        if ((end.getX() == start.getX()) &&
                                (end.getY() == start.getY())) {
                            artemisEffect = false;
                        } else {
                            turno.baseMovement(effectMove2);
                            turno.getValidationMove(turno.isValidationMove());
                            artemisEffect = turno.isValidationMove();
                        }
                    }
                }
                if (!artemisEffect) {
                    turno.sendFailed();
                    //ask another movement
                    effectMove2 = turno.moveRequest();
                }
                //else the movement is correct and artemisEffect is true
                //until the movement is correct, the gamer have to insert the correct movement
            } while (!artemisEffect && turno.getCount() <= 5);
            turno.setValidationMove(true);
            turno.getMove().setIdPawn(idMove);
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
