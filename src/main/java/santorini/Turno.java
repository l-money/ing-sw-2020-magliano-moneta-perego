package santorini;

import santorini.model.*;

import java.io.IOException;
import java.util.ArrayList;

public class Turno implements Runnable {
    private ArrayList<God> otherCards;
    private Gamer gamer;
    private Table table;
    private int idStartPawn;
    private Mossa move;
    private boolean startValidation;
    private boolean validationMove;
    private boolean validationBuild;
    private boolean panEffect = false;
    private boolean promEffect = false;
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

    public int getIdStartPawn() {
        return idStartPawn;
    }

    public NetworkHandlerServer getGameHandler() {
        return gameHandler;
    }

    public void setMove(Mossa move) {
        this.move = move;
    }

    public boolean isStartValidation() {
        return startValidation;
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
     * method isValidationBuild for the Godcards
     *
     * @return the global attribute validationBuild
     */
    public boolean isValidationBuild() {
        return validationBuild;
    }

    public void setStartValidation(int idStartPawn) {
        this.idStartPawn = idStartPawn;
    }

    public Mossa giveMeMossa(Mossa.Action action) {
        try {
            return gameHandler.richiediMossa(action, gamer);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendFailed() {
        gameHandler.sendFailed(gamer);
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

    /**
     * method firstControlOfMovement
     */
    public void firstControlOfMovement() {
        startValidation = false;
        if ((getGamer().getMyGodCard().getName().equals("Prometheus")) && (!promEffect)) {
            move = giveMeMossa(Mossa.Action.BUILD);
            promEffect = true;
        } else {
            do {
                move = giveMeMossa(Mossa.Action.MOVE);
                startValidation = controlStandardParameter(move);
                if (!startValidation) {
                    sendFailed();
                } else {
                    idStartPawn = move.getIdPawn();
                }
            } while (!startValidation);
        }
    }

    /**
     * method myMovement
     */

    public void myMovement() {
        validationMove = false;
        gamer.getMyGodCard().beforeOwnerMoving(this);
        for (God card : otherCards) {
            card.beforeOtherMoving(gamer);
        }

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

    /**
     * method control win
     */
    public void win() {
        controlWin();
    }

    /**
     * method myBuilding
     */
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
        Pawn p = getGamer().getPawn(move.getIdPawn());//save my pawn
        Cell end = table.getTableCell(move.getTargetX(), move.getTargetY());//save destination
        Cell start = table.getTableCell(p.getRow(), p.getColumn());//save myCell
        //control if the gamer can do one step
        if (getGamer().getSteps() == 0) {
            validationMove = true;
        } else {
            //control the id is the same of start id
            if ((p.getIdPawn() != idStartPawn)) {
                validationMove = false;
            } else {
                //control the pawn can move
                if (!table.iCanMove(start)) {
                    //if it can not, set iCanPlay false
                    getGamer().getPawn(idStartPawn).setICanPlay(false);
                    //control the two pawns can't move
                    if (amILocked(this.gamer)) {
                        this.gamer.setLoser(true);
                        validationMove = true;
                    } else {
                        this.gamer.setLoser(false);
                            validationMove = false;
                    }
                } else {
                    //control the base movement of the pawn is possible
                    if (!table.controlBaseMovement(start, end)) {
                        validationMove = false;
                    } else {
                        //control if the pawn can go up on level
                        if ((gamer.getLevelsUp() == 0) && (end.getLevel() - start.getLevel() == 1)) {
                            validationMove = false;
                        } else {
                            //do the step and change position
                            validationMove = getMyStep(start, end, p);
                        }

                    }
                }
            }
        }
    }

    /**
     * method getValidationMove
     * Control if the movement is valid
     */
    public void getValidationMove() {
        if (!validationMove) {
            startValidation = false;
            sendFailed();
            do {
                move = giveMeMossa(Mossa.Action.MOVE);
                startValidation = controlStandardParameter(move);
                if (!startValidation || move.getIdPawn() != idStartPawn) {
                    sendFailed();
                }
            } while (!startValidation);
        }
    }

    /**
     * Standard build on game field
     */
    public void baseBuilding(Mossa move) {
        Pawn p = getGamer().getPawn(move.getIdPawn());
        Cell end = table.getTableCell(move.getTargetX(), move.getTargetY());
        Cell start = table.getTableCell(p.getRow(), p.getColumn());
        //control if the pawn can build
        if (gamer.getBuilds() == 0) {
            validationBuild = true;
        } else {
            //control if the id is the same
            if (p.getIdPawn() != idStartPawn) {
                validationBuild = false;
            } else {
                //control the pawn can build
                if (!table.iCanBuild(start)) {
                    //if it can not, set iCanPlay false
                    getGamer().getPawn(idStartPawn).setICanPlay(false);
                    //control the two pawns can't build
                    if (amILocked(this.gamer)) {
                        this.gamer.setLoser(true);
                        validationBuild = true;
                    } else {
                        this.gamer.setLoser(false);
                        validationBuild = false;
                    }
                } else {
                    //control the base build of the pawn is possible
                    if (!table.controlBaseBuilding(start, end)) {
                        validationBuild = false;
                    } else {
                        //do the build
                        boolean b = table.build(end);
                        //control if there are bricks in the bag
                        if (!b) {
                            this.gamer.setLoser(true);
                        }
                        validationBuild = true;
                    }
                }
            }
        }
    }

    /**
     * method getValidationBuild
     * Control if the building is valid
     */
    public void getValidationBuild() {
        if (!validationBuild) {
            sendFailed();
            startValidation = false;
            do {
                move = giveMeMossa(Mossa.Action.BUILD);
                startValidation = controlStandardParameter(move);
                if (!startValidation || move.getIdPawn() != idStartPawn) {
                    sendFailed();
                }
            } while (!startValidation);
        }
    }

    /**
     * Standard win
     */
    public void controlWin() {
        if (panEffect) {
            gamer.setWinner(true);
            gamer.setBuilds(0);
        } else {
            if (
                    (gamer.getPawn(idStartPawn).getPastLevel() == 2) &&
                            (gamer.getPawn(idStartPawn).getPresentLevel() == 3)
            ) {
                gamer.setWinner(true);
                gamer.setBuilds(0);
            } else {
                gamer.setWinner(false);
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

    private boolean amILocked(Gamer gamer) {
        Pawn p0 = gamer.getPawn(0);
        Pawn p1 = gamer.getPawn(1);
        if ((!p0.getICanPlay()) && (!p1.getICanPlay())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * method controlStandardParameter
     *
     * @return true if the parameters are valid, or return false
     */
    public boolean controlStandardParameter(Mossa movement) {
        if ((movement.getIdPawn() < 0) || (movement.getIdPawn() > 1) ||
                (movement.getTargetX() < 0) || (movement.getTargetX() > 5) ||
                (movement.getTargetY() < 0) || (movement.getTargetY() > 5) ||
                (!gamer.getPawn(movement.getIdPawn()).getICanPlay())
        ) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * method nullEffectForGodCards: ONLY FOR GODS EFFECTS
     *
     * @param movement the optional movement (move or build)
     * @return if the gamer wants to do the optional movement of the god card
     */
    public boolean nullEffectForGodCards(Mossa movement) {
        if (movement.getIdPawn() == -1 && movement.getTargetX() == -1 && movement.getTargetY() == -1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * method getMyStep
     *
     * @param startCell the start cell
     * @param endCell   the destination of the movment
     * @param myPawn    the pawn which moves
     * @return true
     */
    public boolean getMyStep(Cell startCell, Cell endCell, Pawn myPawn) {
        int x1 = startCell.getX();
        int y1 = startCell.getY();
        int x2 = endCell.getX();
        int y2 = endCell.getY();
        table.setACell(x1, y1, startCell.getLevel(), true, startCell.isComplete(), null);
        table.setACell(x2, y2, endCell.getLevel(), false, endCell.isComplete(), myPawn);
        getGamer().setAPawn(myPawn.getIdPawn(), x2, y2, startCell.getLevel(), endCell.getLevel());
        return true;
    }

    /**
     * method godCardEffect
     *
     * @param move         the move effect of the card, not nullEffect
     * @param effect       if move is correct, effect is true, else is false
     * @param i            the case of the effect
     * @param specialCell the past position of the pawn
     * @return the effect: true or false
     */
    // TODO end the method and improve it
    public boolean godCardEffect(Mossa move, boolean effect, int i, Cell specialCell) {
        if (nullEffectForGodCards(move)) {
            effect = true;
        } else {
            if (!controlStandardParameter(move)) {
                effect = false;
            } else {
                Pawn myPawn = getGamer().getPawn(move.getIdPawn());
                Cell end = getTable().getTableCell(move.getTargetX(), move.getTargetY());
                Cell start = getTable().getTableCell(myPawn.getRow(), myPawn.getColumn());
                switch (i) {
                    case 0:
                        getGamer().setSteps(1);
                        if (move.getAction() == Mossa.Action.MOVE) {
                            if ((end.getX() == specialCell.getX()) &&
                                    (end.getY() == specialCell.getY())) {
                                effect = false;
                            } else {
                                baseMovement(move);
                                getValidationMove();
                                effect = isValidationMove();
                            }
                        } else {
                            effect = false;
                        }
                        break;
                    case 1:
                        if (move.getAction() == Mossa.Action.BUILD) {
                            ArrayList<Cell> nearCells = getTable().searchAdjacentCells(start);
                            if ((nearCells.contains(end)) && (end.getLevel() >= 0) && (end.getLevel() <= 3) &&
                                    (end.isFree()) && (!end.isComplete()) && (end.getPawn() == null) &&
                                    (controlStandardParameter(move))) {
                                getTable().getTableCell(end.getX(), end.getY()).setComplete(true);
                                effect = true;
                                getGamer().setBuilds(0);
                            } else {
                                effect = false;
                            }
                        } else {
                            effect = false;
                        }
                        break;
                    case 2:
                        if (move.getAction() == Mossa.Action.BUILD) {
                            if (move.getTargetY() == specialCell.getX() &&
                                    move.getTargetY() == specialCell.getY()) {
                                effect = false;
                            } else {
                                baseBuilding(move);
                                getValidationBuild();
                                effect = isValidationBuild();
                            }
                        } else {
                            effect = false;
                        }
                        break;
                    case 3:
                        if (move.getAction() == Mossa.Action.BUILD) {
                            if ((move.getTargetX() == specialCell.getX()) &&
                                    (move.getTargetY() == specialCell.getY())) {
                                baseBuilding(move);
                                getValidationBuild();
                                effect = isValidationBuild();
                            } else {
                                effect = false;
                            }
                        } else {
                            effect = false;
                        }
                        break;

                    default:
                        break;
                }
            }
        }
        return effect;
    }



}