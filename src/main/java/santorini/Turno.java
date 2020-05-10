package santorini;

import santorini.model.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Turno implements Runnable {
    private ArrayList<God> otherCards;
    private Gamer gamer;
    private Table table;
    private Mossa move;
    private boolean validationMove;
    private boolean validationBuild;
    private NetworkHandlerServer gameHandler;
    //count : 5 number of attempts
    private int count = 0;
    //TODO
    private TimerTask task0 = new TimerTask() {
        @Override
        public void run() {
        }
    };
    private TimerTask task1 = new TimerTask() {
        @Override
        public void run() {
        }
    };
    private Timer myTimer0 = new Timer();
    private Timer myTimer1 = new Timer();

    /**
     * Turn initialization for a specified player
     *
     * @param cards all god cards active in this match
     * @param gamer player that has to play
     * @param table game field
     */
    //TODO vedere meglio la gestione dei messaggi
    public Turno(ArrayList<God> cards, Gamer gamer, Table table, NetworkHandlerServer handler) {
        cards.removeIf(g -> g.equals(gamer.getMyGodCard()));
        this.otherCards = cards;
        this.gamer = gamer;
        this.table = table;
        this.gameHandler = handler;
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
            System.out.println("Chiedo movimento");
            move = giveMeMossa(Mossa.Action.MOVE);
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
        gameHandler.sendFailed(gamer, "Mossa non valida,riprova");
    }

    /**
     * Executes in new Thread all player turn with
     * all god cards features
     */
    // TODO aggiustare le stampe e i timer
    public void run() {
        myTimer0.schedule(task0, 30000);
        myTimer0.cancel();
        myTimer0 = new Timer();
        if (!getGamer().getLoser()) {
            getGameHandler().getGame().broadcastMessage("Turno di :" + getGamer().getName() + "\n");
            getGameHandler().sendMessage(getGamer(), "E' il tuo turno");
            getGameHandler().sendMessage(getGamer(), "Carta: " + "\u001B[34m" + getGamer().getMyGodCard().getName() + "\u001B[0m");
            getGameHandler().sendMessage(getGamer(), "Colore: " + printMyColor(getGamer()));
            validationMove = false;
            validationBuild = false;
            getGamer().setSteps(1);
            getGamer().setBuilds(1);
            count = 0;
            do {
                move = moveRequest();
                myMovement();
            } while (!validationMove && count < 5);
            methodLoser(validationMove, count, getGamer());
            controlWin();
            count = 0;
            if (!getGamer().getLoser()) {
                do {
                    getGamer().setBuilds(1);
                    move = buildingRequest();
                    myBuilding();
                    printTableStatusTurn(validationBuild);
                } while (!validationBuild && count < 5);
                methodLoser(validationBuild, count, getGamer());
            }
        }
        //myTimer1.schedule(task1,10000);
        //myTimer1.cancel();
        //myTimer1 = new Timer();
    }


    /**
     * method myMovement
     */
    public void myMovement() {
        validationMove = false;
        if ((controlStandardParameter(getMove()) && getMove().getAction().equals(Mossa.Action.MOVE))) {
                gamer.getMyGodCard().beforeOwnerMoving(this);
                for (God card : otherCards) {
                    card.beforeOtherMoving(gamer);
                }
            baseMovement(getMove());
            getValidationMove(validationMove);

                gamer.getMyGodCard().afterOwnerMoving(this);
                for (God card : otherCards) {
                    card.afterOtherMoving(gamer);
                }
        } else {
            getGameHandler().sendMessage(getGamer(), "\u001B[31m" + "##Coordinate non calide##" + "\u001B[0m");
            getValidationMove(validationMove);
            }
    }


    /**
     * method myBuilding
     */
    public void myBuilding() {
        validationBuild = false;
        gamer.getMyGodCard().beforeOwnerBuilding(this);
                for (God card : otherCards) {
                    card.beforeOtherBuilding(gamer);
                }

        if ((controlStandardParameter(getMove())) && (validationMove) &&
                (getMove().getAction().equals(Mossa.Action.BUILD))) {
            baseBuilding(getMove());
            getValidationBuild(validationBuild);
        } else {
            getGameHandler().sendMessage(getGamer(), "\u001B[31m" + "##Coordinate non calide##" + "\u001B[0m");
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
                    getGameHandler().sendMessage(getGamer(), "\u001B[31m" + "##Nessuna casella adiacente libera##" + "\u001B[0m");
                    getGamer().getPawn(move.getIdPawn()).setICanPlay(false);
                    //control the two pawns can't move
                    if (amILocked(getGamer())) {
                        getGamer().setLoser(true);
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
                        getGameHandler().sendMessage(getGamer(), "\u001B[31m" + "###Non puoi muoverti qui" + "\u001B[0m");
                        validationMove = false;
                    } else {
                        //control if the pawn can go up on level
                        if ((gamer.getLevelsUp() == 0) && (end.getLevel() - start.getLevel() == 1)) {
                            getGameHandler().sendMessage(getGamer(), "\u001B[31m" + "###Non puoi salire di livello" + "\u001B[0m");
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
            //sendFailed();
            count++;
            getGameHandler().sendMessage(getGamer(), "Tentativi rimanenti: " + (5 - count));
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
                //control the pawn can build
                if (!table.iCanBuild(start)) {
                    //if it can not, set iCanPlay false
                    getGameHandler().sendMessage(getGamer(), "\u001B[31m" + "###Nessuna casella adiacente disponibile" + "\u001B[0m");
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
                        getGameHandler().sendMessage(getGamer(), "\u001B[31m" + "###Non puoi costruire qui" + "\u001B[0m");
                        validationBuild = false;
                    } else {
                        //do the build
                        table.build(end);
                        validationBuild = true;
                    }
                    }
                }
            }

    /**
     * method getValidationBuild
     * Control if the building is valid
     */
    public void getValidationBuild(boolean vB) {
        if (!vB) {
            //sendFailed();
            count++;
            getGameHandler().sendMessage(getGamer(), "Tentativi rimanenti: " + (5 - count));
        } else {
                getGamer().setBuilds(0);
                    }
            }

    /**
     * Standard win
     */
    public void controlWin() {
        int idP = getMove().getIdPawn();
            if (
                    (getGamer().getPawn(idP).getPastLevel() == 2) &&
                            (getGamer().getPawn(idP).getPresentLevel() == 3)
            ) {
                getGamer().setWinner(true);
                getGameHandler().getGame().setWinner(getGamer());
                getGameHandler().getGame().broadcastMessage("FINE PARTITA");
            } else {
                getGamer().setWinner(false);
            }

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
     * method printTableStatusTurn
     *
     * @param b if is true, print status, else print false
     */
    public void printTableStatusTurn(boolean b) {
        if (b) {
            gameHandler.getGame().updateField();
        }
    }

    /**
     * method methodLoser
     *
     * @param b validationMove or validationBuild
     * @param i count
     * @param g the current gamer
     */
    private void methodLoser(boolean b, int i, Gamer g) {
        //exceeded the number of attempts
        if ((!b) && (i >= 5)) {
            g.setLoser(true);
            getGameHandler().sendMessage(g, "Hai esaurito i tentativi!!!");
        } else {
            g.setLoser(false);
        }
        //general control of defeat
        if (g.getLoser()) {
            getGameHandler().getGame().broadcastMessage(g.getName() + " ha perso!!!");
        }
    }

    /**
     * method printMyColor
     *
     * @param g the gamer
     */
    private String printMyColor(Gamer g) {
        if (g.getColorGamer() == Color.YELLOW) {
            return "\u001B[33m" + "Giallo" + "\u001B[0m";
        } else {
            if (g.getColorGamer() == Color.RED) {
                return "\u001B[31m" + "Rosso" + "\u001B[0m";
            } else {
                if (g.getColorGamer() == Color.BLUE) {
                    return "\u001B[36m" + "Blu" + "\u001B[0m";
                } else {
                    return "No color";
                }
            }
        }
    }



}
