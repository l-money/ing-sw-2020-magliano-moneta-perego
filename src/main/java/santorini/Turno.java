package santorini;

import santorini.model.*;

import java.io.IOException;
import java.util.ArrayList;

public class Turno implements Runnable {
    private ArrayList<God> otherCards;
    private Gamer gamer;
    private Table table;
    private int idPawnOfMovement;
    private Mossa move;
    private boolean validationMove;
    private boolean validationBuild;
    private boolean panEffect = false;
    private boolean promEffect = false;
    private boolean startValidation;
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

    public int getIdPawnOfMovement() {
        return idPawnOfMovement;
    }

    public NetworkHandlerServer getGameHandler() {
        return gameHandler;
    }

    public void setMove(Mossa move) {
        this.move = move;
    }

    /**
     * Executes in new Thread all player turn with
     * all god cards features
     */
    public void run() {
        firstControlOfMovement();
        myMovement();
        win();
        myBuilding();
    }

    private Mossa giveMeMossa(Mossa.Action action) {
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

    private void firstControlOfMovement() {
        startValidation = false;
        validationMove = false;
        validationBuild = false;
        if ((gamer.getMyGodCard().getName().equals("Prometheus")) && (!promEffect)) {
            move = giveMeMossa(Mossa.Action.BUILD);
            promEffect = true;
        } else {
            promEffect = false;
            do {
                move = giveMeMossa(Mossa.Action.MOVE);
                startValidation = getStartParameter(move);
                if (!startValidation) {
                    sendFailed();
                    move = giveMeMossa(Mossa.Action.MOVE);
                }
            } while (!startValidation);
            idPawnOfMovement = move.getIdPawn();
        }
    }

    private void getFirst() {
        firstControlOfMovement();
    }

    public void myMovement() {
        gamer.getMyGodCard().beforeOwnerMoving(this);
        for (God card : otherCards) {
            card.beforeOtherMoving(gamer);
        }

        validationMove = false;
        do {
            baseMovement(move);
            getValidationMove();
        } while (!validationMove);
        getGamer().setSteps(0);

        gamer.getMyGodCard().afterOwnerMoving(this);
        for (God card : otherCards) {
            card.afterOtherMoving(gamer);
        }
    }

    public void win() {
        Win();
    }

    public void myBuilding() {
        move = giveMeMossa(Mossa.Action.BUILD);
        gamer.getMyGodCard().beforeOwnerBuilding(this);
        for (God card : otherCards) {
            card.beforeOtherBuilding(gamer);
        }

        validationBuild = false;
        do {
            baseBuilding(move);
            getValidationBuild();
        } while (!validationBuild);
        getGamer().setBuilds(0);

        gamer.getMyGodCard().afterOwnerBuilding(this);
        for (God card : otherCards) {
            card.afterOtherBuilding(gamer);
        }
    }

    /**
     * Standard move
     */
    public void baseMovement(Mossa move) {
        Pawn p = getGamer().getPawn(move.getIdPawn());
        Cell destination = table.getTableCell(move.getTargetX(), move.getTargetY());
        Cell myCell = table.getTableCell(p.getRow(), p.getColumn());
        if (move.getIdPawn() != idPawnOfMovement) {
            validationMove = false;
        } else {
            if (!table.iCanMove(myCell)) {
                getGamer().getPawn(move.getIdPawn()).setICanPlay(false);
                gamer.setLoser(amILocked());
                validationMove = false;
            } else {
                if ((!table.controlBaseMovement(myCell, destination))) {
                    validationMove = false;
                } else {
                    if ((gamer.getSteps() != 1)) {
                        validationMove = false;
                    } else {
                        if ((gamer.getLevelsUp() != 1) && (destination.getLevel() - myCell.getLevel() == 1)) {
                            validationMove = false;
                        } else {
                            validationMove = getChange(myCell, destination, p);
                        }
                    }
                }
            }
        }
    }


    /**
     * Standard build on game field
     */

    public void baseBuilding(Mossa move) {
        Pawn p = getGamer().getPawn(move.getIdPawn());
        int idP = move.getIdPawn();
        Cell destination = table.getTableCell(move.getTargetX(), move.getTargetY());
        Cell myCell = table.getTableCell(p.getRow(), p.getColumn());
        if (idP != idPawnOfMovement) {
            validationBuild = false;
        } else {
            if (!table.iCanBuild(myCell)) {
                getGamer().getPawn(move.getIdPawn()).setICanPlay(false);
                gamer.setLoser(amILocked());
                validationBuild = false;
            } else {
                if (!table.controlBaseBuilding(myCell, destination)) {
                    validationBuild = false;
                } else {
                    if (gamer.getBuilds() == 1) {
                        int x2 = destination.getX();
                        int y2 = destination.getY();
                        validationBuild = getTable().build(getTable().getTableCell(x2, y2));
                    } else {
                        validationBuild = false;
                    }
                }
            }
        }
    }

    /**
     * Standard win
     */
    public void Win() {
        Pawn myPawn = getGamer().getPawn(move.getIdPawn());
        if (panEffect) {
            getGamer().setWinner(true);
        } else {
            if ((myPawn.getPastLevel() == 2) && (myPawn.getPresentLevel()) == 3) {
                getGamer().setWinner(true);
            } else {
                getGamer().setWinner(false);
            }
        }
    }

    /**
     * method setPanEffect
     * If the gamer has Pan card, control the effect of Pan
     */
    public void setPanEffect(boolean newPanEffect) {
        this.panEffect = newPanEffect;
    }

    /**
     * method getPromEffect
     *
     * @return promEffect: true or false
     */
    public boolean isPromEffect() {
        return promEffect;
    }

    /**
     * method setPromEffect
     *
     * @param promEffect new boolean
     */
    public void setPromEffect(boolean promEffect) {
        this.promEffect = promEffect;
    }

    /**
     * method amILocked
     *
     * @return true if at least one pawn can move and  build, otherwise false
     */

    public boolean amILocked() {
        Pawn p0 = gamer.getPawn(0);
        Pawn p1 = gamer.getPawn(1);
        if ((!p0.getICanPlay()) && (!p1.getICanPlay())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * method getValidationMove
     * Control if the movement is valid
     */

    public void getValidationMove() {
        if (!validationMove) {
            sendFailed();
            move = giveMeMossa(Mossa.Action.MOVE);
        }
    }

    /**
     * method isValidationMove for the Godcards
     *
     * @return the global attribute validationMove
     */
    public boolean isValidationMove() {
        return validationMove;
    }

    /**
     * method getValidationBuild
     * Control if the building is valid
     */
    public void getValidationBuild() {
        if (!validationBuild) {
            sendFailed();
            move = giveMeMossa(Mossa.Action.BUILD);
        }
    }

    /**
     * method isValidationBuild for the Godcards
     *
     * @return the global attribute validationBuild
     */
    public boolean isValidationBuild() {
        return validationBuild;
    }

    /**
     * method getStartParameter
     *
     * @return true if the parameters are valid, or return false
     */
    public boolean getStartParameter(Mossa movement) {
        if ((movement.getIdPawn() < 0) || (movement.getIdPawn() > 1) ||
                (movement.getTargetX() < 0) || (movement.getTargetX() > 5) ||
                (movement.getTargetY() < 0) || (movement.getTargetY() > 5)
        ) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * method getChange
     *
     * @param startCell the start cell
     * @param endCell   the destination of the movment
     * @param myPawn    the pawn which moves
     * @return true
     */

    public boolean getChange(Cell startCell, Cell endCell, Pawn myPawn) {
        int x1 = startCell.getX();
        int y1 = startCell.getY();
        int x2 = endCell.getX();
        int y2 = endCell.getY();
        getTable().getTableCell(x1, y1).getPawn().setPastLevel(startCell.getLevel());
        getTable().getTableCell(x1, y1).getPawn().setPresentLevel(endCell.getLevel());
        getTable().getTableCell(x2, y2).setPawn(myPawn);
        getTable().getTableCell(x2, y2).setFree(false);
        getTable().getTableCell(x2, y2).getPawn().setRow(startCell.getX());
        getTable().getTableCell(x2, y2).getPawn().setColumn(endCell.getY());
        getTable().getTableCell(x1, y1).setPawn(null);
        getTable().getTableCell(x1, y1).setFree(true);
        return true;
    }

}