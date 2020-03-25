package santorini.model;

import java.io.Serializable;

public class Brick  implements Serializable {
    private int level;


    /**set new level of the brick*/
    public Brick(int level) {
        this.level = level;
    }

    /** metode getLevel*/
    public int getLevel() {
        return level;
    }

    /**metode setLevel, change the level of the brick*/
    public void setLevel(int newLevel){
        level = newLevel;
    }
}

