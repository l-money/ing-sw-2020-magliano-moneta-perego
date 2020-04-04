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

    @Test
    public void testLevelCell() {
        cell.setLevel(1);
        int k = cell.getLevel();
        assertEquals(1, k);
        cell.setLevel(4);
        assertTrue(true == cell.isComplete());
    }

    @Test
    public void testStartCell() {
        cell.startCell();
        assertEquals(0, cell.getLevel());
        assertTrue(true == cell.isFree());
        assertTrue(false == cell.isComplete());
    }

    @Test
    public void testCell() {

    }



}
