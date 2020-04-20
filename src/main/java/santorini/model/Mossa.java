package santorini.model;

import java.io.Serializable;

public class Mossa implements Serializable {
    public static enum Action {
        MOVE,
        BUILD
    }

    private Action action;
    private int targetX;
    private int targetY;
    private int idPawn;


    public Mossa(Action action, int targetX, int targetY, int idPawn) {
        this.action = action;
        this.targetX = targetX;
        this.targetY = targetY;
        this.idPawn = idPawn;
    }

    public Action getAction() {
        return action;
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public int getIdPawn() {
        return idPawn;
    }
}
