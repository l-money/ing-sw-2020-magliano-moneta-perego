package santorini.model;

import java.io.Serializable;

public class Bag implements Serializable {
    private Brick brick;
    private int[] counterBrick;

    public Bag (){
        counterBrick = new int[4];
        counterBrick[0]=22; /**Brick level 1*/
        counterBrick[1]=18; /**Brick level 2*/
        counterBrick[2]=14; /**Brick level 3*/
        counterBrick[3]=18; /**Brick level 4 = dome*/
    }

    public int[] getCounterBrick() {
        return counterBrick;
    }

    public boolean controlExistBrick (int levelBrick){
       return (counterBrick[levelBrick-1]!=0);
       /** if result is not 0 return true, else return false*/
    }

    public int extractionBrick (int levelBrick){
        boolean exist = controlExistBrick(levelBrick);
        if (exist) {
            counterBrick[levelBrick-1] = counterBrick[levelBrick-1]-1;
            return levelBrick;
        } else{
            return -10;
        }
    }

    public void reinsertBrick (int levelBrick){

        counterBrick[levelBrick-1] = counterBrick[levelBrick-1]+1;
    }
}



