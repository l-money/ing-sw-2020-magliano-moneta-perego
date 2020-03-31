package model;

import org.junit.Before;
import org.junit.Test;
import santorini.model.Pawn;
import santorini.model.utils.Color;

import static org.junit.Assert.assertTrue;

public class TestPawn {
    private Pawn pawn;

    @Before
    public void before()
    {
        pawn = new Pawn(1,1,0,0,1,0,Color.BLUE);
    }

    @Test
    public void testGetIdPawn(){
        int x = pawn.getIdPawn();
        assertTrue(x==1);
        pawn.setIdPawn(0);
        assertTrue(pawn.getIdPawn()==0);
    }

    @Test
    public void testGetIdGamer(){
        int x = pawn.getIdGamer();
        assertTrue(x==1);
        pawn.setIdGamer(0);
        assertTrue(pawn.getIdGamer()==0);
    }

    @Test
    public void testRowPawn(){
        int x = pawn.getRowPawn();
        assertTrue(x==0);
        pawn.setRowPawn(1);
        assertTrue(pawn.getRowPawn()==1);
    }

    @Test
    public void testColumnPawn(){
        int x = pawn.getColumnPawn();
        assertTrue(x==0);
        pawn.setColumnPawn(1);
        assertTrue(pawn.getColumnPawn()==1);
    }

    @Test
    public void testLevelsPawn(){
        int x = pawn.getPastLevel();
        int y = pawn.getPresentLevel();
        assertTrue(x==0);
        assertTrue(y==1);
        pawn.setPastLevel(2);
        pawn.setPresentLevel(2);
        assertTrue(pawn.getPastLevel()==2);
        assertTrue(pawn.getPresentLevel()==2);
    }

    @Test
    public void testColorPawn(){
        Color c = pawn.getColorPawn();
        assertTrue(c == Color.BLUE);
        pawn.setColorPawn(Color.GREEN);
        assertTrue(pawn.getColorPawn()==Color.GREEN);
    }

    @Test
    public void testPositionPawn(){
        int[] p;
        p = pawn.positionPawn();
            int l = p.length;
            assertTrue(l==2);
            assertTrue(p[0]==pawn.getRowPawn());
            assertTrue(p[1]==pawn.getColumnPawn());
    }
}
