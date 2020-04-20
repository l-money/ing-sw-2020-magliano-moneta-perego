package santorini.model;

import java.io.Serializable;

public class Cell implements Serializable {

    private int level;
    private boolean free;
    private boolean complete;
    private int x;
    private int y;
    private final int MAX_LEVEL = 3;
    private Pawn pawn = null;


    /**
     * Cell with param x,y that i will use to initialize
     */
    public Cell() {
        this.level = 0;
        this.free = true;
        this.complete = false;
    }

    /**
     * method get level
     *
     * @return level of the brick
     */
    public int getLevel() {
        return level;
    }

    /**
     * method setLevel
     * this method see if the level is 4 is completed
     *
     * @param newLevel
     */
    public void setLevel(int newLevel) {
        level = newLevel;
        if (level == 4) {
            setComplete(true);
        }
    }

    /**
     * method isFree
     *
     * @return if the cell is free
     */
    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    /**
     * free this cell deleting pawn reference
     */
    public void free() {
        this.free = true;
        pawn = null;
    }

    /**
     * Puts a pawn in this cell if free and sets it not free
     *
     * @param pawn pawn to put in this cell
     * @return operation success or failure
     */
    public boolean setPawn(Pawn pawn) {
        if (isFree() && !isComplete()) {
            this.pawn = pawn;
            this.free = false;
            return true;
        } else {
            return false;
        }
    }

    public Pawn getPawn() {
        return pawn;
    }

    /**
     * @return complete
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * @param complete
     */
    public void setComplete(boolean complete) {
        this.complete = complete;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }
}


