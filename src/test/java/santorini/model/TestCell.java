/**
 * @author Magliano
 */
package santorini.model;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class TestCell {

    /*private Cell cell;

    @Before
    public void before() {
        cell = new Cell();
    }

    *//**
     * method that tests if the level is 1
     * and if the level is 4 the cell is complete.
     *//*
    @Test
    public void testStartCell() {
        assertEquals(0, cell.getLevel());
        assertTrue(cell.isFree());
        assertFalse(cell.isComplete());
        assertNull(cell.getPawn());
    }

    *//**
     * method that tests the coordinate of the cell
     *//*
    @Test
    public void testCoordinateCell() {
        cell.setX(0);
        cell.setY(0);
        assertEquals(0, cell.getX());
        assertEquals(0, cell.getY());
    }

    *//**
     * method that tests the level of the cell
     *//*
    @Test
    public void testCellLevel() {
        cell.setLevel(0);
        assertEquals(0,cell.getLevel());
        cell.setLevel(1);
        assertEquals(1,cell.getLevel());
        cell.setLevel(2);
        assertEquals(2,cell.getLevel());
        cell.setLevel(3);
        assertEquals(3,cell.getLevel());
        cell.setLevel(4);
        assertEquals(3,cell.getLevel());
    }

    *//**
     * method that tests if a cell is free or not
     *//*
    @Test
    public void testCellFree() {
        cell.setFree(true);
        assertTrue(cell.isFree());
    }

    *//**
     * method that tests is a cell is complete
     *//*
    @Test
    public void testCellComplete() {
        cell.setLevel(2);
        cell.setComplete(true);
        assertEquals(2, cell.getLevel());
        assertTrue(cell.isComplete());
        cell.setLevel(3);
        cell.setComplete(false);
        assertEquals(3, cell.getLevel());
        assertTrue(!cell.isComplete());
    }

*/
}
