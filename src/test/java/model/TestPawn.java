package model;

import org.junit.Before;
import org.junit.Test;
import santorini.model.Cell;
import santorini.model.Pawn;
import santorini.model.Table;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Class TestPawn
 *
 * @author G. Perego
 */


public class TestPawn {
    private Pawn pawn;

    @Before
    public void before() {
        pawn = new Pawn();
    }

    /**
     * method that tests getIdPawn
     */
    @Test
    public void testGetIdPawn(){
        pawn.setIdPawn(0);
        assertEquals(0, pawn.getIdPawn());
    }

    /**
     * method that tests getIdGamer
     */

    @Test
    public void testGetIdGamer(){
        pawn.setIdGamer(0);
        assertEquals(0, pawn.getIdGamer());
    }
    /**
     * method that tests pastLevel and presentLevel
     */

    @Test
    public void testLevelsPawn(){
        pawn.setPastLevel(0);
        pawn.setPresentLevel(2);
        int x = pawn.getPastLevel();
        int y = pawn.getPresentLevel();
        assertEquals(0, x);
        assertEquals(2, y);
    }

    /**
     * method that tests colorPawn, the team of the pawn
     */
    @Test
    public void testColorPawn(){
        pawn.setColorPawn(Color.GREEN);
        Color c = pawn.getColorPawn();
        assertEquals(Color.GREEN, c);
    }

    /**
     * method that tests the pawn's position
     */
    @Test
    public void testPawnPosition() {
        pawn.setRow(0);
        pawn.setColumn(0);
        assertEquals(0, pawn.getRow());
        assertEquals(0, pawn.getColumn());

    }

    /**
     * method that tests if the pawn fills a new position
     */

    @Test
    public void testSetCellNotFree() {
        pawn.setRow(2);
        pawn.setColumn(2);
        Table table = new Table();
        Cell myCell;
        myCell = table.getTableCell(2, 2);
        pawn.setCellNotFree(myCell);
        assertFalse(myCell.isFree());
    }
}
