package santorini.model.godCards;

import santorini.controller.Turno;
import santorini.model.Cell;
import santorini.model.Gamer;
import santorini.model.Mossa;

public class Hephaestus extends God {
    private boolean HEffect;
    private Mossa buildingPlus;
    boolean printerStatus = true;

    public Hephaestus() {
        super("Hephaestus", "Tua costruzione:\n" +
                "il tuo lavoratore\n" +
                "può costruire un blocco aggiuntivo\n" +
                "(non una cupola) al di sopra del primo blocco.");
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
            //print status of the table
            turno.printTableStatusTurn(turno.isValidationMove());
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
            if (printerStatus) {
                //broadcast message of building
                turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha costruito in: " +
                        "[" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
                //print table
                turno.printTableStatusTurn(printerStatus);
                printerStatus = false;
            }
            HEffect = false;
            turno.setCount(0);
            int x = turno.getMove().getTargetX();
            int y = turno.getMove().getTargetY();
            Cell pastB = turno.getTable().getTableCell(x, y);
            if (pastB.isComplete()) {
                turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Costruzione " +
                        "completa" + "\u001B[0m");
            } else {
                //I could build on it again
                turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Hai Hephaestus, puoi costruire una volta in più\n" +
                        "nella casella precedentemente scelta. " +
                        "\nSe vuoi costruire, selezionala nuovamente." +
                        "\nSe non vuoi costruire scegli l'opzione 'Salta'" + "\u001B[0m");
                do {
                    buildingPlus = turno.buildingRequest();
                    if (turno.nullEffectForGodCards(buildingPlus)) {
                        HEffect = true;
                        printerStatus = false;
                        turno.getValidation(true);
                        turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Effetto annullato " + "\u001B[0m");
                    } else {
                        Cell end = turno.getTable().getTableCell(buildingPlus.getTargetX(), buildingPlus.getTargetY());
                        if (pastB != end) {
                            HEffect = false;
                            turno.getGameHandler().sendFailed(turno.getGamer(), "##Non puoi costruire " +
                                    "in un'altra casella##");
                            turno.getValidation(false);
                        } else {
                            int l = end.getLevel() + 1;
                            turno.getTable().getTableCell(buildingPlus.getTargetX(), buildingPlus.getTargetY()).setLevel(l);
                            turno.getValidation(true);
                            HEffect = true;
                            printerStatus = true;
                        }
                    }
                } while (!HEffect && turno.getCount() < 3);
            }
            if (HEffect && printerStatus) {
                turno.setMove(buildingPlus);
                //broadcast message of building
                turno.getGameHandler().getGame().broadcastMessage("\u001B[34m" + "Effetto di Hephaestus" + "\u001B[0m");
                turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha costruito in: " +
                        "[" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
                turno.printTableStatusTurn(true);

            }
            turno.methodLoser(HEffect, turno.getCount(), turno.getGamer());
            printerStatus = true;
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
