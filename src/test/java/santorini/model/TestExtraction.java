package santorini.model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Class TestExtraction
 *
 * @author G. Perego
 */

public class TestExtraction {
    private Extraction extraction;

    @Before
    public void before() {
        extraction = new Extraction();

    }

    /**
     * method that tests the lenght of the list of god card
     */

    @Test
    public void testLenght() {
        int number = 3;
        List<God> cards = extraction.extractionGods(number);
        assertEquals(number, cards.size());
    }

    /**
     * method that tests the uniqueness of the list of god card


    @Test
    public void testUniqueness() {
        List<God> cards = extraction.extractionGods(3);
        assertTrue(!cards.get(0).equals(cards.get(1)));
        assertTrue(!cards.get(0).equals(cards.get(2)));
        assertTrue(!cards.get(1).equals(cards.get(2)));
    }
     */

}
