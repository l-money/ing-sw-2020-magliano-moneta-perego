package santorini.model;

import santorini.model.utils.Exception.TableException;

public class Cell {

    private int level;
    private boolean free;
    private boolean complete;
    private int x;
    private int y;


    /**
     * Cell with param x,y,b that i will use to initialize
     */
    public Cell() {
        this.level = 0;
        this.free = true;
        this.complete = false;
        this.x = x;
        this.y = y;
    }


//    private Pawn pawn; //domani con il pawn committato

//    public void buildCell (int levelBuild){
//        if(free){
//            levelBuild = 0;
//            if(levelBuild == 0){
//               this.level = levelBuild;
//            }
//        }else{
//            levelBuild = 1;
//            if(levelBuild == 1){
//                this.level = levelBuild;
//            }
//            levelBuild = 2;
//            if(levelBuild == 2) {
//                this.level = levelBuild;
//            }
//            levelBuild = 3;
//            if(levelBuild == 3) {
//                this.level = levelBuild;
//            }
//
//        }
//    }

    /**
     * method get level
     *
     * @return level of the brick
     */
    public int getLevel() {
        return level;
    }

    /**
     * method set level
     *
     * @param newLevel that set level
     * @throws TableException the class where there are exception if the level
     *                        is up 3 or under 0
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
     * @return
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

    public void startCell() {
        level = 0;
        free = true;
        complete = false;
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
