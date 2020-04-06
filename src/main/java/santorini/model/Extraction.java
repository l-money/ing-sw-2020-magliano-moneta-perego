package santorini.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class Extraction
 *
 * @author G. Perego
 */

public class Extraction implements Serializable {
    private List<God> gods;

    public Extraction() {
        gods = new ArrayList<God>();
        gods.add(new Apollo(null));
        gods.add(new Artemis(null));
        gods.add(new Athena(null));
        gods.add(new Atlas(null));
        /**
         gods.add(new Demeter());
         gods.add(new Hephaestus());
         gods.add(new Minotaur());
         gods.add(new Pan());
         gods.add(new Prometheus());
         gods.add(new Zeus());
         gods.add(new Demeter());
         gods.add(new Hephaestus());
         gods.add(new Zeus());
         gods.add(new Poseidon());
         gods.add(new Hera());
         gods.add(new Triton());
         gods.add(new Ares());
         */
    }

    /**
     * method extraction cards
     *
     * @param players number of gamers
     * @return an array of god card
     */
    public List<God> extractionGods(int players) {
        List<God> cards = new ArrayList<God>();
        for (int i = 0; i < players; i++) {
            int n = new Random().nextInt(gods.size());
            cards.add(gods.get(n));
            gods.remove(n);
        }
        return cards;
    }

}
