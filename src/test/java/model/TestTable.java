package model;

import org.junit.Before;
import org.junit.Test;
import santorini.model.Cell;
import santorini.model.Table;

import java.io.Serializable;

import static org.junit.Assert.*;

public class TestTable implements Serializable {
    private Table table;

    @Before
    public void before() {
        table = new Table();
    }

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

    @Test
    public void testNearCell() {
        Cell nearCell;
        nearCell = new Cell();
        int i, j, x, y;
        for (i = x - 1; i < x + 2; i++) {
            for (j = y - 1; j < y + 2; j++) {
                if ((i != x) && (j != y) || (i >= 0) || (i <= 4) || (j >= 0) || (j <= 4)) {
                    nearCell = table.getTableCell(i, j);
                    assertArrayEquals(nearCell.getX());
                    assertArrayEquals(nearCell.getY());
                } else {

                }
            }
        }
    }

}
