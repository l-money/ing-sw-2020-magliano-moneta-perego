package santorini.controller;

import santorini.network.NetworkHandlerServer;
import santorini.model.*;
import santorini.model.godCards.God;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * Class turno
 */

public class Turno implements Runnable {
    private ArrayList<God> otherCards;
    private Gamer gamer;
    private Table table;
    private Mossa move;
    private boolean validationMove;
    private boolean validationBuild;
    private NetworkHandlerServer gameHandler;
    //count : 3 number of attempts
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
     *
     * @return the gamer
     */
    public Gamer getGamer() {
        return gamer;
    }

    /**
     * getMove
     *
     * @return the move
     */
    public Mossa getMove() {
        return move;
    }

    /**
     * method getCount
     * it will substitute by a timer
     *
     * @return the number of a wrong mossa
     */
    public int getCount() {
        return count;
    }

    /**
     * method getGameHandler
     *
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
     *
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
     * @param validationMove boolean for validate the movement
     */
    public void setValidationMove(boolean validationMove) {
        this.validationMove = validationMove;
    }

    /**
     * method setValidationBuild
     *
     * @param validationBuild boolean for validate the movement
     */
    public void setValidationBuild(boolean validationBuild) {
        this.validationBuild = validationBuild;
    }


    /**
     * method giveMeMove
     *
     * @param action the action of the gamer (MOVE or BUILD)
     * @return the move
     */
    private Mossa giveMeMove(Mossa.Action action) {
        try {
            return gameHandler.richiediMossa(action, gamer);
        } catch (IOException | ClassNotFoundException e) {
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
        move = giveMeMove(Mossa.Action.MOVE);
        return move;
    }

    /**
     * method buildingRequest
     *
     * @return a move of typo BUILD
     */
    public Mossa buildingRequest() {
        move = giveMeMove(Mossa.Action.BUILD);
        return move;
    }


    /**
     * Executes in new Thread all player turn with
     * all god cards features
     */
    public void run() {
        try {
            int maxAttempts = 3;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!getGamer().getLoser()) {
                firstLockdown(getGamer());
                getGameHandler().getGame().broadcastMessage("\n|*****************************************************|\n" +
                        "Turno di :" + getGamer().getName());
                getGameHandler().sendMessage(getGamer(), "E' il tuo turno\n" +
                        "Carta: " + "\u001B[34m" + getGamer().getMyGodCard().getName() + "\u001B[0m" +
                        "\nColore: " + printMyColor(getGamer()));
                validationMove = false;
                validationBuild = false;
                getGamer().setSteps(1);
                getGamer().setBuilds(1);
                count = 0;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gamer.getMyGodCard().initializeOwner(this);
                while (!validationMove && count < maxAttempts) {
                    move = moveRequest();
                    myMovement();
                }
                methodLoser(validationMove, count, getGamer());
                controlWin();
                count = 0;
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!getGamer().getLoser()) {
                    while (!validationBuild && count < maxAttempts) {
                        move = buildingRequest();
                        myBuilding();
                    }
                    methodLoser(validationBuild, count, getGamer());
                }
            }
        } catch (ConcurrentModificationException ex) {
            Thread.currentThread().stop();
        }
    }

    /**
     * method myMovement
     * controls the phases of the action movement
     */
    private void myMovement() {
        validationMove = false;
        gamer.getMyGodCard().beforeOwnerMoving(this);
        for (God card : otherCards) {
            card.beforeOtherMoving(gamer);
        }

        if ((controlStandardParameter(getMove()) && getMove().getAction().equals(Mossa.Action.MOVE))) {
            baseMovement(getMove());
            getValidation(validationMove);

            gamer.getMyGodCard().afterOwnerMoving(this);
            for (God card : otherCards) {
                card.afterOtherMoving(gamer);
            }
        } else {
            getGameHandler().sendFailed(getGamer(), "##Coordinate non valide##");
            getValidation(validationMove);
        }
    }


    /**
     * method myBuilding
     * controls the phases of the action building
     */
    private void myBuilding() {
        validationBuild = false;
        gamer.getMyGodCard().beforeOwnerBuilding(this);
        for (God card : otherCards) {
            card.beforeOtherBuilding(gamer);
        }
        if ((controlStandardParameter(getMove())) && (validationMove) &&
                (getMove().getAction().equals(Mossa.Action.BUILD))) {
            baseBuilding(getMove());
            getValidation(validationBuild);
        } else {
            getGameHandler().sendFailed(getGamer(), "##Coordinate non valide##");
            getValidation(validationBuild);
        }
        gamer.getMyGodCard().afterOwnerBuilding(this);
        for (God card : otherCards) {
            card.afterOtherBuilding(gamer);
        }

    }

    /**
     * method baseMovement
     * controls if the action movement is correct
     */
    public void baseMovement(Mossa move) {
        Pawn p = getGamer().getPawn(move.getIdPawn());//save my pawn
        Cell end = table.getTableCell(move.getTargetX(), move.getTargetY());//save destination
        Cell start = table.getTableCell(p.getRow(), p.getColumn());//save myCell
        //control if the gamer can do one step
        if (getGamer().getSteps() == 0) {
            validationMove = true;
        } else {
            //control the base movement of the pawn is possible
            if (!table.controlBaseMovement(start, end)) {
                getGameHandler().sendFailed(getGamer(), "##Non puoi muoverti qui##");
                validationMove = false;
            } else {
                //control if the pawn can go up on level
                if ((gamer.getLevelsUp() == 0) && (end.getLevel() - start.getLevel() == 1)) {
                    getGameHandler().sendFailed(getGamer(), "##Non puoi salire di livello##");
                    validationMove = false;
                } else {
                    validationMove = getMyStep(start, end, p);
                }

            }
        }
    }

    /**
     * method getValidation
     * controls if the movement is valid
     */
    public void getValidation(boolean v) {
        if (!v) {
            count++;
            if (count > 0) {
                getGameHandler().sendMessage(getGamer(), "\u001B[31m" + "Tentativi rimanenti: " + (3 - count) + "\u001B[0m");
            }
        }
    }

    /**
     * method baseBuilding
     * controls if the action building is correct
     */
    public void baseBuilding(Mossa move) {
        Pawn p = getGamer().getPawn(move.getIdPawn());
        Cell end = table.getTableCell(move.getTargetX(), move.getTargetY());
        Cell start = table.getTableCell(p.getRow(), p.getColumn());
        //control if the pawn can build
        if (gamer.getBuilds() == 0) {
            validationBuild = true;
        } else {
            //control the base build of the pawn is possible
            if (!table.controlBaseBuilding(start, end)) {
                getGameHandler().sendFailed(getGamer(), "##Non puoi costruire qui##");
                validationBuild = false;
            } else {
                //do the build
                table.build(end);
                validationBuild = true;
            }
        }
    }

    /**
     * Standard win
     * controls if the gamer wins the match
     */
    private void controlWin() {
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
     * method controlStandardParameter
     *
     * @return true if the parameters are valid, or return false
     */
    public boolean controlStandardParameter(Mossa movement) {
        return (movement.getIdPawn() >= 0) && (movement.getIdPawn() <= 1) &&
                (movement.getTargetX() >= 0) && (movement.getTargetX() <= 5) &&
                (movement.getTargetY() >= 0) && (movement.getTargetY() <= 5);
    }

    /**
     * method nullEffectForGodCards: ONLY FOR GODS EFFECTS
     *
     * @param movement the optional movement (move or build)
     * @return if the gamer wants to do the optional movement of the god card
     */
    public boolean nullEffectForGodCards(Mossa movement) {
        return movement.getIdPawn() == -1 || movement.getTargetX() == -1 || movement.getTargetY() == -1;
    }

    /**
     * method getMyStep
     * method for do a movement
     *
     * @param startCell the start cell
     * @param endCell   the destination of the movment
     * @param myPawn    the pawn which moves
     * @return true
     */
    private boolean getMyStep(Cell startCell, Cell endCell, Pawn myPawn) {
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
    public void methodLoser(boolean b, int i, Gamer g) {
        //exceeded the number of attempts
        if ((!b) && (i >= 3)) {
            g.setLoser(true);
            getGameHandler().sendFailed(g, "##Hai esaurito i tentativi##");
            getGameHandler().getGame().broadcastMessage(getGamer().getName() + " ha esaurito i tentativi disponibili, è fuori dal gioco");
        } else {
            g.setLoser(false);
        }
        //general control of defeat
        if (g.getLoser()) {
            endOfTheMatch(getGameHandler());
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

    /**
     * method firstLockdown
     * controls if the pawns are locked or not
     *
     * @param gamer the current gamer
     */
    private void firstLockdown(Gamer gamer) {
        if (!gamer.getLoser()) {
            Pawn p0 = gamer.getPawn(0);
            Pawn p1 = gamer.getPawn(1);
            Cell p0Cell = table.getTableCell(p0.getRow(), p0.getColumn());
            Cell p1Cell = table.getTableCell(p1.getRow(), p1.getColumn());
            //control if my pawns can move or not
            boolean b0 = table.iCanMove(p0Cell);
            boolean b1 = table.iCanMove(p1Cell);
            if (!b0 && !b1) {
                gamer.getPawn(0).setICanPlay(false);
                gamer.getPawn(1).setICanPlay(false);
                //message for the gamer
                getGameHandler().sendFailed(gamer, "##Hai entrambe le pedine bloccate.##\n" +
                        "##Non puoi più muoverti.##\n");
                //message for all the players
                getGameHandler().getGame().broadcastMessage(getGamer() + " non può più muoversi");
                gamer.setLoser(true);
                getGameHandler().sendLockedPawn(1, gamer, true);
                getGameHandler().sendLockedPawn(0, gamer, true);
                endOfTheMatch(getGameHandler());
            } else if (!b1) {
                gamer.getPawn(0).setICanPlay(true);
                gamer.getPawn(1).setICanPlay(false);
                gamer.setLoser(false);
                getGameHandler().sendMessage(gamer, "\u001B[31m" + "##La pedina 1 è bloccata##" + "\u001B[0m");
                getGameHandler().sendLockedPawn(1, gamer, true);
                getGameHandler().sendLockedPawn(0, gamer, false);
            } else if (!b0) {
                gamer.getPawn(1).setICanPlay(true);
                gamer.getPawn(0).setICanPlay(false);
                gamer.setLoser(false);
                getGameHandler().sendMessage(gamer, "\u001B[31m" + "##La pedina 0 è bloccata##" + "\u001B[0m");
                getGameHandler().sendLockedPawn(0, gamer, true);
                getGameHandler().sendLockedPawn(1, gamer, false);
            } else {
                gamer.getPawn(1).setICanPlay(true);
                gamer.getPawn(0).setICanPlay(true);
                gamer.setLoser(false);
                getGameHandler().sendLockedPawn(0, gamer, false);
                getGameHandler().sendLockedPawn(1, gamer, false);
            }
        }
    }

    /**
     * method endOfTheMatch
     *
     * controls if the match will end for pawns locked or number of attempts exhausted of the gamers
     * @param h the networkHandlerServer
     */
    private void endOfTheMatch(NetworkHandlerServer h) {
        int p = h.getGame().getPlayersInGame().size();
        int k = 0;
        for (int i = 0; i < p; i++) {
            if (h.getGame().getPlayersInGame().get(i).getLoser()) {
                k++;
            }
        }
        if (k == (p - 1)) {
            for (int i = 0; i < p; i++) {
                if (!h.getGame().getPlayersInGame().get(i).getLoser()) {
                    h.getGame().setWinner(h.getGame().getPlayersInGame().get(i));
                }
            }
        }
    }

}
