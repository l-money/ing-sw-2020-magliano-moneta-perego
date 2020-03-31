package santorini.model;

public class Cell {

    private int level;
    private boolean free;
    private boolean complete;
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

    public void setLevel(int level) {
        this.level = level;
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
