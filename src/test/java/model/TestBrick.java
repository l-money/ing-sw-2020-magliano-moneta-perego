package model;

import org.junit.Before;
import org.junit.Test;
import santorini.model.Brick;

import static org.junit.Assert.assertTrue;

public class TestBrick {
    public Brick b;

    @Before
    public void before(){
        b = new Brick(1);
    }

    @Test
    public void testGetBrick(){
        int x = b.getLevel();
        assertTrue(x==1);
    }

    @Test
    public void testSetBrick(){
        b.setLevel(4);
        int x = b.getLevel();
        assertTrue(x==4);
    }
}
