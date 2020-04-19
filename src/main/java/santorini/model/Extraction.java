package santorini.model;

import santorini.model.godCards.*;

import java.io.Serializable;
import java.util.*;

/**
 * Class Extraction
 *
 * @author G. Perego
 */

public class Extraction implements Serializable {
    private ArrayList<God> gods;

    public Extraction() {
        gods = new ArrayList<God>();
        gods.add(new Apollo());
        gods.add(new Artemis());
        gods.add(new Athena());
        gods.add(new Atlas());
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
        Collections.shuffle(gods);
    }

    /**
     * method extraction cards
     *
     * @param players number of gamers
     * @return an array of god card
     */
    public ArrayList<God> extractionGods(int players) {
        ArrayList<God> cards = new ArrayList<>();
        for (int i = 0; i < players; i++) {
            cards.add(gods.get(i));
        }
        return cards;
    }

}
