package santorini.model;

import santorini.model.utils.Exception.TableException;

public class Cell {

    private int level;
    private boolean free;
    private boolean complete;
    private int x;
    private int y;
    private Table table;
    private final int MAX_LEVEL = 3;

    public Table getTable() {
        return table;
    }

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

    /**
     * method set free
     *
     * @param free free taked current state
     */
    public void setFree(boolean free) {
        this.free = free;
    }

    /**
     * @return comlete
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

    public void initCell() {
        level = 0;
        free = true;
        complete = false;
    }

    public boolean build() {
        if (isFree() && !isComplete()) {
            this.level++;
        } else {
            return false;
        }
        if (level == MAX_LEVEL) {
            setComplete(true);
        }
        return true;
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


/**
 * public boolean build() throws TableException {
 * if (this.level + 1 > 3 || !isFree()) {
 * throw new TableException();
 * } else if (this.level + 1 == 3) {
 * complete = true;
 * }
 * if (blocchi.extractionBrick(this.level + 1)) { //controllo della cella
 * this.level++;
 * return true;
 * } else {
 * return false;
 * }
 * <p>
 * }
 */
