package santorini.model;

import org.junit.Before;
import org.junit.Test;
import santorini.model.Brick;

import static org.junit.Assert.assertEquals;

/**
 * Class TestBrick
 * @author G. Perego
 */

public class TestBrick {
    private  Brick b;

    @Before
    public void before(){
        b = new Brick(1);
    }

    /**
     * method that tests getLevel method
     * it returns level of the brick
     * it checks the getLevel provides some desired value
     */

    @Test
    public void testGetBrick(){
        int x = b.getLevel();
        assertEquals(1, x);
    }

    /**
     * method that tests setLevel method
     * the input parameter is the  level of the brick
     * it checks the setlevel sets some desired value into level of the brick
     */

    @Test
    public void testSetBrick(){
        b.setLevel(4);
        int x = b.getLevel();
        assertEquals(4, x);
    }


}
