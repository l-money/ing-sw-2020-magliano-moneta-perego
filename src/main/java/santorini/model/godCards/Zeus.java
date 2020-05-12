package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Cell;
import santorini.model.Gamer;
import santorini.model.Mossa;

public class Zeus extends God {
    private Cell myCell;
    private boolean zeusEffect = false;
    private boolean printStatus = true;
    private Mossa zeusBuild;

    public Zeus() {
        super("Zeus", "Tua costruzione: il tuo lavoratore può costruire\n" +
                "sotto di se nella casella attuale, aumentandola di un livello");
    }

    /**
     * Initialize player variables with card
     *
     * @param turno player owner of card
     */
    @Override
    public void initializeOwner(Turno turno) {

    }

    /**
     * Features added by card before its owner does his moves
     *
     * @param turno
     */
    @Override
    public void beforeOwnerMoving(Turno turno) {
        zeusEffect = false;
        printStatus = true;

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
            //Zeus effect
            if (turno.getTable().getTableCell(turno.getMove().getTargetX(), turno.getMove().getTargetY()).getLevel() == 3) {
                turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Non puoi costruire sotto di te una cupola" + "\u001B[0m");
                zeusEffect = true;
            } else {
                zeusEffect = false;
                myCell = turno.getTable().getTableCell(turno.getMove().getTargetX(), turno.getMove().getTargetY());
                turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Hai Zeus, puoi costruire un livello sotto di te.\n" +
                        "Se vuoi costruire scegli una casella adiacente qualsiasi." +
                        "\nSe non vuoi costruire scegli l'opzione 'No'." + "\u001B[0m");
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
        if (!zeusEffect) {
            zeusBuild = turno.getMove();
            if (turno.nullEffectForGodCards(zeusBuild)) {
                zeusEffect = true;
                printStatus = false;
                turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Effetto annullato" + "\u001B[0m");
                turno.getGamer().setBuilds(1);
                turno.setMove(turno.buildingRequest());
            } else {
                turno.getGamer().getPawn(zeusBuild.getIdPawn()).setPastLevel(myCell.getLevel());
                int l = myCell.getLevel() + 1;
                turno.getTable().setACell(myCell.getX(), myCell.getY(), l, false, myCell.isComplete(), myCell.getPawn());
                zeusEffect = true;
                printStatus = true;
                turno.getGamer().setBuilds(0);
            }
            if (zeusEffect && printStatus) {
                turno.setMove(zeusBuild);
                //broadcast message of building
                turno.getGameHandler().getGame().broadcastMessage("Per effetto di Zeus.");
                turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha costruito in: " +
                        "[" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
                //print status of the table
                turno.printTableStatusTurn(true);
            }
        }
    }

    /**
     * Features added by card after its owner starts building
     *
     * @param turno
     */
    @Override
    public void afterOwnerBuilding(Turno turno) {
        if (turno.isValidationBuild() && zeusEffect && !printStatus) {
            //broadcast message of building
            turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha costruito in: " +
                    "[" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
            //print status of the table
            turno.printTableStatusTurn(true);
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
