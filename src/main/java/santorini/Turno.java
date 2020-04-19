package santorini;

import org.junit.runner.manipulation.NoTestsRemainException;
import santorini.model.*;

import java.io.IOException;
import java.util.ArrayList;

public class Turno implements Runnable {
    private ArrayList<God> otherCards;
    private Gamer gamer;
    private Table table;
    private Pawn pawn;
    private Mossa move;
    private NetworkHandlerServer gameHandler;

    /**
     * Turn initialization for a specified player
     *
     * @param cards all god cards active in this match
     * @param gamer player that has to play
     * @param table game field
     */
    public Turno(ArrayList<God> cards, Gamer gamer, Table table, NetworkHandlerServer handler) {
        for (God g : cards) {
            if (g.equals(gamer.getMyGodCard())) {
                cards.remove(g);
            }
        }
        this.otherCards = cards;
        this.gamer = gamer;
        this.table = table;
        this.gameHandler = handler;
    }

    public Table getTable() {
        return table;
    }

    public Gamer getGamer() {
        return gamer;
    }

    public Mossa getMove() {
        return move;
    }

    public Pawn getPawn() {
        return pawn;
    }

    /**
     * Executes in new Thread all player turn with
     * all god cards features
     */
    public void run() {
        //Devo avere un oggetto mossa di tipo move da controllare
        mossa();
        winner();
        //Prima di lanciare un altro turno far controllare che il giocatore non abbia vinto
        //Se non ha vinto devo avere un oggetto mossa di tipo build da controllare
        costruzione();
    }

    private Mossa leggiMossa(Mossa.Azione action) {
        try {
            return gameHandler.richiediMossa(action, gamer);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendFailed() {
        gameHandler.sendFailed(gamer);
    }

    //TODO metto controllo di steps, builds per mossa base e costruzione base
    public void mossa() {
        Mossa m = leggiMossa(Mossa.Azione.MOVE);
        gamer.getMyGodCard().beforeOwnerMoving(this);
        for (God card : otherCards) {
            card.beforeOtherMoving(gamer);
        }
        boolean step1 = baseMovement(getMove().getPawn(), getMove().getTarget());
        gamer.getMyGodCard().afterOwnerMoving(this);
        for (God card : otherCards) {
            card.afterOtherMoving(gamer);
        }
    }

    public void costruzione() {
        Mossa m = leggiMossa(Mossa.Azione.BUILD);
        gamer.getMyGodCard().beforeOwnerBuilding(this);
        for (God card : otherCards) {
            card.beforeOtherBuilding(gamer);
        }
        boolean step2 = baseBuilding(getMove().getPawn(), getMove().getTarget());
        gamer.getMyGodCard().afterOwnerBuilding(this);
        for (God card : otherCards) {
            card.afterOtherBuilding(gamer);
        }
    }

    public void winner() {
        baseWinner(getMove().getPawn());
    }

    /**
     * Standard move
     *
     * @param p  pawn to be moved
     * @param destination target cell
     * @return move result
     */
    public boolean baseMovement(Pawn p, Cell destination) {
        Cell myCell = table.getTableCell(p.getRow(), p.getColumn());
        if (!table.iCanMove(myCell)) {
            gamer.setLooser(true);
            return false;
        } else {
            if ((!table.controlBaseMovement(myCell, destination))) {
                return false;
            } else {
                if (gamer.getSteps() == 1) {
                    myCell.getPawn().setPastLevel(myCell.getLevel());
                    myCell.getPawn().setPresentLevel(destination.getLevel());
                    destination.setPawn(p);
                    destination.setFree(false);
                    destination.getPawn().setRow(destination.getX());
                    destination.getPawn().setColumn(destination.getY());
                    myCell.setPawn(null);
                    myCell.setFree(true);
                    return true;
                } else {
                    return false;
                }
            }
        }
    }


    /**
     * Standard build on game field
     *
     * @param p           reference pawn
     * @param destination target cell to build on
     * @return build result
     */

    private boolean baseBuilding(Pawn p, Cell destination) {
        Cell myCell = table.getTableCell(p.getRow(), p.getColumn());
        if (!table.iCanBuild(myCell)) {
            gamer.setLooser(true);
            return false;
        } else {
            if (!table.controlBaseBuilding(myCell, destination)) {
                return false;
            } else {
                if (gamer.getBuilds() == 1) {
                    int newLevel = destination.getLevel() + 1;
                    if (newLevel > 3) {
                        destination.setLevel(3);
                        destination.setComplete(true);
                        return true;
                    } else {
                        destination.setLevel(newLevel);
                        return true;
                    }
                } else {
                    return false;
                }
            }
        }
    }

    public void baseWinner(Pawn p) {
        if ((p.getPastLevel() == 2) && (p.getPresentLevel() == 3) && (getMove().getPawn() == p)) {
            gamer.setWinner(true);
        } else {
            gamer.setWinner(false);
        }
    }

    /*Aggiungere un metodo che controlla la vittoria standard per il giocatore corrente*/
}

/**
 * private boolean mossaBase(Pawn p, Cell to) {
 * Cell from = table.getTableCell(p.getRow(), p.getColumn());
 * ArrayList<Cell> adiacenti = table.searchAdjacentCells(table.getTableCell(p.getRow(), p.getColumn()));
 * if (!adiacenti.contains(to)) {
 * return false;
 * }
 * if (table.walkNearCell(from, to) > gamer.getSteps()) {
 * return false;
 * }
 * if (!to.isFree() && !gamer.isOverwrite()) {
 * return false;
 * }
 * if (to.getLevel() - from.getLevel() > gamer.getLevelsUp()) {
 * return false;
 * }
 * if (from.getLevel() - to.getLevel() > gamer.getLevelsDown()) {
 * return false;
 * }
 * from.free();
 * p.setRow(to.getX());
 * p.setColumn(to.getY());
 * to.setPawn(p);
 * return true;
 * }
 * private boolean costruzioneBase(Pawn p, Cell on) {
 * ArrayList<Cell> adiacenti = table.searchAdjacentCells(table.getTableCell(p.getRow(), p.getColumn()));
 * if (!adiacenti.contains(on)) {
 * return false;
 * }
 * if (on.isFree() && !on.isComplete()) {
 * return on.build();
 * }
 * return false;
 * }
 */

/**
 private boolean costruzioneBase(Pawn p, Cell on) {
 ArrayList<Cell> adiacenti = table.searchAdjacentCells(table.getTableCell(p.getRow(), p.getColumn()));
 if (!adiacenti.contains(on)) {
 return false;
 }
 if (on.isFree() && !on.isComplete()) {
 return on.build();
 }
 return false;
 }*/