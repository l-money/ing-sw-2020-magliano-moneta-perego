package model;

import org.junit.Before;
import org.junit.Test;
import santorini.model.Bag;
import santorini.model.Brick;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestBag {
    private Bag bag, box;
    private Brick brick;
    private int y=2; /**Random level 1*/
    private int y1=3; /**Random level 2*/

    @Before
    public void before()
    {
        bag = new Bag();
        brick = new Brick(y);
    }

    @Test
    public void testBagConstructionCounterArray() {
        int lenghtBagCounterBrick = bag.getCounterBrick().length;
        assertTrue(lenghtBagCounterBrick == 4);
        assertTrue(bag.getCounterBrick()[0]==22);
        assertTrue(bag.getCounterBrick()[1]==18);
        assertTrue(bag.getCounterBrick()[2]==14);
        assertTrue(bag.getCounterBrick()[3]==18);
    } /**maybe I can separate the testLenghtOfCounterArray and the testOfValuesIntoCounterArray*/

    @Test
    public void testControlExistBrick (){
        assertTrue(bag.controlExistBrick(y));
        bag.getCounterBrick()[y1] = 0;
        assertFalse(bag.controlExistBrick(y1+1));
    }

    @Test
    public void testExtractionBrick () {
        int pastCounterBrick = bag.getCounterBrick()[y-1];
        int controlNewBrick;
        controlNewBrick = bag.extractionBrick(y);
        assertTrue((pastCounterBrick)==(bag.getCounterBrick()[y - 1]+1));
        assertTrue (controlNewBrick == y);
        bag.getCounterBrick()[y-1]=0;
        controlNewBrick = bag.extractionBrick(y);
        assertTrue(controlNewBrick == -10);
    } /** newCounter = pastCounter -1*/

    @Test
    public void testInsertionBrick(){
        int pastCounterBrick = bag.getCounterBrick()[y-1];
        bag.reinsertBrick(y);
        assertTrue((pastCounterBrick+1)==(bag.getCounterBrick()[y - 1]));
    }/** newCounter = pastCounter +1*/
}
