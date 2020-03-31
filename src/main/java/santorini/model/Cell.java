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
//            if(levelBuild == 1){
//               this.level = levelBuild;
//            }
//        }else{
//            levelBuild = 1;
//            if(levelBuild == 2){
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


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) throws TableException {
        if (level > 3 || level < 0) {
            throw new TableException();
        }
        this.level = level;
    }

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

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
