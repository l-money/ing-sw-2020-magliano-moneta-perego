import org.junit.Before;
import org.junit.Test;
import santorini.NetworkHandlerServer;
import santorini.Turno;
import santorini.model.*;
import santorini.model.godCards.Athena;
import santorini.model.godCards.Pdor;
import santorini.model.godCards.Prometheus;

import java.util.ArrayList;

import static junit.framework.TestCase.*;


public class TestTurno {
    private Turno turn;
    private Table table;
    private Gamer gamer;
    private God Athena, Prometheus, Pdor;
    private NetworkHandlerServer gamerHandler;
    private ArrayList<God> godCards;
    private boolean effect;

    @Before
    public void before() {
        table = new Table();
        gamer = new Gamer(null, "Hercules", 0, null, null);
        //gamerHandler = new NetworkHandlerServer();
        Athena = new Athena();
        Prometheus = new Prometheus();
        Pdor = new Pdor();
        godCards = new ArrayList<>();
        godCards.add(Pdor);
        godCards.add(Athena);
        godCards.add(Prometheus);

        turn = new Turno(godCards, gamer, table, gamerHandler);
    }

    /**
     * method that tests the start parameter of the base move (movement and building)
     */
    @Test
    public void testGetStandardParameter() {
        Mossa move00 = new Mossa(Mossa.Action.MOVE, 0, 2, 2);
        assertTrue(turn.controlStandardParameter(move00));
        Mossa move01 = new Mossa(Mossa.Action.BUILD, 0, 2, 2);
        assertTrue(turn.controlStandardParameter(move01));
        Mossa move02 = new Mossa(Mossa.Action.MOVE, -1, 2, 2);
        Mossa move03 = new Mossa(Mossa.Action.BUILD, 0, 3, 7);
        Mossa move04 = new Mossa(Mossa.Action.BUILD, 3, -4, 9);
        assertTrue(!turn.controlStandardParameter(move02));
        assertTrue(!turn.controlStandardParameter(move03));
        assertTrue(!turn.controlStandardParameter(move04));
    }

    /**
     * method that tests the first control of the move selected by the client
     */
    //TODO simulate a client typing of the move
    @Test
    public void testFirstControlOfMovement() {
    }

    /**
     * method that tests the base movement of a pawn
     */
    @Test
    public void testBaseMovement() {
        Pawn q = new Pawn();
        turn.getTable().setACell(1, 1, 0, false, false, turn.getGamer().getPawn(0));
        turn.getTable().setACell(1, 2, 0, false, false, turn.getGamer().getPawn(1));
        //turn.setIdStartPawn(turn.getGamer().getPawn(0).getIdPawn());
        //cell[rowX,columnY,level]
        //move up from cell[1,1,0] to cell[2,2,1]
        turn.getTable().setACell(2, 2, 1, true, false, null);
        Mossa move0 = new Mossa(Mossa.Action.MOVE, 0, 2, 2);
        turn.baseMovement(move0);
        assertTrue(turn.isValidationMove());
        assertEquals(turn.getGamer().getPawn(0), turn.getTable().getTableCell(2, 2).getPawn());
        assertNull(turn.getTable().getTableCell(1, 1).getPawn());
        //move up from cell[2,2,1] to cell[1,1,3] : impossible
        turn.getTable().setACell(1, 1, 3, true, false, null);
        Mossa move1 = new Mossa(Mossa.Action.MOVE, 0, 1, 1);
        turn.baseMovement(move1);
        assertTrue(!turn.isValidationMove());
        assertEquals(turn.getGamer().getPawn(0), turn.getTable().getTableCell(2, 2).getPawn());
        assertNull(turn.getTable().getTableCell(1, 1).getPawn());
        //move to the same level from cell[2,2,1] to cell[2,3,1]
        turn.getTable().setACell(2, 3, 1, true, false, null);
        Mossa move2 = new Mossa(Mossa.Action.MOVE, 0, 2, 3);
        turn.baseMovement(move2);
        assertTrue(turn.isValidationMove());
        //move to a cell with dome from cell[2,3,1] to cell[1,3,dome]
        turn.getTable().setACell(1, 3, 3, false, true, null);
        Mossa move3 = new Mossa(Mossa.Action.MOVE, 0, 2, 3);
        turn.baseMovement(move3);
        assertTrue(!turn.isValidationMove());
        //move down from cell[2,2,3] to cell[3,3,0]
        turn.getTable().setACell(2, 2, 3, false, false, turn.getGamer().getPawn(0));
        turn.getTable().setACell(3, 3, 0, true, false, null);
        Mossa move4 = new Mossa(Mossa.Action.MOVE, 0, 3, 3);
        turn.baseMovement(move4);
        assertTrue(turn.isValidationMove());
        //the gamer changes the pawn
        Mossa move5 = new Mossa(Mossa.Action.MOVE, 1, 0, 3);
        turn.baseMovement(move5);
        assertTrue(turn.isValidationMove());
        //the gamer has steps = 0
        turn.getGamer().setSteps(0);
        turn.getTable().setACell(2, 2, 1, false, false, turn.getGamer().getPawn(0));
        turn.getTable().setACell(3, 3, 2, true, false, null);
        Mossa move6 = new Mossa(Mossa.Action.MOVE, 0, 3, 3);
        turn.baseMovement(move6);
        assertTrue(turn.isValidationMove());
        turn.getGamer().setSteps(1);
        //there is another Pawn from cell[2,2,1] to cell[3,3,2]: impossible
        turn.getTable().setACell(3, 3, 2, false, false, q);
        Mossa move7 = new Mossa(Mossa.Action.MOVE, 0, 3, 3);
        turn.baseMovement(move7);
        assertTrue(!turn.isValidationMove());
        assertEquals(turn.getGamer().getPawn(0), turn.getTable().getTableCell(2, 2).getPawn());
        assertEquals(q, turn.getTable().getTableCell(3, 3).getPawn());
    }


    /**
     * method that tests the base building
     */
    @Test
    public void testBaseBuilding() {
        //turn.setIdStartPawn(0);
        turn.getTable().setACell(1, 1, 0, false, false, turn.getGamer().getPawn(0));
        turn.getTable().setACell(2, 2, 1, false, false, turn.getGamer().getPawn(1));
        //pawn0 can build:
        //level 1 in cell[1,2]
        turn.getTable().setACell(1, 2, 0, true, false, null);
        Mossa m0 = new Mossa(Mossa.Action.BUILD, 0, 1, 2);
        turn.baseBuilding(m0);
        assertTrue(turn.isValidationBuild());
        assertEquals(1, turn.getTable().getTableCell(1, 2).getLevel());
        //level 2 in cell[0,2]
        turn.getTable().setACell(0, 2, 1, true, false, null);
        Mossa m1 = new Mossa(Mossa.Action.BUILD, 0, 0, 2);
        turn.baseBuilding(m1);
        assertTrue(turn.isValidationBuild());
        assertEquals(2, turn.getTable().getTableCell(0, 2).getLevel());
        //level 3 in cell[0,1]
        turn.getTable().setACell(0, 1, 2, true, false, null);
        Mossa m2 = new Mossa(Mossa.Action.BUILD, 0, 0, 1);
        turn.baseBuilding(m2);
        assertTrue(turn.isValidationBuild());
        assertEquals(3, turn.getTable().getTableCell(0, 1).getLevel());
        //dome in cell[0,0]
        turn.getTable().setACell(0, 0, 3, true, false, null);
        Mossa m3 = new Mossa(Mossa.Action.BUILD, 0, 0, 0);
        turn.baseBuilding(m3);
        assertTrue(turn.isValidationBuild());
        assertEquals(3, turn.getTable().getTableCell(0, 0).getLevel());
        assertTrue(turn.getTable().getTableCell(0, 0).isComplete());
        //pawn0 can not build in cell[1,0] because it is complete
        turn.getTable().setACell(1, 0, 2, false, true, null);
        Mossa m4 = new Mossa(Mossa.Action.BUILD, 0, 1, 0);
        turn.baseBuilding(m4);
        assertTrue(!turn.isValidationBuild());
        assertEquals(2, turn.getTable().getTableCell(1, 0).getLevel());
        assertTrue(turn.getTable().getTableCell(1, 0).isComplete());
        //pawn0 can not build in cell[2,2] because there is another pawn
        Mossa m5 = new Mossa(Mossa.Action.BUILD, 0, 2, 2);
        turn.baseBuilding(m5);
        assertTrue(!turn.isValidationBuild());
        assertEquals(turn.getGamer().getPawn(1), turn.getTable().getTableCell(2, 2).getPawn());
    }

}