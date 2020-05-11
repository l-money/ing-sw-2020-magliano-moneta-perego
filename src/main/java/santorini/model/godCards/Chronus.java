package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Gamer;
import santorini.model.God;
import santorini.model.Table;

public class Chronus extends God {
    private int k = 0;

    public Chronus() {
        super("Chronus", "Condizione di vittoria: vinci anche quando\n" +
                "ci sono almeno cinque torri complete sul tabellone");
    }


    /**
     * Initialize player variables with card
     *
     * @param g player owner of card
     */
    @Override
    public void initializeOwner(Gamer g) {

    }

    /**
     * Features added by card before its owner does his moves
     *
     * @param turno
     */
    @Override
    public void beforeOwnerMoving(Turno turno) {
        k = 0;

    }

    /**
     * Features added by card after its owner does his moves
     *
     * @param turno
     */
    @Override
    public void afterOwnerMoving(Turno turno) {
        if (turno.isValidationMove()) {
            //broadcast message of movement
            turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha mosso: " + turno.getMove().getIdPawn() +
                    " in [" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
            //print status of the table
            turno.printTableStatusTurn(turno.isValidationMove());
            Table t = turno.getTable();
            int k = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; i < 5; j++) {
                    if ((t.getTableCell(i, j).isComplete()) && (t.getTableCell(i, j).getLevel() == 3)) {
                        k++;
                    }
                }
            }
            if (k >= 5) {
                turno.getGameHandler().getGame().broadcastMessage("\u001B[34m" + "Effetto di Chronos" + "\u001B[0m");
                turno.getGameHandler().getGame().setWinner(turno.getGamer());
            } else {
                k = 0;
            }
        }

    }

    /**
     * Features added by card before its owner starts building
     *
     * @param turno
     */
    @Override
    public void beforeOwnerBuilding(Turno turno) {

    }

    /**
     * Features added by card after its owner starts building
     *
     * @param turno
     */
    @Override
    public void afterOwnerBuilding(Turno turno) {
        if ((k == 0) && turno.isValidationBuild()) {
            //broadcast message of building
            turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha costruito in: " +
                    "[" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
        }
    }

    /**
     * Features added by card before other player does his moves
     *
     * @param other player to customize
     */
    @Override
    public void beforeOtherMoving(Gamer other) {

    }

    /**
     * Features added by card after other player does his moves
     *
     * @param other player to customize
     */
    @Override
    public void afterOtherMoving(Gamer other) {

    }

    /**
     * Features added by card before other player starts building
     *
     * @param other player to customize
     */
    @Override
    public void beforeOtherBuilding(Gamer other) {

    }

    /**
     * Features added by card before other player starts building
     *
     * @param other player to customize
     */
    @Override
    public void afterOtherBuilding(Gamer other) {

    }
}