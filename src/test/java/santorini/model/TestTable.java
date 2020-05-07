package santorini.model;

import org.junit.Before;
import org.junit.Test;
import santorini.View;

import java.io.Serializable;
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
        assertEquals(4, table.getBag().getCounterBrick().length);
        assertEquals(22, table.getBag().getCounterBrick()[0]);
        assertEquals(18, table.getBag().getCounterBrick()[1]);
        assertEquals(14, table.getBag().getCounterBrick()[2]);
        assertEquals(18, table.getBag().getCounterBrick()[3]);
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
        assertEquals(2, table.getTableCell(2, 2).getX());
        assertEquals(2, table.getTableCell(2, 2).getY());
        assertEquals(0, table.getTableCell(2, 2).getLevel());
        assertTrue(table.getTableCell(2, 2).isFree());
        assertFalse(table.getTableCell(2, 2).isComplete());
        assertNull(table.getTableCell(2, 2).getPawn());
        table.setACell(2, 2, 1, false, false, p);
        assertEquals(2, table.getTableCell(2, 2).getX());
        assertEquals(2, table.getTableCell(2, 2).getY());
        assertEquals(1, table.getTableCell(2, 2).getLevel());
        assertFalse(table.getTableCell(2, 2).isFree());
        assertFalse(table.getTableCell(2, 2).isComplete());
        assertNotNull(table.getTableCell(2, 2).getPawn());
        assertEquals(1, table.getTableCell(2, 2).getPawn().getPresentLevel());
        assertEquals(2, table.getTableCell(2, 2).getPawn().getRow());
        assertEquals(2, table.getTableCell(2, 2).getPawn().getColumn());
        //test the hierarchy of level>complete>free>pawn
        //case 1: level
        table.setACell(2, 2, 4, true, true, p);
        assertEquals(3, table.getTableCell(2, 2).getLevel());
        assertFalse(table.getTableCell(2, 2).isFree());
        assertTrue(table.getTableCell(2, 2).isComplete());
        assertNull(table.getTableCell(2, 2).getPawn());
        //case 2 complete
        table.setACell(2, 2, 2, true, true, p);
        assertEquals(2, table.getTableCell(2, 2).getLevel());
        assertFalse(table.getTableCell(2, 2).isFree());
        assertTrue(table.getTableCell(2, 2).isComplete());
        assertNull(table.getTableCell(2, 2).getPawn());
        //case 3: free
        table.setACell(2, 2, 3, true, false, p);
        assertEquals(3, table.getTableCell(2, 2).getLevel());
        assertTrue(table.getTableCell(2, 2).isFree());
        assertFalse(table.getTableCell(2, 2).isComplete());
        assertNull(table.getTableCell(2, 2).getPawn());
        //case 4 pawn
        table.setACell(2, 2, 1, false, false, p);
        assertEquals(1, table.getTableCell(2, 2).getLevel());
        assertFalse(table.getTableCell(2, 2).isFree());
        assertFalse(table.getTableCell(2, 2).isComplete());
        assertEquals(p, table.getTableCell(2, 2).getPawn());
        //case 5 pawn null
        table.setACell(2, 2, 0, true, false, null);
        assertEquals(0, table.getTableCell(2, 2).getLevel());
        assertTrue(table.getTableCell(2, 2).isFree());
        assertFalse(table.getTableCell(2, 2).isComplete());
        assertNull(table.getTableCell(2, 2).getPawn());
    }


    @Test
    public void testICanMove() {
        View v = new View(null, null);
        Pawn p0 = new Pawn();
        Pawn q1 = new Pawn();
        p0.setIdPawn(0);
        p0.setIdGamer(0);
        q1.setIdPawn(1);
        q1.setIdGamer(1);
        table.setACell(0, 0, 0, false, false, q1);
        table.setACell(0, 1, 3, false, true, null);
        table.setACell(1, 0, 2, true, false, null);
        table.setACell(1, 1, 1, false, false, p0);
        //v.printTable(table);
        boolean b = table.iCanMove(table.getTableCell(p0.getRow(), p0.getColumn()));
        assertTrue(b);
        b = table.iCanMove(table.getTableCell(q1.getRow(), q1.getColumn()));
        assertTrue(!b);
        table.setACell(1, 1, 1, true, false, p0);
        //v.printTable(table);
        b = table.iCanMove(table.getTableCell(q1.getRow(), q1.getColumn()));
        assertTrue(b);
    }

    @Test
    public void testICanBuild() {
        //View v = new View(null,null);
        Pawn p0 = new Pawn();
        Pawn q1 = new Pawn();
        p0.setIdPawn(0);
        p0.setIdGamer(0);
        q1.setIdPawn(1);
        q1.setIdGamer(1);
        table.setACell(0, 0, 0, false, false, q1);
        table.setACell(0, 1, 3, false, true, null);
        table.setACell(1, 0, 2, true, true, p0);
        table.setACell(1, 1, 1, false, false, p0);
        //v.printTable(table);
        boolean b = table.iCanBuild(table.getTableCell(p0.getRow(), p0.getColumn()));
        assertTrue(b);
        b = table.iCanBuild(table.getTableCell(q1.getRow(), q1.getColumn()));
        assertTrue(!b);
        table.setACell(1, 1, 1, true, false, p0);
        //v.printTable(table);
        b = table.iCanBuild(table.getTableCell(q1.getRow(), q1.getColumn()));
        assertTrue(b);
    }

    /**
     * method that tests controlBaseMovement
     */
    @Test
    public void testControlBaseMovement() {
        View v = new View(null, null);
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
        v.printTable(table);
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

    /**
     * method tests controlBaseBuild and simple build
     */
    @Test
    public void testControlBaseBuilding() {
        Pawn p = new Pawn();
        Pawn q = new Pawn();
        Cell start;
        Cell end1, end2, end3, end4, end5;
        table.setACell(0, 2, 1, false, false, p);
        start = table.getTableCell(0, 2);
        //control if is possible to build
        table.setACell(0, 1, 2, true, false, null);//level 2: I can build level 3
        table.setACell(0, 3, 3, true, false, null);//level 3: I can build a dome
        table.setACell(1, 1, 0, true, false, null);//level 0: I can build level 1
        table.setACell(1, 2, 2, false, false, q);//is not free: impossible build
        table.setACell(1, 3, 3, false, true, null);//is complete: impossible build
        end1 = table.getTableCell(0, 1);
        end2 = table.getTableCell(0, 3);
        end3 = table.getTableCell(1, 1);
        end4 = table.getTableCell(1, 2);
        end5 = table.getTableCell(1, 3);
        assertTrue(table.controlBaseBuilding(start, end1));
        assertTrue(table.controlBaseBuilding(start, end2));
        assertTrue(table.controlBaseBuilding(start, end3));
        assertFalse(table.controlBaseBuilding(start, end4));
        assertFalse(table.controlBaseBuilding(start, end5));
        //build and control
        table.build(end1);
        table.build(end2);
        table.build(end3);
        assertEquals(3, end1.getLevel());
        assertEquals(13, table.getBag().getCounterBrick()[2]);
        assertEquals(3, end2.getLevel());
        assertEquals(17, table.getBag().getCounterBrick()[3]);
        assertTrue(end2.isComplete());
        assertEquals(1, end3.getLevel());
        assertEquals(21, table.getBag().getCounterBrick()[0]);
        //build another brick on cell [1;1]
        table.build(end3);
        assertEquals(2, end3.getLevel());
        assertEquals(17, table.getBag().getCounterBrick()[1]);
        //I empty the brick of level 1
        for (int i = 0; i < 21; i++) {
            table.getBag().extractionBrick(1);
        }
        assertEquals(0, table.getBag().getCounterBrick()[0]);
        end3.setLevel(0);
        assertFalse(table.controlBaseBuilding(start, end3));
        assertFalse(table.build(end3));
    }

}
