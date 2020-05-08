package santorini;

import santorini.model.*;

import java.io.IOException;
import java.util.ArrayList;

public class Turno implements Runnable {
    private ArrayList<God> otherCards;
    private Gamer gamer;
    private Table table;
    private Mossa move;
    private boolean validationMove;
    private boolean validationBuild;
    private boolean panEffect = false;
    private boolean promEffect = false;
    private boolean athenaEffect = false;
    private int AG;
    private NetworkHandlerServer gameHandler;
    //count is for test, it will substitute by a timer
    private int count = 0;

    /**
     * Turn initialization for a specified player
     *
     * @param cards all god cards active in this match
     * @param gamer player that has to play
     * @param table game field
     */
    public Turno(ArrayList<God> cards, Gamer gamer, Table table, NetworkHandlerServer handler) {
        cards.removeIf(g -> g.equals(gamer.getMyGodCard()));
        this.otherCards = cards;
        this.gamer = gamer;
        this.table = table;
        this.gameHandler = handler;
        this.athenaEffect = athenaEffect;
    }

    /**
     * method getTable
     *
     * @return the table
     */
    public Table getTable() {
        return table;
    }

    /**
     * method getGamer
     * @return the gamer
     */
    public Gamer getGamer() {
        return gamer;
    }

    /**
     * getMove
     * @return the move
     */
    public Mossa getMove() {
        return move;
    }

    /**
     * method getCount
     * It will substitute by a timer
     *
     * @return the number of a wrong mossa
     */
    public int getCount() {
        return count;
    }

    /**
     * method getGameHandler
     * @return the gameHandler
     */
    public NetworkHandlerServer getGameHandler() {
        return gameHandler;
    }

    /**
     * method setTable
     *
     * @param table table of the past turn
     */
    public void setTable(Table table) {
        this.table = table;
    }

    /**
     * method setGamer
     *
     * @param gamer the new gamer
     */
    public void setGamer(Gamer gamer) {
        this.gamer = gamer;
    }

    /**
     * method setCount
     *
     * @param count the start count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * method setMove
     * @param move my new mossa
     */
    public void setMove(Mossa move) {
        this.move = move;
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

    /**
     * method setValidationMove
     *
     * @param validationMove
     */
    public void setValidationMove(boolean validationMove) {
        this.validationMove = validationMove;
    }

    /**
     * method setValidationBuild
     *
     * @param validationBuild
     */
    public void setValidationBuild(boolean validationBuild) {
        this.validationBuild = validationBuild;
    }

    public int getAG() {
        return AG;
    }

    public void setAG(int AG) {
        this.AG = AG;
    }

    /**
     * method giveMeMossa
     * @param action the action of the gamer (MOVE or BUILD)
     * @return the move
     */
    public Mossa giveMeMossa(Mossa.Action action) {
        try {
            return gameHandler.richiediMossa(action, gamer);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            gameHandler.getGame().networkError(gamer);
        }
        return null;
    }

    /**
     * method moveRequest
     *
     * @return a move of typo MOVE
     */
    public Mossa moveRequest() {
        if (!promEffect) {
            System.out.println("Chiedo movimento");
            move = giveMeMossa(Mossa.Action.MOVE);
        }
        return move;
    }

    /**
     * method buildingRequest
     *
     * @return a move of typo BUILD
     */
    public Mossa buildingRequest() {
        System.out.println("Chiedo costruzione");
        move = giveMeMossa(Mossa.Action.BUILD);
        return move;
    }

    /**
     * method sendFailed
     * Prints an error or sends a failed to handler
     */
    public void sendFailed() {
        gameHandler.sendFailed(gamer, "Mossa non valida\nRiprova");
        System.err.print("Errore_Caccoso\n");
    }

    /**
     * Executes in new Thread all player turn with
     * all god cards features
     */
    public void run() {
        //TODO
        // Condizione di sconfitta
        // Situazione attuale, appena loser==true, il giocatore smette di giocare immediatamente
        // Domanda:
        // Appena loser==true il giocatore smette di giocare in qualsiasi caso
        // oppure cerca di vedere se una pedina si è sbloccata??
        if (!getGamer().isWinner()) {
            validationMove = false;
            validationBuild = false;
            getAG();
            getGamer().setSteps(1);
            getGamer().setBuilds(1);
            count = 0;
            do {
                getGamer().setSteps(1);
                effectsOfCards();
                move = moveRequest();
                myMovement(move);
            } while (!validationMove && count < 5);
            controlWin();
            gameHandler.getGame().updateField();
            count = 0;
            if (!getGamer().isWinner() && !getGamer().getLoser()) {
                do {
                    getGamer().setBuilds(1);
                    move = buildingRequest();
                    myBuilding(move);
                } while (!validationBuild && count < 5);

            }
        }
        gameHandler.getGame().updateField();
    }

    /**
     * method effectsOfCards
     * Sets true if the gamer wants to use the effect of Prometheus (build-move-build with levelUp = 0)
     * else sets false promEffect
     * TODO remember to remove System.out and the comments
     */
    public void effectsOfCards() {
        //Prometheus
        setPromEffect(false);
        if ((getGamer().getMyGodCard().getName().equals("Prometheus")) && (!promEffect)) {
            System.out.println("Hai Prometheus, costruzione facoltativa ");
            move = buildingRequest();
            setPromEffect(true);

             if (nullEffectForGodCards(move)) {
             setPromEffect(false);
             }

        }
        //Athena
        //TODO review Athena effect
        if (athenaEffect) {
            getGamer().setLevelsUp(0);
        } else {
            getGamer().setLevelsUp(1);
        }

    }

    /**
     * method myMovement
     */
    public void myMovement(Mossa m) {
        validationMove = false;
        if ((controlStandardParameter(m) && m.getAction().equals(Mossa.Action.MOVE) && (!promEffect))
                ||
                (controlStandardParameter(m) && m.getAction().equals(Mossa.Action.BUILD) && (promEffect))
        ) {
                gamer.getMyGodCard().beforeOwnerMoving(this);
                for (God card : otherCards) {
                    card.beforeOtherMoving(gamer);
                }
            baseMovement(m);
            getValidationMove(validationMove);

                gamer.getMyGodCard().afterOwnerMoving(this);
                for (God card : otherCards) {
                    card.afterOtherMoving(gamer);
                }
        } else {
            getValidationMove(validationMove);
            }
    }


    /**
     * method myBuilding
     */
    public void myBuilding(Mossa b) {
        validationBuild = false;
        gamer.getMyGodCard().beforeOwnerBuilding(this);
                for (God card : otherCards) {
                    card.beforeOtherBuilding(gamer);
                }

        if ((controlStandardParameter(b)) && (validationMove) &&
                (b.getAction().equals(Mossa.Action.BUILD))) {
            baseBuilding(b);
            getValidationBuild(validationBuild);
        } else {
            getValidationBuild(validationBuild);
        }
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
        int k = move.getIdPawn();
        //control if the gamer can do one step
        if (getGamer().getSteps() == 0) {
            validationMove = true;
        } else {
                //control the pawn can move
                if (!table.iCanMove(start)) {
                    //if it can not, set iCanPlay false
                    getGamer().getPawn(move.getIdPawn()).setICanPlay(false);
                    //control the two pawns can't move
                    if (amILocked(getGamer())) {
                        getGamer().setLoser(true);
                        //getGamer().setBuilds(0);
                        validationMove = true;
                    } else {
                        getGamer().setLoser(false);
                        validationMove = false;
                    }
                } else {
                    getGamer().getPawn(move.getIdPawn()).setICanPlay(true);
                    getGamer().setLoser(false);
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

    /**
     * method getValidationMove
     * Control if the movement is valid
     * TODO remember to remove the comments
     */
    public void getValidationMove(boolean vM) {
        if (!vM) {
            sendFailed();
            count++;
        } else {
            getGamer().setSteps(0);
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
            /**
             //control if the id is the same
             if (p.getIdPawn() != idStartPawn) {
             validationBuild = false;
             } else {
             */
                //control the pawn can build
                if (!table.iCanBuild(start)) {
                    //if it can not, set iCanPlay false
                    getGamer().getPawn(move.getIdPawn()).setICanPlay(false);
                    //control the two pawns can't build
                    if (amILocked(getGamer())) {
                        getGamer().setLoser(true);
                        validationBuild = true;
                    } else {
                        getGamer().setLoser(false);
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
                            getGamer().setLoser(true);
                            validationBuild = false;
                        }
                        validationBuild = true;
                    }
                }
            }
        }

    /**
     * method getValidationBuild
     * Control if the building is valid
     * TODO remember to remove the comments
     */
    public void getValidationBuild(boolean vB) {
        if (!vB) {
            sendFailed();
            //System.err.println("Mossa numero " + (count + 1) + "\tcostruzione non validata");
            count++;
        } else {
                getGamer().setBuilds(0);
                    }
            }

    /**
     * Standard win
     */
    public void controlWin() {
        int idP = getMove().getIdPawn();
        if (panEffect) {
            getGamer().setWinner(true);
            System.out.println("HAI VINTO :" + getGamer().getName());
            getGamer().setBuilds(0);
        } else {
            if (
                    (getGamer().getPawn(idP).getPastLevel() == 2) &&
                            (getGamer().getPawn(idP).getPresentLevel() == 3)
            ) {
                getGamer().setWinner(true);
                System.out.println("HAI VINTO :" + getGamer().getName());
                getGamer().setBuilds(0);
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
    public boolean getPromEffect() {
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
     * method getAthenaEffect
     * @return athenaEffect
     */
    public boolean getAthenaEffect() {
        return athenaEffect;
    }

    /**
     * method setAthenaEffect
     * @param athenaEffect effect of the god
     */
    public void setAthenaEffect(boolean athenaEffect) {
        this.athenaEffect = athenaEffect;
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
                (movement.getTargetY() < 0) || (movement.getTargetY() > 5)) {
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
        if (movement.getIdPawn() == -1 || movement.getTargetX() == -1 || movement.getTargetY() == -1) {
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
        int l1 = startCell.getLevel();
        int x2 = endCell.getX();
        int y2 = endCell.getY();
        int l2 = endCell.getLevel();
        getGamer().setAPawn(myPawn.getIdPawn(), x2, y2, l1, l2);
        table.setACell(x1, y1, startCell.getLevel(), true, startCell.isComplete(), null);
        table.setACell(x2, y2, endCell.getLevel(), false, endCell.isComplete(), myPawn);
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
    public boolean godCardEffect(Mossa move, boolean effect, int i, Cell specialCell) {
        if (nullEffectForGodCards(move)) {
            System.out.println("Effetto Nullo");
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
                                getValidationMove(validationMove);
                                effect = validationMove;
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
                                getValidationBuild(validationBuild);
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
                                getValidationBuild(validationBuild);
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
