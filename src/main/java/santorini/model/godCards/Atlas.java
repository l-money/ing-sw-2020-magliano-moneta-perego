package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Gamer;
import santorini.model.God;
import santorini.model.Mossa;

public class Atlas extends God {
    private Mossa buildDome;
    private boolean atlasEffect;

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
        atlasEffect = false;
        //request a movement from the gamer
        effectMove = turno.buildingRequest();
        turno.setMove(effectMove);
            do {
                atlasEffect = turno.godCardEffect(getEffectMove(), atlasEffect, 1, null);
                if (!atlasEffect) {
                    turno.sendFailed();
                    turno.setCount(turno.getCount() + 1);
                    //ask another movement
                    //TODO uncomment effectMove = turno.moveRequest();
                    effectMove = turno.buildingRequest();
                    turno.setMove(effectMove);
                    ;
                } else {
                    turno.getGamer().setBuilds(0);
                }
            } while (!atlasEffect && turno.getCount() <= 2);
        //turno.getGamer().setBuilds(0);
        }

    /**
     * Features added by card after its owner starts building
     *
     * @param turno current turn
     */
    public void afterOwnerBuilding(Turno turno) {
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
