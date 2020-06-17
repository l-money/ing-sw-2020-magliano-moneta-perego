package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Gamer;
import santorini.model.Table;

public class Chronus extends God {

    public Chronus() {
        super("Chronus", "Condizione di vittoria:\n" +
                "vinci anche quando\n" +
                "ci sono almeno cinque torri complete sul tabellone");
    }


    /**
     * Initialize player variables with card
     *
     * @param turno player owner of card
     */
    @Override
    public void initializeOwner(Turno turno) {
        if (ChronosWins(turno.getTable())) {
            turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Effetto di Chronus " + "\u001B[0m");
            turno.getGamer().setWinner(true);
            turno.getGameHandler().getGame().setWinner(turno.getGamer());
        }
    }

    /**
     * Features added by card before its owner does his moves
     *
     * @param turno the current turn
     */
    @Override
    public void beforeOwnerMoving(Turno turno) {
    }

    /**
     * Features added by card after its owner does his moves
     *
     * @param turno the current turn
     */
    @Override
    public void afterOwnerMoving(Turno turno) {
        if (turno.isValidationMove()) {
            //broadcast message of movement
            turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha mosso: " + turno.getMove().getIdPawn() +
                    " in [" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
            //print status of the table
            turno.printTableStatusTurn(true);
        }
    }

    /**
     * Features added by card before its owner starts building
     *
     * @param turno the current turn
     */
    @Override
    public void beforeOwnerBuilding(Turno turno) {
    }

    /**
     * Features added by card after its owner starts building
     *
     * @param turno the current turn
     */
    @Override
    public void afterOwnerBuilding(Turno turno) {
        if (turno.isValidationBuild()) {
            //broadcast message of building
            turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha costruito in: " +
                    "[" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
            if (ChronosWins(turno.getTable())) {
                turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Effetto di Chronus " + "\u001B[0m");
                turno.getGamer().setWinner(true);
                turno.getGameHandler().getGame().setWinner(turno.getGamer());
            } else {
                //print status of the table
                turno.printTableStatusTurn(true);
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

    /**
     * method ChronosWins
     *
     * @param t the current table
     * @return true if there are at least five towers (complete building) on the table, otherwise false
     */
    private boolean ChronosWins(Table t) {
        int towers = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if ((t.getTableCell(i, j).isComplete()) &&
                        (t.getTableCell(i, j).getLevel() == 3)) {
                    towers++;
                }
            }
        }
        return towers >= 5;
    }

}