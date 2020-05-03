import org.junit.Before;
import org.junit.Test;
import santorini.NetworkHandlerServer;
import santorini.Turno;
import santorini.View;
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
        turn.setIdStartPawn(turn.getGamer().getPawn(0).getIdPawn());
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
     * method that tests when a pawn is lock or unlock and if the gamer lose the game because he can not move
     */
    @Test
    public void testBaseMovementLockUnlock() {
        Pawn q = new Pawn();
        turn.setIdStartPawn(0);//gamer want to move  pawn0
        turn.getTable().setACell(0, 0, 0, false, false, turn.getGamer().getPawn(0));
        turn.getTable().setACell(4, 4, 0, false, false, turn.getGamer().getPawn(1));
        //pawn0 can not move, pawn1 can move
        turn.getTable().setACell(0, 1, 1, false, false, q);//there is another pawn
        turn.getTable().setACell(1, 0, 2, true, false, null);//level 2
        turn.getTable().setACell(1, 1, 2, false, true, null);//level 2 with dome for Atlas effect
        turn.getTable().setACell(3, 3, 1, false, false, null);//level 1 free
        turn.getTable().setACell(3, 4, 2, false, false, null);//level 2
        turn.getTable().setACell(4, 3, 3, false, true, null);//level with dome
        Mossa move0 = new Mossa(Mossa.Action.MOVE, 0, 1, 1);
        turn.baseMovement(move0);
        assertTrue(!turn.isValidationMove());//I can not move with pawn0
        assertTrue(!turn.getGamer().getPawn(0).getICanPlay());//pawn0 is locked
        assertTrue(turn.getGamer().getPawn(1).getICanPlay());//pawn1 is not locked
        assertTrue(!turn.getGamer().getLoser());//gamer can move with pawn1
        //pawn0 can not move and pawn1 can not move
        turn.setIdStartPawn(1);//gamer want to move pawn1
        turn.getTable().setACell(3, 3, 1, false, true, null);//level 1 with dome for Atlas effect
        Mossa move1 = new Mossa(Mossa.Action.MOVE, 1, 3, 4);
        turn.baseMovement(move1);
        assertTrue(turn.isValidationMove());//validationMove is true for exit from do-while
        assertTrue(!turn.getGamer().getPawn(0).getICanPlay());//pawn0 is locked
        assertTrue(!turn.getGamer().getPawn(1).getICanPlay());//pawn1 is locked
        assertTrue(turn.getGamer().getLoser());//gamer can not move
        //pawn0 unlocks
        turn.getGamer().setLoser(false);
        turn.setIdStartPawn(0);//gamer want to move pawn0
        turn.getTable().setACell(0, 1, 1, true, false, null);//cell is free
        Mossa move3 = new Mossa(Mossa.Action.MOVE, 0, 0, 1);
        turn.baseMovement(move3);
        assertTrue(turn.isValidationMove());
        assertTrue(turn.getGamer().getPawn(0).getICanPlay());//pawn0 is not locked
        assertTrue(!turn.getGamer().getPawn(1).getICanPlay());//pawn1 is locked
        assertTrue(!turn.getGamer().getLoser());//gamer can move
    }

    /**
     * method that tests the base building
     */
    @Test
    public void testBaseBuilding() {
        turn.setIdStartPawn(0);
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

    /**
     * method that tests when a pawn is lock or unlock and if the gamer lose the game because he can not build
     */
    @Test
    public void testBaseBuildingLockUnlock() {
        turn.setIdStartPawn(1);
        Pawn q = new Pawn();
        turn.getTable().setACell(4, 4, 0, false, false, turn.getGamer().getPawn(0));
        turn.getTable().setACell(0, 0, 1, false, false, turn.getGamer().getPawn(1));
        //pawn1 can not build but pawn0 can, gamer doesn't lose
        turn.getTable().setACell(0, 1, 3, false, true, null);
        turn.getTable().setACell(1, 0, 3, false, true, null);
        turn.getTable().setACell(1, 1, 1, false, false, q);
        Mossa m0 = new Mossa(Mossa.Action.BUILD, 1, 1, 1);
        turn.baseBuilding(m0);
        assertFalse(turn.isValidationBuild());
        assertTrue(!turn.getGamer().getPawn(1).getICanPlay());
        assertTrue(turn.getGamer().getPawn(0).getICanPlay());
        assertTrue(!turn.getGamer().getLoser());
        //pawn1 can not build, pawn0 can not build, gamer loses
        turn.setIdStartPawn(0);
        turn.getTable().setACell(4, 4, 0, false, false, turn.getGamer().getPawn(0));
        turn.getTable().setACell(3, 4, 3, false, true, null);
        turn.getTable().setACell(4, 3, 3, false, true, null);
        turn.getTable().setACell(3, 3, 3, false, true, null);
        Mossa m1 = new Mossa(Mossa.Action.BUILD, 0, 3, 3);
        turn.baseBuilding(m1);
        assertTrue(turn.isValidationBuild());//validationBuild is true for exit from do-while
        assertTrue(!turn.getGamer().getPawn(1).getICanPlay());
        assertTrue(!turn.getGamer().getPawn(0).getICanPlay());
        assertTrue(turn.getGamer().getLoser());
        //pawn1 unlocks
        turn.getGamer().setLoser(false);
        turn.setIdStartPawn(1);//gamer want to move pawn1
        turn.getTable().setACell(1, 1, 1, true, false, null);//cell is free
        Mossa move3 = new Mossa(Mossa.Action.BUILD, 1, 1, 1);
        turn.baseBuilding(move3);
        assertTrue(turn.isValidationBuild());
        assertTrue(turn.getGamer().getPawn(1).getICanPlay());//pawn1 is not locked
        assertTrue(!turn.getGamer().getPawn(0).getICanPlay());//pawn0 is locked
        assertTrue(!turn.getGamer().getLoser());//gamer can move
    }

    /**
     * method that tests when a pawn can not build because there are not bricks into the bag
     */
    @Test
    public void testBagEmpty() {
        turn.setIdStartPawn(0);
        //set empty the counterBrick of level 1
        while (turn.getTable().getBag().controlExistBrick(1)) {
            turn.getTable().getBag().extractionBrick(1);
        }
        //pawn0 wants to build in cell[1,1] but it can not because there are not bricks of level 1 into the bag
        turn.getTable().setACell(0, 0, 0, false, false, turn.getGamer().getPawn(0));
        Mossa m0 = new Mossa(Mossa.Action.BUILD, 0, 1, 1);
        turn.baseBuilding(m0);
        assertTrue(!turn.isValidationBuild());
        assertEquals(0, turn.getTable().getTableCell(1, 1).getLevel());
        assertEquals(0, turn.getTable().getBag().getCounterBrick()[0]);
        //set empty the counterBrick of the domes
        while (turn.getTable().getBag().controlExistBrick(4)) {
            turn.getTable().getBag().extractionBrick(4);
        }
        //pawn0 wants to build in cell[0,1] but it can not because there are not domes into the bag
        turn.getTable().setACell(0, 1, 3, true, false, null);
        Mossa m1 = new Mossa(Mossa.Action.BUILD, 0, 0, 1);
        turn.baseBuilding(m1);
        assertTrue(!turn.isValidationBuild());
        assertEquals(3, turn.getTable().getTableCell(0, 1).getLevel());
        assertEquals(0, turn.getTable().getBag().getCounterBrick()[3]);
    }

    /**
     * method that tests the base win of the gamer
     */
    @Test
    public void testControlBaseWin() {
        turn.setIdStartPawn(0);
        turn.getGamer().setAPawn(0, 1, 1, 1, 2);
        turn.getTable().setACell(1, 1, 2, false, false, turn.getGamer().getPawn(0));
        turn.getTable().setACell(2, 2, 3, true, false, null);
        turn.controlWin();
        assertTrue(!turn.getGamer().isWinner());
        assertEquals(1, turn.getGamer().getBuilds());
        Mossa m0 = new Mossa(Mossa.Action.MOVE, 0, 2, 2);
        turn.baseMovement(m0);
        turn.controlWin();
        assertTrue(turn.getGamer().isWinner());
        assertEquals(2, turn.getGamer().getPawn(0).getPastLevel());
        assertEquals(3, turn.getGamer().getPawn(0).getPresentLevel());
        assertEquals(0, turn.getGamer().getBuilds());
    }

    /**
     * method that tests the null move for the god card
     */
    @Test
    public void testNullEffectForGodCards() {
        Mossa nullMove = new Mossa(Mossa.Action.MOVE, -1, -1, -1);
        Mossa nullBuild = new Mossa(Mossa.Action.BUILD, -1, -1, -1);
        Mossa nullOne = new Mossa(Mossa.Action.MOVE, -1, 3, 7);
        Mossa nullTwo = new Mossa(Mossa.Action.MOVE, 0, 8, 2);
        Mossa nullThree = new Mossa(Mossa.Action.MOVE, -2, 9, 7);
        assertTrue(turn.nullEffectForGodCards(nullMove));
        assertTrue(turn.nullEffectForGodCards(nullBuild));
        assertTrue(!turn.nullEffectForGodCards(nullOne));
        assertTrue(!turn.nullEffectForGodCards(nullTwo));
        assertTrue(!turn.nullEffectForGodCards(nullThree));
    }

    /**
     * method that tests getMyStep
     */
    @Test
    public void testGetMyStep() {
        turn.setIdStartPawn(1);
        turn.getGamer().setAPawn(1, 1, 1, 0, 1);
        turn.getTable().setACell(1, 1, 1, false, false, turn.getGamer().getPawn(1));
        turn.getTable().setACell(2, 2, 2, true, false, null);
        turn.getMyStep(turn.getTable().getTableCell(1, 1), turn.getTable().getTableCell(2, 2), turn.getGamer().getPawn(1));
        assertNull(turn.getTable().getTableCell(1, 1).getPawn());
        assertTrue(turn.getTable().getTableCell(1, 1).isFree());
        assertEquals(2, turn.getGamer().getPawn(1).getRow());
        assertEquals(2, turn.getGamer().getPawn(1).getColumn());
        assertEquals(1, turn.getGamer().getPawn(1).getPastLevel());
        assertEquals(2, turn.getGamer().getPawn(1).getPresentLevel());
        assertNotNull(turn.getTable().getTableCell(2, 2).getPawn());
        assertTrue(!turn.getTable().getTableCell(2, 2).isFree());
    }

    /**
     * method that tests the god effect of Artemis
     */
    @Test
    public void testGodCardEffectArtemis() {
        turn.setIdStartPawn(0);
        //pawn0 moves from cell[1,1] to cell[2,2] and does a secondo move to cell[3,3]
        turn.getGamer().setAPawn(0, 0, 0, 0, 0);
        turn.getTable().setACell(1, 1, 1, false, false, turn.getGamer().getPawn(0));
        turn.getTable().setACell(2, 2, 1, true, false, null);
        turn.getTable().setACell(3, 3, 1, true, false, null);
        Cell myPosition = turn.getTable().getTableCell(turn.getGamer().getPawn(0).getRow(), turn.getGamer().getPawn(0).getColumn());
        Mossa m0 = new Mossa(Mossa.Action.MOVE, 0, 2, 2);
        turn.baseMovement(m0);
        assertTrue(turn.isValidationMove());
        assertEquals(2, turn.getGamer().getPawn(0).getRow());
        assertEquals(2, turn.getGamer().getPawn(0).getColumn());
        Mossa m1 = new Mossa(Mossa.Action.MOVE, 0, 3, 3);
        effect = turn.godCardEffect(m1, effect, 0, myPosition);
        assertTrue(effect);
        //pawn0 moves from cell[1,1] to cell[2,2] and it wants to go back to cell[1,1] but it can not
        turn.getTable().setACell(1, 1, 1, false, false, turn.getGamer().getPawn(0));
        turn.getTable().setACell(2, 2, 1, true, false, null);
        turn.getTable().setACell(3, 3, 1, true, false, null);
        myPosition = turn.getTable().getTableCell(turn.getGamer().getPawn(0).getRow(), turn.getGamer().getPawn(0).getColumn());
        Mossa m2 = new Mossa(Mossa.Action.MOVE, 0, 2, 2);
        turn.baseMovement(m2);
        assertTrue(turn.isValidationMove());
        Mossa m3 = new Mossa(Mossa.Action.MOVE, 0, 1, 1);
        effect = turn.godCardEffect(m3, effect, 0, myPosition);
        assertTrue(!effect);
        //pawn0 moves from cell[2,2] to cell[2,3] and the gamer doesn't want do a second move
        myPosition = turn.getTable().getTableCell(2, 2);
        Mossa m4 = new Mossa(Mossa.Action.MOVE, 0, 2, 3);
        turn.baseMovement(m4);
        assertTrue(!turn.isValidationMove());
        //System.out.print("x: "+turn.getGamer().getPawn(0).getRow());
        //System.out.print(" y "+turn.getGamer().getPawn(0).getColumn()+"\n");
        Mossa m5 = new Mossa(Mossa.Action.MOVE, -1, -1, -1);
        effect = turn.godCardEffect(m5, effect, 0, myPosition);
        assertTrue(effect);
        //pawn0 wants to build in cell[2,4] but it can not
        Mossa m6 = new Mossa(Mossa.Action.BUILD, 0, 2, 4);
        effect = turn.godCardEffect(m6, effect, 0, myPosition);
        assertTrue(!effect);
    }

    /**
     * method that tests if the gamer has the card Prometheus
     */
    @Test
    public void testEffectOfPrometheusCard() {
        gamer.setMyGodCard(Prometheus);
        assertFalse(turn.getPromEffect());
        turn.effectOfPrometheusCard();
        assertTrue(turn.getPromEffect());
        turn.setPromEffect(false);
        gamer.setMyGodCard(Pdor);
        assertFalse(turn.getPromEffect());
        turn.effectOfPrometheusCard();
        assertTrue(!turn.getPromEffect());
    }

    /**
     * method that tests a turn of a gamer with movement and building
     */
    @Test
    public void testMovementAndBuilding() {
        View v = new View();
        Pawn q = new Pawn();
        q.setIdGamer(1);
        q.setIdPawn(0);
        gamer.setMyGodCard(Pdor);
        gamer.setAPawn(1, -2, -2, 0, 0);
        gamer.setAPawn(0, -2, -2, 0, 0);
        turn.getTable().setACell(0, 0, 1, false, false, gamer.getPawn(1));
        turn.getTable().setACell(3, 0, 1, false, false, gamer.getPawn(0));
        turn.getTable().setACell(1, 0, 3, true, false, null);
        turn.getTable().setACell(0, 1, 1, false, false, q);
        turn.getTable().setACell(1, 1, 2, true, false, null);
        turn.getTable().setACell(1, 2, 3, false, true, null);
        v.printTable(turn.getTable());
        Mossa m0 = new Mossa(Mossa.Action.BUILD, 1, 1, 1);
        Mossa m1 = new Mossa(Mossa.Action.MOVE, 1, 1, 0);
        Mossa m2 = new Mossa(Mossa.Action.MOVE, 1, 1, 1);
        Mossa m3 = new Mossa(Mossa.Action.MOVE, 1, 2, 3);
        Mossa m4 = new Mossa(Mossa.Action.BUILD, 0, 1, 2);
        Mossa m5 = new Mossa(Mossa.Action.BUILD, 1, 1, 2);
        Mossa m6 = new Mossa(Mossa.Action.BUILD, 1, 2, 2);
        System.out.println("\n");
        do {
            turn.effectOfPrometheusCard();
            assertTrue(!turn.getPromEffect());
            turn.myMovement(m0);
        } while (!turn.isValidationMove() && turn.getCount() <= 1);
        assertFalse(turn.isValidationMove());

        turn.setCount(0);
        do {
            turn.effectOfPrometheusCard();
            assertTrue(!turn.getPromEffect());
            turn.myMovement(m1);
        } while (!turn.isValidationMove() && turn.getCount() <= 1);
        assertFalse(turn.isValidationMove());

        turn.setCount(0);
        do {
            turn.effectOfPrometheusCard();
            assertTrue(!turn.getPromEffect());
            turn.myMovement(m2);
        } while (!turn.isValidationMove() && turn.getCount() <= 1);
        assertTrue(turn.isValidationMove());
        assertNull(turn.getTable().getTableCell(0, 0).getPawn());
        assertEquals(turn.getGamer().getPawn(1), turn.getTable().getTableCell(1, 1).getPawn());
        assertEquals(1, turn.getIdStartPawn());
        v.printTable(turn.getTable());

        turn.win();
        assertFalse(turn.getGamer().isWinner());

        System.out.println("\n");

        turn.setCount(0);
        do {
            turn.myBuilding(m3);
        } while (!turn.isValidationBuild() && turn.getCount() <= 1);
        assertFalse(turn.isValidationBuild());

        turn.setCount(0);
        do {
            turn.myBuilding(m4);
        } while (!turn.isValidationBuild() && turn.getCount() <= 1);
        assertFalse(turn.isValidationBuild());

        turn.setCount(0);
        do {
            turn.myBuilding(m5);
        } while (!turn.isValidationBuild() && turn.getCount() <= 1);
        assertFalse(turn.isValidationBuild());

        turn.setCount(0);
        do {
            turn.myBuilding(m6);
        } while (!turn.isValidationBuild() && turn.getCount() <= 1);
        assertTrue(turn.isValidationBuild());
        assertEquals(1, turn.getTable().getTableCell(2, 2).getLevel());
        v.printTable(turn.getTable());
    }

    /**
     * method that tests the win of the current gamer
     */
    @Test
    public void testTheGamerWins() {
        View v = new View();
        turn.getGamer().setMyGodCard(Pdor);
        turn.getGamer().setAPawn(0, -2, -2, -2, -2);
        turn.getTable().setACell(1, 1, 2, false, false, turn.getGamer().getPawn(0));
        turn.getTable().setACell(2, 2, 3, true, false, null);
        v.printTable(turn.getTable());
        Mossa win = new Mossa(Mossa.Action.MOVE, 0, 2, 2);
        Mossa build = new Mossa(Mossa.Action.BUILD, 0, 3, 3);
        do {
            turn.effectOfPrometheusCard();
            assertTrue(!turn.getPromEffect());
            turn.myMovement(win);
        } while (!turn.isValidationMove() && turn.getCount() <= 1);
        assertTrue(turn.isValidationMove());
        v.printTable(turn.getTable());
        turn.win();
        assertTrue(turn.getGamer().isWinner());
        assertEquals(0, turn.getGamer().getBuilds());
        turn.setCount(0);
        do {
            turn.myBuilding(build);
        } while (!turn.isValidationMove() && turn.getCount() <= 1);
        assertTrue(turn.isValidationBuild());
    }


}