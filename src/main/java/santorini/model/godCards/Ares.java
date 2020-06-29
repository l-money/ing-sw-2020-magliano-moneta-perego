package santorini.model.godCards;

import santorini.controller.Turno;
import santorini.model.*;
import java.util.ArrayList;

/**
 * Class Ares
 */

public class Ares extends God {

    public Ares() {
        super("Ares", "Fine del tuo turno:\n" +
                "puoi rimuovere un blocco libero\n" +
                "(non una cupola)\n" +
                "adiacente al lavoratore che non hai mosso");
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
            //print table status
            turno.printTableStatusTurn(true);
            //change pawn reference
            turno.getGameHandler().switchPawn(turno.getGamer());
            //save start cell
            Cell start = otherPawnPosition(turno.getGamer(), turno.getMove(), turno.getTable());
            //control if I can remove buildings
            if (floorCells(start, turno.getTable()) == 0) {
                turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Non puoi demolire nulla intorno a te" + "\u001B[0m");
            } else {
                //Ares's effect
                turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Hai Ares, puoi demolire una costruzione\n" +
                        "adiacente alla pedina che non hai mosso.\n" +
                        "Se non vuoi demolire scegli l'opzione 'Salta'." + "\u001B[0m");
                turno.setCount(0);
                boolean aresEffect = false;
                boolean printStatus = false;
                do {
                    turno.getGameHandler().sendMessage(turno.getGamer(), "Stai giocando con la pedina " + start.getPawn().getIdPawn());
                    Mossa aresBuild = turno.buildingRequest();
                    if (turno.nullEffectForGodCards(aresBuild)) {
                        aresEffect = true;
                        turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Effetto annullato" + "\u001B[0m");
                        turno.setValidationBuild(true);
                    } else {
                        Cell end = turno.getTable().getTableCell(aresBuild.getTargetX(), aresBuild.getTargetY());
                        if (end.isComplete() || end.getPawn() != null || end.getLevel() == 0) {
                            turno.getGameHandler().sendFailed(turno.getGamer(), "##Non puoi demolire qui##");
                            turno.getValidation(false);
                        } else {
                            aresEffect = true;
                            printStatus = true;
                            int l = end.getLevel() - 1;
                            turno.getTable().getTableCell(aresBuild.getTargetX(), aresBuild.getTargetY()).setLevel(l);
                        }
                    }
                    if (aresEffect && printStatus) {
                        turno.setMove(aresBuild);
                        turno.getGameHandler().getGame().broadcastMessage("\u001B[34m" + "Effetto di Ares" + "\u001B[0m");
                        turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha demolito un livello in: " +
                                "[" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
                        turno.printTableStatusTurn(true);
                        turno.setValidationBuild(true);
                    }
                } while (!aresEffect && turno.getCount() < 3);
                turno.methodLoser(aresEffect, turno.getCount(), turno.getGamer());
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
     * method floorCells
     * control if there are cells with buildings
     *
     * @param start my cell
     * @param t the table
     * @return number of cells with buildings
     */
    private int floorCells(Cell start, Table t) {
        int k = 0;
        ArrayList<Cell> adjacentCells;
        adjacentCells = t.searchAdjacentCells(start);
        for (Cell adjacentCell : adjacentCells) {
            if (adjacentCell.getLevel() > 0 && adjacentCell.getLevel() <= 3 && adjacentCell.getPawn() == null && !adjacentCell.isComplete()) {
                k++;
            }
        }
        return k;
    }

    /**
     * method otherPawnPosition
     *
     * @param g the current gamer
     * @param m the last move (movement or building)
     * @param t the current table
     * @return the cell position of the other pawn
     */
    private Cell otherPawnPosition(Gamer g, Mossa m, Table t) {
        Pawn p = g.otherPawn(g.getPawn(m.getIdPawn()));
        return t.getTableCell(p.getRow(), p.getColumn());
    }
}
