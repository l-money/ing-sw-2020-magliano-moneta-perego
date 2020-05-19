package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Cell;
import santorini.model.Gamer;
import santorini.model.Mossa;

import java.util.ArrayList;

public class Hestia extends God {
    private Cell end;
    private Mossa hestiaBuild;
    private boolean hestiaEffect;
    private boolean printStatus;

    public Hestia() {
        super("Hestia", "Tua costruzione: il tuo lavoratore può costruire una volta in più,\n" +
                "anche nella stessa casella, una costruzione aggiuntiva, però, non può avvenire\n" +
                "su una casella perimetrale");
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
     * @param turno current turn
     */
    @Override
    public void beforeOwnerMoving(Turno turno) {

    }

    /**
     * Features added by card after its owner does his moves
     *
     * @param turno current turn
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
     * @param turno current turn
     */
    @Override
    public void beforeOwnerBuilding(Turno turno) {

    }

    /**
     * Features added by card after its owner starts building
     *
     * @param turno current turn
     */
    @Override
    public void afterOwnerBuilding(Turno turno) {
        if (turno.isValidationBuild()) {
            //broadcast message of building
            turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha costruito in: " +
                    "[" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
            turno.printTableStatusTurn(true);
        }
        if (turno.isValidationBuild()) {
            turno.setCount(0);
            turno.getGamer().setBuilds(1);
            hestiaEffect = false;
            printStatus = false;
            do {
                turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Hai Hestia, puoi costruire una volta in più\n" +
                        "ma non su una casella perimetrale.\n" +
                        "Se non vuoi costruire scegli l'opzione 'No'." + "\u001B[0m");
                hestiaBuild = turno.buildingRequest();
                if (turno.nullEffectForGodCards(hestiaBuild)) {
                    hestiaEffect = true;
                    printStatus = false;
                    turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Effetto annullato" + "\u001B[0m");
                } else {
                    end = turno.getTable().getTableCell(hestiaBuild.getTargetX(), hestiaBuild.getTargetY());
                    ArrayList<Cell> perimetralCells = turno.getTable().tablePerimetralCells(turno.getTable());
                    if (perimetralCells.contains(end)) {
                        hestiaEffect = false;
                        turno.getValidation(false);
                        turno.getGameHandler().sendFailed(turno.getGamer(), "##Non puoi costruire su una casella perimetrale##");
                    } else {
                        turno.baseBuilding(hestiaBuild);
                        turno.getValidation(turno.isValidationBuild());
                        hestiaEffect = turno.isValidationBuild();
                        printStatus = hestiaEffect;
                    }
                }
                if (hestiaEffect && printStatus) {
                    turno.setMove(hestiaBuild);
                    //broadcast message of building
                    turno.getGameHandler().getGame().broadcastMessage("\u001B[34m" + "Effetto di Hestia" + "\u001B[0m");
                    turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha costruito in: " +
                            "[" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
                    //print status of the table
                    turno.printTableStatusTurn(true);
                }
            } while (!hestiaEffect && turno.getCount() < 5);
            turno.methodLoser(hestiaEffect, turno.getCount(), turno.getGamer());
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
