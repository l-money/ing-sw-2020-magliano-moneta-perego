package santorini.model;

import santorini.model.utils.Exception.TableException;

public class Cell {

    private int level;
    private boolean free;
    private boolean complete;
    private int X, Y;
    private Bag blocchi;

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    /**
     * Cell with param x,y,b that i will use to initialize
     *
     * @param x
     * @param y
     * @param b
     */
    public Cell(int x, int y, Bag b) {
        this.level = 0;
        this.free = true;
        this.complete = false;
        this.X = x;
        this.Y = y;
        this.blocchi = b;
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
     * @param level that set level
     * @throws TableException the class where there are exception if the level
     *                        is up 3 or under 0
     */
    public void setLevel(int level) throws TableException {
        if (level > 3 || level < 0) {
            throw new TableException();
        }
        this.level = level;
    }

    /**
     * @return
     * @throws TableException
     */
    public boolean build() throws TableException {
        if (this.level + 1 > 3 || !isFree()) {
            throw new TableException();
        } else if (this.level + 1 == 3) {
            complete = true;
        }
        if (blocchi.extractionBrick(this.level + 1)) {
            this.level++;
            return true;
        } else {
            return false;
        }

    }

    /**
     * method idFree
     *
     * @return if the cell is free
     */
    public boolean isFree() {
        return free;
    }

    /**
     * @param free
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
}
