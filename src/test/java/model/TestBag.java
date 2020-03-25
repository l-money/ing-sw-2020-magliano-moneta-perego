package model;

import org.junit.Before;
import org.junit.Test;
import santorini.model.Bag;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class TestBag {
    private Bag bag;

    @Before
    public void before(){
        bag = new Bag();
    }

    @Test
    public void testControlExistBrick (){
        assertTrue(bag.controlExistBrick(2));
        bag.getCounterBrick()[3] = 0;
        assertFalse(bag.controlExistBrick(4));
    }

    //TODO test extractionBrick, test insertionBrick, test constructor method
}
