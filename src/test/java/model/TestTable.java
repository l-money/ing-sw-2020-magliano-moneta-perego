/**
 * @author Magliano
 */
package model;

import org.junit.Before;
import org.junit.Test;
import santorini.model.Cell;
import santorini.model.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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
    public void testSetCellsInTable() {
        Cell position;
        position = new Cell();
        int i, j;
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                position = table.getTableCell(i, j);
                assertEquals(position.getX(), i);
                assertEquals(position.getY(), j);
                assertEquals(position.getLevel(), 0);
                assertTrue(position.isFree());
                assertFalse(position.isComplete());
            }
        }
    }

    /**
     * methods that test the near cells from the cell where my pawn stay.
     * In this case i'm testing the cell (0,0) and the nearest cell are c1(0,1), c2(1,0) c3(1,1)
     */
    @Test
    public void testNearCell1() {
        List<Cell> nearCell = new ArrayList<Cell>();
        Cell myCell;
        myCell = table.getTableCell(0, 0);
        nearCell = table.searchCells(myCell);
        int lenght = nearCell.size();
        assertTrue(lenght == 3);
        assertTrue(nearCell.get(0) == table.getTableCell(0, 1));
        assertTrue(nearCell.get(1) == table.getTableCell(1, 0));
        assertTrue(nearCell.get(2) == table.getTableCell(1, 1));
    }

    //11 lunghezza 8

    /**
     * methods that test the near cells from the cell where my pawn stay.
     * In this case i'm testing the cell (1,1) and the nearest cell are c1(0,0), c2(0,1), c3(0,2)
     * c4(1,0), c5(1,2), c6(2,0), c7(2,1), c8(2,2)
     */
    @Test
    public void testNearCell2() {
        List<Cell> nearCell = new ArrayList<Cell>();
        Cell myCell;
        myCell = table.getTableCell(1, 1);
        nearCell = table.searchCells(myCell);
        int lenght = nearCell.size();
        assertTrue(lenght == 8);
        assertTrue(nearCell.get(0) == table.getTableCell(0, 0));
        assertTrue(nearCell.get(1) == table.getTableCell(0, 1));
        assertTrue(nearCell.get(2) == table.getTableCell(0, 2));
        assertTrue(nearCell.get(3) == table.getTableCell(1, 0));
        assertTrue(nearCell.get(4) == table.getTableCell(1, 2));
        assertTrue(nearCell.get(5) == table.getTableCell(2, 0));
        assertTrue(nearCell.get(6) == table.getTableCell(2, 1));
        assertTrue(nearCell.get(7) == table.getTableCell(2, 2));
    }

    //34

    /**
     * methods that test the near cells from the cell where my pawn stay.
     * In this case i'm testing the cell (3,4) and and the nearest cell are c1(2,3), c2(2,4), c3(3,3)
     * c4(4,3), c5(4,4)
     */
    @Test
    public void testNearCell3() {
        List<Cell> nearCell = new ArrayList<Cell>();
        Cell myCell;
        myCell = table.getTableCell(3, 4);
        nearCell = table.searchCells(myCell);
        int lenght = nearCell.size();
        assertTrue(lenght == 5);
        assertTrue(nearCell.get(0) == table.getTableCell(2, 3));
        assertTrue(nearCell.get(1) == table.getTableCell(2, 4));
        assertTrue(nearCell.get(2) == table.getTableCell(3, 3));
        assertTrue(nearCell.get(3) == table.getTableCell(4, 3));
        assertTrue(nearCell.get(4) == table.getTableCell(4, 4));
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
}
