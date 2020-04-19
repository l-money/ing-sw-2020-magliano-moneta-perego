package santorini.model;

import org.junit.Before;
import org.junit.Test;
import santorini.model.Bag;

import static org.junit.Assert.*;

/**
 * Class TestBag
 * @author G. Perego
 */

public class TestBag {
    private Bag bag;
    //private Brick brick;


    @Before
    public void before()
    {
        bag = new Bag();
        //brick = new Brick(y);
    }

    /**
     * method that tests there are four elements in the array counterBrick created
     * and also tests into the four cells of the array there are: 22 bricks of level 1,
     * 18 bricks of level 2, 14 bricks of level 3 and 18 domes (bricks of level 4)
     */

    @Test
    public void testBagConstructionCounterArray() {
        int lenghtBagCounterBrick = bag.getCounterBrick().length;
        assertEquals(4, lenghtBagCounterBrick);
        assertEquals(22, bag.getCounterBrick()[0]);
        assertEquals(18, bag.getCounterBrick()[1]);
        assertEquals(14, bag.getCounterBrick()[2]);
        assertEquals(18, bag.getCounterBrick()[3]);
    }

    /**
     * method that tests the existence of the brick into the bag
     * the input parameter is the level of the brick to check
     * it returns true if the result of the required level of the brick is not 0, else return false
     * it checks if  a whatever cell of the counterBrick is not empty
     * it checks if a whatever cell of the counterBrick is empty
     */

    @Test
    public void testControlExistBrick (){
        assertTrue(bag.controlExistBrick(2));
        bag.getCounterBrick()[3] = 0;
        assertFalse(bag.controlExistBrick(4));
    }

    /**
     *method that tests the correct extraction of the brick
     *the input parameter is level of the brick to extract
     *it returns the level of the brick if the counter of that level brick is not empty, else return -10
     *the method: controls the existence of the brick, substracts one from the brick level counter,
     *returns the level of the brick
     *it checks: newCounter = pastCounter -1
     *it checks: the method returns the level of the brick
     *it checks: the method returns -10 if a whatever cell of the counterBrick is empty
     */

    @Test
    public void testExtractionBrick () {
        int pastCounterBrick = bag.getCounterBrick()[1];
        boolean controlNewBrick;
        controlNewBrick = bag.extractionBrick(2);
        assertEquals((pastCounterBrick), (bag.getCounterBrick()[1] + 1));
        assertTrue(controlNewBrick);
        bag.getCounterBrick()[1] = 0;
        controlNewBrick = bag.extractionBrick(2);
        assertFalse(controlNewBrick);
    }

    /**
     *method that tests the correct insertion of the brick
     *the input parameter is the level of the brick to insert
     *the insertionBrick adds one to the brick level counter
     *it checks: newCounter = pastCounter +1
     */

    @Test
    public void testInsertionBrick(){
        int pastCounterBrick = bag.getCounterBrick()[1];
        bag.reinsertBrick(2);
        assertEquals((pastCounterBrick + 1), (bag.getCounterBrick()[1]));
    }
}
