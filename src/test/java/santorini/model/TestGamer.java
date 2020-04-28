package santorini.model;

import org.junit.Before;
import org.junit.Test;
import santorini.model.godCards.Apollo;
import santorini.model.godCards.Artemis;
import santorini.model.godCards.Athena;

import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestGamer implements Serializable {
    private Gamer gamer;
    private ArrayList<God> gods;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    @Before
    public void before() {
        Socket socket = new Socket();
        String name = "Ajeje Brazorf";
        int id = 0;
        gamer = new Gamer(socket, name, id, inputStream, outputStream);
        gods = new ArrayList<>();
        gods.add(new Apollo());
        gods.add(new Artemis());
        gods.add(new Athena());
    }

    /**
     * method that tests the name of the gamer
     */
    @Test
    public void testNameGamer() {
        assertEquals("Ajeje Brazorf", gamer.getName());
        gamer.setName("Pdor");
        assertEquals("Pdor", gamer.getName());
    }

    /**
     * method that tests the id of the gamer
     */
    @Test
    public void testIdGamer() {
        assertEquals(0, gamer.getIdGamer());
        gamer.setIdGamer(1);
        assertEquals(1, gamer.getIdGamer());
    }

    /**
     * method that tests the setup of the gamer and the possible change of them
     */
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

    /**
     * method that tests the socket of the gamer
     */
    @Test
    public void testSocket() {
        Socket s = new Socket();
        gamer.setSocket(null);
        assertNull(gamer.getSocket());
        gamer.setSocket(s);
        assertEquals(s, gamer.getSocket());
    }

    /**
     * method that tests the outputStream and inputStream
     */
    @Test
    public void testOutputStreamAndInputStream() {
        assertEquals(outputStream, gamer.getOutputStream());
        assertEquals(inputStream, gamer.getInputStream());
    }

    /**
     * method that tests the GodCard of the gamer
     */
    @Test
    public void testMyGodCard() {
        gamer.setMyGodCard(gods.get(0));
        assertEquals(gods.get(0), gamer.getMyGodCard());
        assertEquals(gods.get(0).getName(), gamer.getMyGodCard().getName());
        gamer.setMyGodCard(gods.get(1));
        assertEquals(gods.get(1), gamer.getMyGodCard());
        assertEquals(gods.get(1).getName(), gamer.getMyGodCard().getName());
        gamer.setMyGodCard(null);
        assertNull(gamer.getMyGodCard());
    }

    /**
     * method that tests the color of the gamer
     */
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

    /**
     * method that tests the pawns of the gamer
     */
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

    /**
     * method that tests setAPawn method
     */
    @Test
    public void testSetAPawn() {
        gamer.setAPawn(0, 2, 2, 0, 1);
        gamer.setAPawn(1, 3, 3, 1, 2);
        assertEquals(2, gamer.getPawn(0).getRow());
        assertEquals(2, gamer.getPawn(0).getColumn());
        assertEquals(0, gamer.getPawn(0).getPastLevel());
        assertEquals(1, gamer.getPawn(0).getPresentLevel());
        assertEquals(3, gamer.getPawn(1).getRow());
        assertEquals(3, gamer.getPawn(1).getColumn());
        assertEquals(1, gamer.getPawn(1).getPastLevel());
        assertEquals(2, gamer.getPawn(1).getPresentLevel());
    }
}
