package santorini.model;

import org.junit.Before;
import org.junit.Test;
import santorini.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Class TestTable
 *
 * @author Magliano
 */

public class TestTable implements Serializable {
    private Table table;

    @Before
    public void before() {
        table = new Table();
    }

    /**
     * methods that test all 25 cells in the table.
     * in particular i'm testing coordinates (x,y), the level at 0 for every cell,
     * and if the cell is free or complete.
     */
    @Test
    public void testStartTable() {
        int i, j;
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                assertEquals(i, table.getTableCell(i, j).getX());
                assertEquals(j, table.getTableCell(i, j).getY());
                assertEquals(0, table.getTableCell(i, j).getLevel());
                assertTrue(table.getTableCell(i, j).isFree());
                assertFalse(table.getTableCell(i, j).isComplete());
                assertNull(table.getTableCell(i, j).getPawn());
            }
        }
    }

    /**
     * methods that test the near cells from the cell where my pawn stay.
     * In this case i'm testing the cell (0,0) and the nearest cell are c1(0,1), c2(1,0) c3(1,1)
     */
    @Test
    public void testNearCell1() {
        List<Cell> nearCell;
        Cell myCell;
        myCell = table.getTableCell(0, 0);
        nearCell = table.searchAdjacentCells(myCell);
        int lenght = nearCell.size();
        assertEquals(3, lenght);
        assertEquals(nearCell.get(0), table.getTableCell(0, 1));
        assertEquals(nearCell.get(1), table.getTableCell(1, 0));
        assertEquals(nearCell.get(2), table.getTableCell(1, 1));
    }

    /**
     * methods that test the near cells from the cell where my pawn stay.
     * In this case i'm testing the cell (1,1) and the nearest cell are c1(0,0), c2(0,1), c3(0,2)
     * c4(1,0), c5(1,2), c6(2,0), c7(2,1), c8(2,2)
     */
    @Test
    public void testNearCell2() {
        List<Cell> nearCell; //= new ArrayList<Cell>();
        Cell myCell;
        myCell = table.getTableCell(1, 1);
        nearCell = table.searchAdjacentCells(myCell);
        int lenght = nearCell.size();
        assertEquals(8, lenght);
        assertEquals(nearCell.get(0), table.getTableCell(0, 0));
        assertEquals(nearCell.get(1), table.getTableCell(0, 1));
        assertEquals(nearCell.get(2), table.getTableCell(0, 2));
        assertEquals(nearCell.get(3), table.getTableCell(1, 0));
        assertEquals(nearCell.get(4), table.getTableCell(1, 2));
        assertEquals(nearCell.get(5), table.getTableCell(2, 0));
        assertEquals(nearCell.get(6), table.getTableCell(2, 1));
        assertEquals(nearCell.get(7), table.getTableCell(2, 2));
    }

    //34

    /**
     * methods that test the near cells from the cell where my pawn stay.
     * In this case i'm testing the cell (3,4) and and the nearest cell are c1(2,3), c2(2,4), c3(3,3)
     * c4(4,3), c5(4,4)
     */
    @Test
    public void testNearCell3() {
        List<Cell> nearCell; // = new ArrayList<Cell>();
        Cell myCell;
        myCell = table.getTableCell(3, 4);
        nearCell = table.searchAdjacentCells(myCell);
        int lenght = nearCell.size();
        assertEquals(5, lenght);
        assertEquals(nearCell.get(0), table.getTableCell(2, 3));
        assertEquals(nearCell.get(1), table.getTableCell(2, 4));
        assertEquals(nearCell.get(2), table.getTableCell(3, 3));
        assertEquals(nearCell.get(3), table.getTableCell(4, 3));
        assertEquals(nearCell.get(4), table.getTableCell(4, 4));
    }

    /**
     * methods that test the max distance between two cell: cell1(x1,y1), cell2(x2,y2)
     * In this case i'm testing the distance between the cell c1(1,1) and the cell c2 (0,2).
     * I must see that max distance is 1.
     */
    @Test
    public void testMaxDistance1() {
        Cell c1;
        Cell c2;
        c1 = table.getTableCell(1, 1);
        c2 = table.getTableCell(0, 2);
        int distance;
        distance = table.walkNearCell(c1, c2);
        assertEquals(1, distance);
    }

    /**
     * methods that test the max distance between two cell: cell1(x1,y1), cell2(x2,y2)
     * In this case i'm testing the distance between the cell c1(4,3) and the cell c2 (4,2).
     * I must see that max distance is 1.
     */
    @Test
    public void testMaxDistance2() {
        Cell c1;
        Cell c2;
        c1 = table.getTableCell(4, 3);
        c2 = table.getTableCell(4, 2);
        int distance;
        distance = table.walkNearCell(c1, c2);
        assertEquals(1, distance);
    }

    /**
     * method that tests getTableCell
     */
    @Test
    public void testGetTableCell() {
        Pawn p = new Pawn();
        table.getTableCell(0, 0).setLevel(2);
        table.getTableCell(0, 0).setPawn(p);
        assertEquals(2, table.getTableCell(0, 0).getLevel());
        assertNotNull(table.getTableCell(0, 0).getPawn());
        assertEquals(p, table.getTableCell(0, 0).getPawn());
        assertFalse(table.getTableCell(0, 0).isFree());
    }

    /**
     * method that tests setACell for the tests
     */
    @Test
    public void testSetACell() {
        Pawn p = new Pawn();
        table.setACell(0, 0, 1, true, false, null);
        Cell c = table.getTableCell(0, 0);
        assertEquals(0, c.getX());
        assertEquals(0, c.getY());
        assertEquals(1, c.getLevel());
        assertTrue(c.isFree());
        assertTrue(!c.isComplete());
        assertNull(c.getPawn());
        table.setACell(0, 1, 2, true, false, p);
        c = table.getTableCell(0, 1);
        assertEquals(0, c.getX());
        assertEquals(1, c.getY());
        assertEquals(2, c.getLevel());
        assertTrue(!c.isFree());
        assertTrue(!c.isComplete());
        assertEquals(p, c.getPawn());
        table.setACell(0, 1, 2, false, true, null);
        c = table.getTableCell(0, 1);
        assertEquals(0, c.getX());
        assertEquals(1, c.getY());
        assertEquals(2, c.getLevel());
        assertTrue(c.isComplete());
        assertNull(c.getPawn());
        table.setACell(0, 1, 4, true, true, p);
        c = table.getTableCell(0, 1);
        assertEquals(0, c.getX());
        assertEquals(1, c.getY());
        assertEquals(3, c.getLevel());
        assertTrue(c.isFree());
        assertTrue(c.isComplete());
        assertNull(c.getPawn());

    }


    @Test
    public void testICanMove() {
        Pawn p0 = new Pawn();
        Pawn q0 = new Pawn();
        table.setACell(0, 0, 0, false, false, p0);
        Cell s = table.getTableCell(0, 0);
        table.setACell(0, 1, 3, false, true, null);
        table.setACell(1, 0, 2, true, false, null);
        table.setACell(1, 1, 1, false, false, q0);
        assertFalse(table.iCanMove(s));
        table.setACell(1, 1, 1, true, false, null);
        assertTrue(table.iCanMove(s));
        //v.printTable(table);

    }

    @Test
    public void testICanBuild() {
        Pawn p0 = new Pawn();
        table.setACell(0, 0, 1, false, false, p0);
        table.setACell(0, 1, 3, false, true, null);
        table.setACell(1, 0, 3, false, true, null);
        table.setACell(1, 1, 3, false, true, null);
        assertFalse(table.iCanBuild(table.getTableCell(p0.getRow(), p0.getColumn())));
        table.setACell(1, 1, 2, true, false, null);
        assertTrue(table.iCanBuild(table.getTableCell(p0.getRow(), p0.getColumn())));
    }

    /**
     * method that tests controlBaseMovement
     */
    @Test
    public void testControlBaseMovement() {
        View v = new View(null);
        Pawn p0 = new Pawn();
        Pawn q1 = new Pawn();
        p0.setIdPawn(0);
        p0.setIdGamer(0);
        q1.setIdPawn(1);
        q1.setIdGamer(1);
        table.setACell(0, 0, 0, false, false, q1);
        table.setACell(0, 1, 3, false, true, null);
        table.setACell(1, 0, 3, true, false, null);
        table.setACell(1, 1, 1, false, false, p0);
        table.setACell(0, 2, 2, true, false, null);
        table.setACell(1, 2, 1, true, false, null);
        v.setTable(table);
        v.printTable();
        //control adjacentCells
        boolean b = table.controlBaseMovement(table.getTableCell(1, 1), table.getTableCell(3, 3));
        assertFalse(b);
        //control is not complete
        b = table.controlBaseMovement(table.getTableCell(1, 1), table.getTableCell(0, 1));
        assertFalse(b);
        //control is free and null
        b = table.controlBaseMovement(table.getTableCell(1, 1), table.getTableCell(0, 0));
        assertFalse(b);
        //control movement is possible
        b = table.controlBaseMovement(table.getTableCell(1, 1), table.getTableCell(1, 0));
        assertFalse(b);
        //control baseMovement is true
        b = table.controlBaseMovement(table.getTableCell(1, 1), table.getTableCell(0, 2));
        assertTrue(b);
        b = table.controlBaseMovement(table.getTableCell(1, 1), table.getTableCell(1, 2));
        assertTrue(b);
        b = table.controlBaseMovement(table.getTableCell(1, 1), table.getTableCell(2, 2));
        assertTrue(b);
    }

    @Test
    public void testBaseBuilding() {
        Pawn p0 = new Pawn();
        Pawn q0 = new Pawn();
        table.setACell(0, 0, 1, false, false, p0);
        table.setACell(0, 1, 3, false, true, null);
        table.setACell(1, 0, 3, true, false, null);
        table.setACell(1, 1, 2, false, false, q0);
        Cell s = table.getTableCell(p0.getRow(), p0.getColumn());
        assertFalse(table.controlBaseBuilding(s, table.getTableCell(3, 1)));
        assertFalse(table.controlBaseBuilding(s, table.getTableCell(0, 1)));
        assertFalse(table.controlBaseBuilding(s, table.getTableCell(1, 1)));
        table.setACell(1, 1, 2, true, false, null);
        assertTrue(table.controlBaseBuilding(s, table.getTableCell(1, 1)));
    }

    /**
     * method that tests the perimetral cells of the table
     */
    @Test
    public void testPerimetralCells() {
        ArrayList<Cell> pCells;
        pCells = table.tablePerimetralCells(table);
        assertEquals(16, pCells.size());
        assertTrue(pCells.contains(table.getTableCell(0, 0)));
        assertTrue(pCells.contains(table.getTableCell(1, 0)));
        assertTrue(pCells.contains(table.getTableCell(3, 0)));
        assertTrue(pCells.contains(table.getTableCell(4, 3)));
        assertTrue(!pCells.contains(table.getTableCell(1, 1)));
        assertTrue(!pCells.contains(table.getTableCell(2, 3)));
        assertTrue(!pCells.contains(table.getTableCell(3, 1)));
    }

}
