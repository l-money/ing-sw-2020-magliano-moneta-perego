package santorini.model;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.Serializable;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestGamer implements Serializable {
    private Gamer gamer;

    @Before
    public void before() {
        Socket socket = new Socket();
        String name = "Ajeje Brazorf";
        int id = 0;
        gamer = new Gamer(socket, name, id, null, null);

    }

    @Test
    public void testNameGamer() {
        assertEquals("Ajeje Brazorf", gamer.getName());
        gamer.setName("Pdor");
        assertEquals("Pdor", gamer.getName());
    }

    @Test
    public void testIdGamer() {
        assertEquals(0, gamer.getIdGamer());
        gamer.setIdGamer(1);
        assertEquals(1, gamer.getIdGamer());
    }

    @Test
    public void testSetUp() {
        assertEquals(1, gamer.getSteps());
        assertEquals(1, gamer.getLevelsUp());
        assertEquals(3, gamer.getLevelsDown());
        assertEquals(1, gamer.getBuilds());
        gamer.setSteps(0);
        gamer.setLevelsUp(0);
        gamer.setLevelsDown(0);
        gamer.setBuilds(0);
        assertEquals(0, gamer.getSteps());
        assertEquals(0, gamer.getLevelsUp());
        assertEquals(0, gamer.getLevelsDown());
        assertEquals(0, gamer.getBuilds());
    }

    @Test
    public void testSocket() {
        gamer.setSocket(null);
        assertNull(gamer.getSocket());
    }

    @Test
    public void testMyGodCard() {
        //TODO to complete
    }

    @Test
    public void testColorGamer() {
        gamer.setId(0);
        assertEquals(Color.YELLOW, gamer.getColorGamer());
        gamer.setId(1);
        assertEquals(Color.RED, gamer.getColorGamer());
        gamer.setId(2);
        assertEquals(Color.BLUE, gamer.getColorGamer());
        gamer.setId(3);
        assertNull(gamer.getColorGamer());
        gamer.setId(-1);
        assertNull(gamer.getColorGamer());
    }

    @Test
    public void testPawns() {
        int id1 = gamer.getPawn(0).getIdPawn();
        int id2 = gamer.getPawn(1).getIdPawn();
        assertEquals(0, id1);
        assertEquals(1, id2);
        assertEquals(gamer.getIdGamer(), gamer.getPawn(0).getIdGamer());
        assertEquals(gamer.getIdGamer(), gamer.getPawn(1).getIdGamer());
        assertEquals(gamer.getColorGamer(), gamer.getPawn(0).getColorPawn());
        assertEquals(gamer.getColorGamer(), gamer.getPawn(1).getColorPawn());
        assertNull(gamer.getPawn(2));
        assertEquals(-1, gamer.getPawn(0).getRow());
        assertEquals(-1, gamer.getPawn(0).getColumn());
        assertEquals(0, gamer.getPawn(0).getPastLevel());
        assertEquals(0, gamer.getPawn(0).getPresentLevel());
        assertEquals(-1, gamer.getPawn(1).getRow());
        assertEquals(-1, gamer.getPawn(1).getColumn());
        assertEquals(0, gamer.getPawn(1).getPastLevel());
        assertEquals(0, gamer.getPawn(1).getPresentLevel());
        gamer.getPawn(0).setPresentLevel(1);
        gamer.getPawn(1).setPresentLevel(1);
        assertEquals(1, gamer.getPawn(0).getPresentLevel());
        assertEquals(1, gamer.getPawn(1).getPresentLevel());
    }
}
