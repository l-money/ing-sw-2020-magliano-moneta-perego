package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Cell;
import santorini.model.Gamer;
import santorini.model.God;
import santorini.model.Mossa;

public class Atlas extends God {
    private Mossa buildDome;
    private boolean atlasEffect;
    //private int IDP;

    public Atlas() {
        super("Atlas", "Tua costruzione: il tuo lavoratore può costruire una cupola\n" +
                "su qualsiasi livello, compreso il terreno");
    }

    @Override
    public Mossa getEffectMove() {
        return buildDome;
    }

    @Override
    public void setEffectMove(Mossa effectMove) {
        this.buildDome = effectMove;
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
     * @param turno current turn
     */
    public void beforeOwnerMoving(Turno turno) {
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
        if (turno.isValidationMove()) {
            atlasEffect = false;
            buildDome = turno.getMove();
            do {
                if (turno.nullEffectForGodCards(buildDome)) {
                    atlasEffect = true;
                    turno.setMove(turno.buildingRequest());
                } else {
                    if (!turno.controlStandardParameter(buildDome)) {
                        atlasEffect = false;
                    } else {
                        Cell end = turno.getTable().getTableCell(buildDome.getTargetX(), buildDome.getTargetY());
                        if (end.isComplete() || !turno.getTable().getBag().controlExistBrick(4)) {
                            atlasEffect = false;
                        } else {
                            turno.getTable().getTableCell(buildDome.getTargetX(), buildDome.getTargetY()).setComplete(false);
                            turno.getTable().getBag().extractionBrick(4);
                            atlasEffect = true;
                            turno.getGamer().setBuilds(0);
                            turno.setValidationBuild(true);
                        }
                    }
                }
                if (!atlasEffect) {
                    turno.sendFailed();
                    turno.setMove(turno.buildingRequest());
                    buildDome = turno.getMove();
                }
            } while (!atlasEffect && turno.getCount() < 5);
            turno.setCount(0);
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
