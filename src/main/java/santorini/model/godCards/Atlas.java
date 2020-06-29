package santorini.model.godCards;

import santorini.controller.Turno;
import santorini.model.Cell;
import santorini.model.Gamer;
import santorini.model.Mossa;

/**
 * Class Atlas
 */

public class Atlas extends God {
    private Mossa buildDome;
    private boolean atlasEffect;
    private boolean printStatus;
    private int controller = 1;

    public Atlas() {
        super("Atlas", "Tua costruzione:\n" +
                "il tuo lavoratore può costruire una cupola\n" +
                "su qualsiasi livello, compreso il terreno");
    }

    /**
     * method getEffectMove
     *
     * @return buildDome
     */
    @Override
    public Mossa getEffectMove() {
        return buildDome;
    }

    /**
     * method setEffectMove
     *
     * @param effectMove .
     */
    @Override
    public void setEffectMove(Mossa effectMove) {
        this.buildDome = effectMove;
    }

    /**
     * Initialize player variables with card
     *
     * @param turno player owner of card
     */
    public void initializeOwner(Turno turno) {
        controller = 0;
    }

    /**
     * Features added by card before its owner does his moves
     *
     * @param turno current turn
     */
    public void beforeOwnerMoving(Turno turno) {
        printStatus = true;
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
            //Atlas effect
            turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Hai Atlas, puoi costruire una cupola dove vuoi.\n" +
                    "Se non vuoi costruire la cupola scegli l'opzione 'Salta'" + "\u001B[0m" + "\n");
        }
    }

    /**
     * Features added by card before its owner starts building
     *
     * @param turno current turn
     */
    public void beforeOwnerBuilding(Turno turno) {
        if (controller == 0) {
            turno.setCount(0);
            atlasEffect = false;
            printStatus = false;
            buildDome = turno.getMove();
            do {
                buildDome = turno.getMove();
                if (turno.nullEffectForGodCards(buildDome)) {
                    atlasEffect = true;
                    printStatus = false;
                    controller++;
                    turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Effetto annullato" + "\u001B[0m");
                    turno.setValidationBuild(false);
                    turno.setCount(0);
                    turno.setMove(turno.buildingRequest());
                } else {
                    if (!turno.controlStandardParameter(buildDome)) {
                        atlasEffect = false;
                        turno.getValidation(false);
                        turno.getGameHandler().sendFailed(turno.getGamer(), "##Coordinate non valide##");
                        turno.setMove(turno.buildingRequest());
                    } else {
                        Cell end = turno.getTable().getTableCell(buildDome.getTargetX(), buildDome.getTargetY());
                        if (end.isComplete() || end.getPawn() != null) {
                            atlasEffect = false;
                            turno.getValidation(false);
                            turno.getGameHandler().sendFailed(turno.getGamer(), "##Non puoi costruire qui##");
                            turno.setMove(turno.buildingRequest());
                        } else {
                            turno.getTable().getTableCell(end.getX(), end.getY()).setComplete(true);
                            atlasEffect = true;
                            printStatus = true;
                            controller++;
                        }
                    }
                }
                if (atlasEffect && printStatus) {
                    turno.getGameHandler().getGame().broadcastMessage("\u001B[34m" + "Effetto di Atlas" + "\u001B[0m");
                    //broadcast message of building
                    turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha costruito in: " +
                            "[" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
                    //print table status
                    turno.printTableStatusTurn(true);
                    turno.getGamer().setBuilds(0);
                    turno.setValidationBuild(true);
                }
            } while (!atlasEffect && turno.getCount() < 5);
            turno.methodLoser(atlasEffect, turno.getCount(), turno.getGamer());
        }
    }

    /**
     * Features added by card after its owner starts building
     *
     * @param turno current turn
     */
    public void afterOwnerBuilding(Turno turno) {
        if (turno.isValidationBuild() && atlasEffect && !printStatus) {
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
