import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import santorini.Turno;
import santorini.View;
import santorini.model.Gamer;
import santorini.model.God;
import santorini.model.Mossa;
import santorini.model.godCards.Apollo;
import santorini.model.godCards.Artemis;
import santorini.model.godCards.Athena;
import santorini.model.godCards.Pdor;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

public class TestView {
    private View view;
    private Gamer f;
    private Gamer g;
    private Gamer h;
    private Apollo a = new Apollo();
    private Artemis b = new Artemis();
    private Athena c = new Athena();
    private ArrayList<Gamer> gamers = new ArrayList<Gamer>();
    private God god = new Pdor();
    private ArrayList<God> listadei = new ArrayList<God>();

    @Before
    public void before() {
        view = new View();
        g = new Gamer(null, "Al", 0, null, null);
        g.setMyGodCard(god);
        f = new Gamer(null, "John", 1, null, null);
        f.setMyGodCard(god);
        h = new Gamer(null, "Jack", 2, null, null);
        h.setMyGodCard(god);
        gamers.add(g);
        gamers.add(f);
        gamers.add(h);
    }

    @Before
    public void before1() {
        view = new View();
        listadei.add(a);
        listadei.add(b);
        listadei.add(c);
    }

    @Test
    public void testColorCellPawn() {
        System.out.print("\n****New table****\n\n");
        view.printGamerInGame(gamers);
        view.printTable(view.getTable());
        System.out.print("\n****pawns in table****\n\n");
        //set pawns of the gamers
        g.setAPawn(0, 0, 0, 0, 0);
        g.setAPawn(1, 0, 0, 0, 0);
        f.setAPawn(0,0,0,0,0);
        f.setAPawn(1,0,0,0,0);
        h.setAPawn(0,0,0,0,0);
        h.setAPawn(1,0,0,0,0);
        //put pawns in table
        view.getTable().setACell(0,0,0,false,false,g.getPawn(0));
        view.getTable().setACell(3,1,1,false,false,g.getPawn(1));
        view.getTable().setACell(1,1,0,false,false,f.getPawn(0));
        view.getTable().setACell(4,1,1,false,false,f.getPawn(1));
        view.getTable().setACell(4,4,0,false,false,h.getPawn(0));
        view.getTable().setACell(0,4,0,false,false,h.getPawn(1));
        view.getTable().setACell(0,1,2,true,false,null);
        view.getTable().setACell(2,0,3,true,false,null);
        view.printTable(view.getTable());
        //move pawn0 of gamer1 in cell[1;0] and builds a dome in cell[2,0]
        view.getTable().setACell(1,0,0,false,false,g.getPawn(0));
        view.getTable().setACell(0,0,0,true,false,null);
        System.out.print("\n****pawn0 moves in [1;0]****\n\n");
        view.printTable(view.getTable());
        System.out.print("\n****pawn0 builds a dome in [2;0]****\n\n");
        view.getTable().setACell(2,0,3,false,true,null);
        view.printTable(view.getTable());
    }

    /**
     * method that tests the coordinate in input
     */
    @Test
    public void testGiveMeStringCoordinate() {
        String s1 = "A";
        String s2 = "3;2";
        String s3 = "6,2";
        String s4 = "2,3";
        String s5 = "A,3";
        int[] coordinate;
        coordinate = view.giveMeStringCoordinate(s1);
        assertEquals(-1, coordinate[0]);
        assertEquals(-1, coordinate[1]);

        coordinate = view.giveMeStringCoordinate(s2);
        assertEquals(-1, coordinate[0]);
        assertEquals(-1, coordinate[1]);

        coordinate = view.giveMeStringCoordinate(s3);
        assertEquals(-1, coordinate[0]);
        assertEquals(-1, coordinate[1]);

        coordinate = view.giveMeStringCoordinate(s5);
        assertEquals(-1, coordinate[0]);
        assertEquals(-1, coordinate[1]);

        coordinate = view.giveMeStringCoordinate(s4);
        assertEquals(2, coordinate[0]);
        assertEquals(3, coordinate[1]);
    }

    /**
     * method that tests if the gamer doesn't want using the card effect
     */
    @Test
    public void testNoGodEffectStringInput() {
        String s1 = "No";
        String s2 = "NoNo";
        Mossa m;
        m = view.noGodEffect(s1);
        assertEquals(Mossa.Action.MOVE, m.getAction());
        assertEquals(-1, m.getIdPawn());
        assertEquals(-1, m.getTargetX());
        assertEquals(-1, m.getTargetY());
        m = view.noGodEffect(s2);
        assertNull(m);
    }

    @Test
    public void testChooseCard() {
        System.out.println("**scelta carte**");
        //ampiezza dell'array list ok
        assertEquals(3, listadei.size());
        //
        view.chooseCards(listadei);
    }

}
