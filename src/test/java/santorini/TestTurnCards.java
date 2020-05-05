package santorini;

import org.junit.Before;
import org.junit.Test;
import santorini.model.Gamer;
import santorini.model.God;
import santorini.model.Mossa;
import santorini.model.Table;
import santorini.model.godCards.Apollo;
import santorini.model.godCards.Artemis;
import santorini.model.godCards.Athena;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestTurnCards {
    private ArrayList<God> cards;
    private God Apollo = new Apollo();
    private God Artemis = new Artemis();
    private God Athena = new Athena();
    private Gamer g1, g2, g3;
    private ArrayList<Gamer> players = new ArrayList<>();
    private Table table;
    private Turno t1;
    private Turno t2;
    private Turno t3;
    //TODO : NOT NULL
    private View v = new View(null, null);

    @Before
    public void before() {
        table = new Table();
        g1 = new Gamer(null, "Ulisse", 0, null, null);
        g2 = new Gamer(null, "Ettore", 1, null, null);
        g3 = new Gamer(null, "Enea", 2, null, null);
        players.add(g1);
        players.add(g2);
        players.add(g3);
        cards = new ArrayList<>();
        cards.add(Apollo);
        cards.add(Artemis);
        cards.add(Athena);
        t1 = new Turno(cards, g1, table, null);
        t2 = new Turno(cards, g2, table, null);
        t3 = new Turno(cards, g3, table, null);
        g1.setMyGodCard(cards.get(0));
        g2.setMyGodCard(cards.get(1));
        g3.setMyGodCard(cards.get(2));
        g1.setAPawn(0, -2, -2, 0, 0);
        g2.setAPawn(0, -2, -2, 0, 0);
        g2.setAPawn(1, -2, -2, 1, 0);
        g3.setAPawn(0, -2, -2, 0, 0);
        t1.getTable().setACell(0, 0, 1, false, false, g1.getPawn(0));
        t1.getTable().setACell(1, 1, 2, false, false, g2.getPawn(0));
        t1.getTable().setACell(2, 0, 2, false, false, g2.getPawn(1));
        t1.getTable().setACell(1, 0, 0, false, false, g3.getPawn(0));
        t1.getTable().setACell(2, 1, 1, true, false, null);
        t2.getTable().setACell(0, 0, 1, false, false, g1.getPawn(0));
        t2.getTable().setACell(1, 1, 2, false, false, g2.getPawn(0));
        t2.getTable().setACell(2, 0, 2, false, false, g2.getPawn(1));
        t2.getTable().setACell(1, 0, 0, false, false, g3.getPawn(0));
        t2.getTable().setACell(2, 1, 1, true, false, null);
    }

    @Test
    public void testApolloEffect() {
        //move p0 from cell[0;0] to cell[1,1]: possible switch positions
        v.printTable(t1.getTable());
        Mossa m0 = new Mossa(Mossa.Action.MOVE, 0, 1, 1);
        t1.setMove(m0);
        do {
            t1.effectOfPrometheusCard();
            assertFalse(t1.getPromEffect());
            t1.myMovement(t1.getMove());
            t1.getValidationMove(t1.isValidationMove());
        } while (!t1.isValidationMove() && t1.getCount() <= 2);
        assertTrue(t1.isValidationMove());
        assertEquals(t1.getGamer().getPawn(0), t1.getTable().getTableCell(1, 1).getPawn());
        assertEquals(1, t1.getGamer().getPawn(0).getRow());
        assertEquals(1, t1.getGamer().getPawn(0).getColumn());
        assertEquals(1, t1.getGamer().getPawn(0).getPastLevel());
        assertEquals(2, t1.getGamer().getPawn(0).getPresentLevel());
        assertEquals(g2.getPawn(0), t1.getTable().getTableCell(0, 0).getPawn());
        assertEquals(0, g2.getPawn(0).getRow());
        assertEquals(0, g2.getPawn(0).getColumn());
        assertEquals(2, g2.getPawn(0).getPastLevel());
        assertEquals(1, g2.getPawn(0).getPresentLevel());
        v.printTable(t1.getTable());
        //move p0 from cell[1;1] to cell[1,0]: possible switch positions
        t1.getGamer().setSteps(1);
        Mossa m1 = new Mossa(Mossa.Action.MOVE, 0, 1, 0);
        t1.setMove(m1);
        do {
            t1.effectOfPrometheusCard();
            assertFalse(t1.getPromEffect());
            t1.myMovement(t1.getMove());
            t1.getValidationMove(t1.isValidationMove());
        } while (!t1.isValidationMove() && t1.getCount() <= 2);
        //validationMove is true for Steps=0 of the baseMovement out of the beforeOwnerMoving
        assertTrue(t1.isValidationMove());
        assertEquals(t1.getGamer().getPawn(0), t1.getTable().getTableCell(1, 0).getPawn());
        assertEquals(1, t1.getGamer().getPawn(0).getRow());
        assertEquals(0, t1.getGamer().getPawn(0).getColumn());
        assertEquals(2, t1.getGamer().getPawn(0).getPastLevel());
        assertEquals(0, t1.getGamer().getPawn(0).getPresentLevel());
        assertEquals(g3.getPawn(0), t1.getTable().getTableCell(1, 1).getPawn());
        assertEquals(1, g3.getPawn(0).getRow());
        assertEquals(1, g3.getPawn(0).getColumn());
        assertEquals(0, g3.getPawn(0).getPastLevel());
        assertEquals(2, g3.getPawn(0).getPresentLevel());
        v.printTable(t1.getTable());
        //move p0 from cell[1;0] to cell[2,0]: impossible switch positions
        t1.getGamer().setSteps(1);
        Mossa m2 = new Mossa(Mossa.Action.MOVE, 0, 2, 0);
        t1.setMove(m2);
        do {
            t1.effectOfPrometheusCard();
            assertFalse(t1.getPromEffect());
            t1.myMovement(t1.getMove());
            t1.getValidationMove(t1.isValidationMove());
        } while (!t1.isValidationMove() && t1.getCount() <= 2);
        assertTrue(t1.isValidationMove());
        assertEquals(t1.getGamer().getPawn(0), t1.getTable().getTableCell(1, 0).getPawn());
        assertEquals(1, t1.getGamer().getPawn(0).getRow());
        assertEquals(0, t1.getGamer().getPawn(0).getColumn());
        assertEquals(2, t1.getGamer().getPawn(0).getPastLevel());
        assertEquals(0, t1.getGamer().getPawn(0).getPresentLevel());
        v.printTable(t1.getTable());
        //move p0 from cell[1;0] to cell[2,1]: possible base movement
        t1.getGamer().setSteps(1);
        Mossa m3 = new Mossa(Mossa.Action.MOVE, 0, 2, 1);
        t1.setMove(m3);
        do {
            t1.effectOfPrometheusCard();
            assertFalse(t1.getPromEffect());
            t1.myMovement(t1.getMove());
            t1.getValidationMove(t1.isValidationMove());
        } while (!t1.isValidationMove() && t1.getCount() <= 2);
        //When the pawn wants to go back, it can not and validationMose is true
        //It doesn't do the second step
        assertTrue(t1.isValidationMove());
        assertEquals(t1.getGamer().getPawn(0), t1.getTable().getTableCell(2, 1).getPawn());
        assertEquals(2, t1.getGamer().getPawn(0).getRow());
        assertEquals(1, t1.getGamer().getPawn(0).getColumn());
        assertEquals(0, t1.getGamer().getPawn(0).getPastLevel());
        assertEquals(1, t1.getGamer().getPawn(0).getPresentLevel());
        v.printTable(t1.getTable());
    }

    @Test
    public void testArtemisEffect() {
        v.printGamerInGame(players);
        v.printTable(t2.getTable());
        Mossa m0 = new Mossa(Mossa.Action.MOVE, 0, 2, 1);
        Mossa m1 = new Mossa(Mossa.Action.MOVE, 0, 3, 2);
        Mossa m2 = new Mossa(Mossa.Action.MOVE, 0, 3, 3);
        Mossa No = new Mossa(Mossa.Action.MOVE, -1, -1, -1);
        Mossa a0 = new Mossa(Mossa.Action.MOVE, 0, 3, 1);
        Mossa a2 = new Mossa(Mossa.Action.MOVE, 0, 3, 2);
        //move p0 from cell[1;1] to cell[3;1] with two steps
        t2.setMove(m0);
        t2.getGamer().getMyGodCard().setEffectMove(a0);
        do {
            t2.effectOfPrometheusCard();
            assertFalse(t2.getPromEffect());
            t2.myMovement(t2.getMove());
            t2.getValidationMove(t2.isValidationMove());
        } while (!t2.isValidationMove() && t2.getCount() <= 2);
        assertTrue(t2.isValidationMove());
        assertEquals(g2.getPawn(0), t2.getTable().getTableCell(3, 1).getPawn());
        assertEquals(3, t2.getGamer().getPawn(0).getRow());
        assertEquals(1, t2.getGamer().getPawn(0).getColumn());
        assertEquals(1, t2.getGamer().getPawn(0).getPastLevel());
        assertEquals(0, t2.getGamer().getPawn(0).getPresentLevel());
        v.printTable(t2.getTable());
        //move p0 from cell[3;1] to cell[3;2] with one steps
        //No second step
        t2.getGamer().setSteps(1);
        t2.setMove(m1);
        t2.getGamer().getMyGodCard().setEffectMove(No);
        do {
            t2.effectOfPrometheusCard();
            assertFalse(t2.getPromEffect());
            t2.myMovement(t2.getMove());
            t2.getValidationMove(t2.isValidationMove());
        } while (!t2.isValidationMove() && t2.getCount() <= 2);
        assertTrue(t2.isValidationMove());
        assertEquals(g2.getPawn(0), t2.getTable().getTableCell(3, 2).getPawn());
        assertEquals(3, t2.getGamer().getPawn(0).getRow());
        assertEquals(2, t2.getGamer().getPawn(0).getColumn());
        assertEquals(0, t2.getGamer().getPawn(0).getPastLevel());
        assertEquals(0, t2.getGamer().getPawn(0).getPresentLevel());
        v.printTable(t2.getTable());
        //move p0 from cell[3;2] to cell[3;2] with two
        //Impossible, p0 remains into cell[3;3]
        t2.getGamer().setSteps(1);
        t2.setMove(m2);
        t2.getGamer().getMyGodCard().setEffectMove(a2);
        do {
            t2.effectOfPrometheusCard();
            assertFalse(t2.getPromEffect());
            t2.myMovement(t2.getMove());
            t2.getValidationMove(t2.isValidationMove());
        } while (!t2.isValidationMove() && t2.getCount() <= 2);
        assertTrue(t2.isValidationMove());
        assertEquals(g2.getPawn(0), t2.getTable().getTableCell(3, 3).getPawn());
        assertEquals(3, t2.getGamer().getPawn(0).getRow());
        assertEquals(3, t2.getGamer().getPawn(0).getColumn());
        assertEquals(0, t2.getGamer().getPawn(0).getPastLevel());
        assertEquals(0, t2.getGamer().getPawn(0).getPresentLevel());
        v.printTable(t2.getTable());
    }


}
