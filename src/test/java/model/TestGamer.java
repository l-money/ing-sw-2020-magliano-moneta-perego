package model;

import org.junit.Before;
import org.junit.Test;
import santorini.model.Gamer;

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
        gamer = new Gamer(socket, name, id);
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
        //TODO da completare
    }

    @Test
    public void testColorGamer() {
        gamer.setColorGamer(Color.BLUE);
        assertEquals(Color.BLUE, gamer.getColorGamer());
    }

    @Test
    public void testPawns() {
        //TODO da completare e sistemare in gamer
    }
}
