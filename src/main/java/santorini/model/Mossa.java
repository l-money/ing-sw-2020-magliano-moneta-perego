package santorini.model;

import java.io.Serializable;

public class Mossa implements Serializable {
    public static enum Azione {
        MOVE,
        BUILD
    }

    private Azione action;
    private Cell target;
    private Pawn pawn;
    //private int coordX, coordY, idPlayer, idPawn;


    public Mossa(Azione action, Cell target, Pawn pawn) {
        this.action = action;
        this.target = target;
        this.pawn = pawn;
    }

    public Azione getAction() {
        return action;
    }

    public Cell getTarget() {
        return target;
    }

    public Pawn getPawn() {
        return pawn;
    }

}
