package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Gamer;

//P
public class Pan extends God {

    public Pan() {
        super("Pan", "Condizione di vittoria: vinci\n" +
                "anche se il tuo lavoratore scende di due o più livelli");
    }

    /**
     * Initialize player variables with card
     *
     * @param turno player owner of card
     */
    public void initializeOwner(Turno turno) {

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
        if (turno.isValidationMove()) {
            //broadcast message of movement
            turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha mosso: " + turno.getMove().getIdPawn() +
                    " in [" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
        }

        if (turno.isValidationMove()) {
            int i = turno.getMove().getIdPawn();
            int k = turno.getGamer().getPawn(i).getPastLevel() - turno.getGamer().getPawn(i).getPresentLevel();
            if ((k >= 2) && turno.getGamer().getPawn(i).getPresentLevel() == 0) {
                turno.getGamer().setWinner(true);
                turno.getGameHandler().getGame().broadcastMessage("\u001B[34m" + "Effetto di Pan" + "\u001B[0m");
                turno.getGameHandler().getGame().setWinner(turno.getGamer());
            } else {
                //print status of the table
                turno.printTableStatusTurn(turno.isValidationMove());
            }
        }
    }

    /**
     * Features added by card before its owner starts building
     *
     * @param turno current turn
     */
    public void beforeOwnerBuilding(Turno turno) {

    }

    /**
     * Features added by card after its owner starts building
     *
     * @param turno current turn
     */
    public void afterOwnerBuilding(Turno turno) {
        if (turno.isValidationBuild()) {
            turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha costruito in: " +
                    "[" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
            //turno.printTableStatusTurn(turno.isValidationBuild());
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
