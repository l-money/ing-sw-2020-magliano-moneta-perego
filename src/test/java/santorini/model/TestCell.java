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
        cell.setLevel(1);
        assertEquals(1, cell.getLevel());
        cell.setLevel(3);
        assertEquals(3, cell.getLevel());
        cell.setLevel(4);
        assertEquals(3, cell.getLevel());
        assertTrue(cell.isComplete());
        assertFalse(cell.isFree());
    }

    *//**
     * method that tests if a cell is free or not
     *//*
    @Test
    public void testCellFree() {
        Pawn pawn = new Pawn();
        //case 1:pawn into the cell
        cell.setPawn(pawn);
        assertFalse(cell.isFree());
        assertNotNull(cell.getPawn());
        //case 2:set free = set pawn null
        cell.setFree(true);
        assertTrue(cell.isFree());
        assertNull(cell.getPawn());
        //case 3:set not free
        cell.setFree(false);
        assertFalse(cell.isFree());
        //case 4:
        cell.setPawn(pawn);
        cell.setFree(true);
        assertTrue(cell.isFree());
        assertNull(cell.getPawn());
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
        assertFalse(cell.isFree());
        cell.setLevel(4);
        assertEquals(3, cell.getLevel());
        assertTrue(cell.isComplete());
        assertFalse(cell.isFree());
    }

*/
}
