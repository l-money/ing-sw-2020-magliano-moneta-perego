package santorini.model;

import java.io.Serializable;

/**
 * Class Bag
 * @author G. Perego
 */

public class Bag implements Serializable {
    /**private Brick brick;*/
    private int[] counterBrick;

    /**
     * constructor of class Bag
     * Bag contains: the brick with its level and an array of integer that controls the number of the bricks of the game
     * Level 4 is the dome, the highest level of the bricks
      */

    public Bag (){
        counterBrick = new int[4];
        this.counterBrick[0] = 22;
        this.counterBrick[1] = 18;
        this.counterBrick[2] = 14;
        this.counterBrick[3] = 18;
    }
    /**
     * method getCounterBrick
     * @return counterBrick
     */

    public int[] getCounterBrick() {
        return this.counterBrick;
    }

    /**
     * method controlExistBrick
     * @param levelBrick level of the brick to check
     * @return true if the result of the required level of the brick is not 0, else return false
     */

    public boolean controlExistBrick (int levelBrick){
        return (this.counterBrick[levelBrick - 1] != 0);
    }

    /**
     * method extractionBrick
     *
     * @param levelBrick level of the brick to extract
     * @return the level of the brick if the counter of that level brick is not empty, else return false
     * the method: controls the existence of the brick, substracts one from the brick level counter,
     * returns the level of the brick
     */

    public boolean extractionBrick(int levelBrick) {
        boolean exist = controlExistBrick(levelBrick);
        if (exist) {
            this.counterBrick[levelBrick - 1] = this.counterBrick[levelBrick - 1] - 1;
            return true;
        } else {
            return false;
        }
    }

    /**
     *method reinsertBrick
     *@param levelBrick level of the brick to insert
     *add one to the brick level counter
     */

    public void reinsertBrick (int levelBrick){

        this.counterBrick[levelBrick - 1] = this.counterBrick[levelBrick - 1] + 1;
    }
}
