package santorini.model;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotSame;

public class TestMossa {
    private Mossa move;
    private Mossa build;
    private Mossa nullEffectMove;
    private Mossa nullEffectBuild;

    @Before
    public void before() {
        move = new Mossa(Mossa.Action.MOVE, 0, 2, 2);
        build = new Mossa(Mossa.Action.BUILD, 1, 1, 2);
        nullEffectMove = new Mossa(Mossa.Action.MOVE, -1, -1, -1);
        nullEffectBuild = new Mossa(Mossa.Action.BUILD, -1, -1, -1);
    }

    /**
     * method that tests the move and the build
     */

    @Test
    public void testMoveAndBuild() {
        assertEquals(Mossa.Action.MOVE, move.getAction());
        assertEquals(0, move.getIdPawn());
        assertEquals(2, move.getTargetX());
        assertEquals(2, move.getTargetY());
        assertEquals(Mossa.Action.BUILD, build.getAction());
        assertEquals(1, build.getIdPawn());
        assertEquals(1, build.getTargetX());
        assertEquals(2, build.getTargetY());
        move.setAction(Mossa.Action.BUILD);
        move.setIdPawn(1);
        move.setTargetX(3);
        move.setTargetY(3);
        assertEquals(Mossa.Action.BUILD, move.getAction());
        assertEquals(1, move.getIdPawn());
        assertEquals(3, move.getTargetX());
        assertEquals(3, move.getTargetY());
    }

    /**
     * method that tests the nullEffectMove and the NullEffectBuild
     */

    @Test
    public void testNullEffectMoveAndBuild() {
        assertNotSame(nullEffectMove.getAction(), nullEffectBuild.getAction());
        assertEquals(nullEffectMove.getIdPawn(), nullEffectBuild.getIdPawn());
        assertEquals(nullEffectMove.getTargetX(), nullEffectBuild.getTargetX());
        assertEquals(nullEffectMove.getTargetY(), nullEffectBuild.getTargetY());
        assertEquals(-1, nullEffectBuild.getIdPawn());
        assertEquals(-1, nullEffectBuild.getIdPawn());
        assertEquals(-1, nullEffectBuild.getIdPawn());
    }

    /**
     * method that tests setMyMossa
     */
    @Test
    public void testSetMyMossa() {
        Mossa k;
        k = move.setMyMossa(Mossa.Action.MOVE, 0, 1, 2);
        assertEquals(Mossa.Action.MOVE, k.getAction());
        assertEquals(0, k.getIdPawn());
        assertEquals(1, k.getTargetX());
        assertEquals(2, k.getTargetY());
        k = move.setMyMossa(Mossa.Action.BUILD, 1, 2, 3);
        assertEquals(Mossa.Action.BUILD, k.getAction());
        assertEquals(1, k.getIdPawn());
        assertEquals(2, k.getTargetX());
        assertEquals(3, k.getTargetY());
    }

    //TODO review this method
/**
     @Test public void testInputMossa() throws IOException {
     Mossa m;
     m = move.InputMossa();
     System.out.println(m.getIdPawn());
     }
     */
}
