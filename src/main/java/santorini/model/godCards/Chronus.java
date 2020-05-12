package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Gamer;

public class Chronus extends God {
    private int towers = 0;

    public Chronus() {
        super("Chronus", "Condizione di vittoria: vinci anche quando\n" +
                "ci sono almeno cinque torri complete sul tabellone");
    }


    /**
     * Initialize player variables with card
     *
     * @param turno player owner of card
     */
    @Override
    public void initializeOwner(Turno turno) {
        towers = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if ((turno.getTable().getTableCell(i, j).isComplete()) &&
                        (turno.getTable().getTableCell(i, j).getLevel() == 3)) {
                    towers++;
                }
            }
        }
        if (towers >= 5) {
            turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Effetto di Chronus " + "\u001B[0m");
            turno.getGamer().setWinner(true);
            turno.getGameHandler().getGame().setWinner(turno.getGamer());
        } else {
            towers = 0;
        }
    }

    /**
     * Features added by card before its owner does his moves
     *
     * @param turno
     */
    @Override
    public void beforeOwnerMoving(Turno turno) {
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
        if (turno.isValidationBuild()) {
            //broadcast message of building
            turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha costruito in: " +
                    "[" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
            towers = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if ((turno.getTable().getTableCell(i, j).isComplete()) &&
                            (turno.getTable().getTableCell(i, j).getLevel() == 3)) {
                        towers++;
                    }
                }
            }
            if (towers >= 5) {
                turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Effetto di Chronus " + "\u001B[0m");
                turno.getGamer().setWinner(true);
                turno.getGameHandler().getGame().setWinner(turno.getGamer());
            } else {
                towers = 0;
            }
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