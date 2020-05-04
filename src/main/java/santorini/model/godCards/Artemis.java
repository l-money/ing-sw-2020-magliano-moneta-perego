package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Cell;
import santorini.model.Gamer;
import santorini.model.God;
import santorini.model.Mossa;

public class Artemis extends God {
    private Cell start;
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

    @Override
    public Mossa getEffectMove() {
        return move2;
    }

    @Override
    public void setEffectMove(Mossa effectMove) {
        this.move2 = effectMove;
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
        artemisEffect = false;
        //request a movement from the gamer
        //TODO uncomment effectMove = turno.moveRequest();
        //effectMove = turno.moveRequest();
        turno.setMove(effectMove);
            do {
                artemisEffect = turno.godCardEffect(getEffectMove(), artemisEffect, 0, start);
                //if the movement is not possible or correct, artemisEffect is false and he have to remake the movement
                if (!artemisEffect) {
                    turno.sendFailed();
                    turno.setCount(turno.getCount() + 1);
                    //ask another movement
                    //TODO uncomment effectMove = turno.moveRequest();
                    //effectMove = turno.moveRequest();
                    turno.setMove(effectMove);
                }
                //else the movement is correct and artemisEffect is true
                //until the movement is correct, the gamer have to insert the correct movement
            } while (!artemisEffect && turno.getCount() <= 2);
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
