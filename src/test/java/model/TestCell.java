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

    private Cell cell;

    @Before
    public void before() {
        cell = new Cell();
    }

    /**
     * method that tests if the level is 1
     * and if the level is 4 the cell is complete.
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
     * method that tests the Cell with level 0 at start and if the cell is free or complete.
     */
    @Test
    public void testStartCell() {
        cell.initCell();
        assertEquals(0, cell.getLevel());
        assertTrue(true == cell.isFree());
        assertTrue(false == cell.isComplete()); //stavo pensando ma in realtà la cella non dovrebbe essere not free?
    }


}
