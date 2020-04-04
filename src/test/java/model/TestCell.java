/**
 * @author Magliano
 */
package model;

import org.junit.Before;
import org.junit.Test;
import santorini.model.Cell;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestCell {

    Cell cell;

    @Before
    public void before() {
        cell = new Cell();
    }

    /**
     * method that tests the level and if te cell is complete
     */
    @Test
    public void testLevelCell() {
        cell.setLevel(1);
        int k = cell.getLevel();
        assertEquals(1, k);
        cell.setLevel(4);
        assertTrue(true == cell.isComplete());
    }

    /**
     * method that tests if the test is 0 at start and the cell is free or complete
     */
    @Test
    public void testStartCell() {
        cell.startCell();
        assertEquals(0, cell.getLevel());
        assertTrue(true == cell.isFree());
        assertTrue(false == cell.isComplete()); //stavo pensando ma in realtà la cella non dovrebbe essere not free?
    }

    @Test
    public void testCell() {

    }


}
