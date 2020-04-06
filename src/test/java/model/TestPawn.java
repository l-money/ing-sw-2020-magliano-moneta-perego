package model;

import org.junit.Before;
import org.junit.Test;
import santorini.model.Cell;
import santorini.model.Pawn;
import santorini.model.Table;
import santorini.model.utils.Color;

import static org.junit.Assert.*;

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
        int x = pawn.getIdPawn();
        assertEquals(1,x);
        pawn.setIdPawn(0);
        assertEquals(0, pawn.getIdPawn());
    }

    /**
     * method that tests getIdGamer
     */

    @Test
    public void testGetIdGamer(){
        int x = pawn.getIdGamer();
        assertEquals(1,x);
        pawn.setIdGamer(0);
        assertEquals(0, pawn.getIdGamer());
    }
    /**
     * method that tests pastLevel and presentLevel
     */

    @Test
    public void testLevelsPawn(){
        int x = pawn.getPastLevel();
        int y = pawn.getPresentLevel();
        assertEquals(0, x);
        assertEquals(1,y);
        pawn.setPastLevel(2);
        pawn.setPresentLevel(2);
        assertEquals(2, pawn.getPastLevel());
        assertEquals(2, pawn.getPresentLevel());
    }

    /**
     * method that tests colorPawn, the team of the pawn
     */
    @Test
    public void testColorPawn(){
        Color c = pawn.getColorPawn();
        assertEquals(Color.BLUE,c);
        pawn.setColorPawn(Color.GREEN);
        assertEquals(Color.GREEN, pawn.getColorPawn());
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
