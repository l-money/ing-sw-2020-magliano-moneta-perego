package santorini.model;

import java.io.Serializable;

/**
 * Class Brick
 * @author G. Perego
 */

public class Brick  implements Serializable {
    private int level;

    /**
     * constructor of class Brick
     * @param level level of the Brick in height
     */
    public Brick(int level) {
        this.level = level;
    }

    /**
     * method getLevel
     * @return level of the brick
     */
    public int getLevel() {
        return level;
    }

    /**
     * method setLevel
     * @param newLevel new level of the brick in height
     * */
    public void setLevel(int newLevel){
        level = newLevel;
    }
}

