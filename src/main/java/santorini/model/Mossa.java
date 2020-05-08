package santorini.model;

import santorini.View;

import java.io.Serializable;

public class Mossa implements Serializable {
    public static enum Action {
        MOVE,
        BUILD,
    }
    private Action action;
    private int targetX;
    private int targetY;
    private int idPawn;
    private View view;


    public Mossa(Action action, int idPawn, int targetX, int targetY) {
        this.action = action;
        this.targetX = targetX;
        this.targetY = targetY;
        this.idPawn = idPawn;
    }

    /**
     * method getAction
     * @return action
     */
    public Action getAction() {
        return action;
    }

    /**
     * method getTargetX
     * @return row
     */
    public int getTargetX() {
        return targetX;
    }

    /**
     * method getTargetY
     * @return column
     */
    public int getTargetY() {
        return targetY;
    }

    /**
     * method getIdPawn
     * @return id of the pawn
     */
    public int getIdPawn() {
        return idPawn;
    }

    /**
     * method setAction
     * @param action .
     */
    public void setAction(Action action) {
        this.action = action;
    }
    /**
     * method setTargetX
     * @param  targetX .
     */
    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }
    /**
     * method setIdPawn
     * @param targetY .
     */
    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }

    /**
     * method setIdPawn
     * @param idPawn id pawn
     */
    public void setIdPawn(int idPawn) {

        this.idPawn = idPawn;
    }

    /**
     * method setMyMossa
     * @param a action MOVE or BUILD
     * @param i id Pawn
     * @param x row
     * @param y column
     * @return my Mossa
     */
    public Mossa setMyMossa(Action a, int i, int x, int y) {
        Mossa m;
        if (a.equals(Action.MOVE)) {
            return m = new Mossa(Action.MOVE, i, x, y);
        } else {
            if (a.equals(Action.BUILD)) {
                return m = new Mossa(Action.BUILD, i, x, y);
            } else {
                return m = null;
            }
        }
    }

}


