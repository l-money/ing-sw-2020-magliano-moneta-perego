package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Gamer;
import santorini.model.God;

public class Athena extends God {

    public Athena() {
        super("Athena", "Tuo avversario :se nel tuo ultimo turno uno dei tuoi lavoratori è salito di livello,\n" +
                "in questo turno i lavoratori avversari non possono salire di livello");
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
        turno.setAthenaEffect(false);
    }

    /**
     * Features added by card after its owner does his moves
     *
     * @param turno current turn
     */
    public void afterOwnerMoving(Turno turno) {
        if (turno.isValidationMove()) {
            int id = turno.getMove().getIdPawn();
            if (turno.getGamer().getPawn(id).getPresentLevel() - turno.getGamer().getPawn(id).getPastLevel() == 1) {
                //athenaEffect is true
                turno.setAthenaEffect(true);
            } else {
                turno.setAthenaEffect(false);
            }
        }
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
