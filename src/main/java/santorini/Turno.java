package santorini;

import santorini.model.*;

import java.util.ArrayList;

public class Turno implements Runnable {
    private ArrayList<God> otherCards;
    private Gamer gamer;
    private Table table;

    /**
     * Turn initialization for a specified player
     *
     * @param cards all god cards active in this match
     * @param gamer player that has to play
     * @param table game field
     */
    public Turno(ArrayList<God> cards, Gamer gamer, Table table) {
        for (God g : cards) {
            if (g.equals(gamer.getGod())) {
                cards.remove(g);
            }
        }
        this.otherCards = cards;
        this.gamer = gamer;
        this.table = table;
    }

    /**
     * Executes in new Thread all player turn with
     * all god cards features
     */
    public void run() {
        mossa();
        costruzione();
    }

    public void mossa() {
        gamer.getMycard().beforeOwnerMoving(this);
        for (God card : otherCards) {
            card.beforeOtherMoving(gamer);
        }
        //mossaBase()
        gamer.getMycard().afterOwnerMoving(this);
        for (God card : otherCards) {
            card.afterOtherMoving(gamer);
        }
    }

    public void costruzione() {
        gamer.getMycard().beforeOwnerBuilding(this);
        for (God card : otherCards) {
            card.beforeOtherBuilding(gamer);
        }
        //costruzioneBase();
        gamer.getMycard().afterOwnerBuilding(this);
        for (God card : otherCards) {
            card.afterOtherBuilding(gamer);
        }
    }

    /**
     * Standard move that checks parameters handled by god cards
     *
     * @param p  pawn to be moved
     * @param to target cell
     * @return move result
     */
    private boolean mossaBase(Pawn p, Cell to) {
        Cell from = table.getTableCell(p.getRow(), p.getColumn());
        ArrayList<Cell> adiacenti = table.searchAdjacentCells(table.getTableCell(p.getRow(), p.getColumn()));
        if (!adiacenti.contains(to)) {
            return false;
        }
        if (table.walkNearCell(from, to) > gamer.getSteps()) {
            return false;
        }
        if (!to.isFree() && !gamer.isOverwrite()) {
            return false;
        }
        if (to.getLevel() - from.getLevel() > gamer.getLevels_up()) {
            return false;
        }
        if (from.getLevel() - to.getLevel() > gamer.getLevel_down()) {
            return false;
        }
        from.free();
        p.setRow(to.getX());
        p.setColumn(to.getY());
        to.setPawn(p);
        return true;
    }

    /**
     * Standard build on game field
     *
     * @param p  reference pawn
     * @param on target cell to build on
     * @return build result
     */
    private boolean costruzioneBase(Pawn p, Cell on) {
        ArrayList<Cell> adiacenti = table.searchAdjacentCells(table.getTableCell(p.getRow(), p.getColumn()));
        if (!adiacenti.contains(on)) {
            return false;
        }
        if (on.isFree() && !on.isComplete()) {
            return on.build();
        }
        return false;
    }

    /*Aggiungere un metodo che controlla la vittoria standard per il giocatore corrente*/
}
