package santorini.model;

import santorini.model.godCards.*;

import java.io.Serializable;
import java.util.*;

/**
 * Class Extraction
 */

public class Extraction implements Serializable {
    private ArrayList<God> gods;

    public Extraction() {
        gods = new ArrayList<>();
        /**
         gods.add(new Apollo());
         gods.add(new Artemis());
         gods.add(new Athena());
         gods.add(new Atlas());
         gods.add(new Demeter());
         gods.add(new Hephaestus());
         gods.add(new Minotaur());
         gods.add(new Pan());
         gods.add(new Prometheus());
         //avanzate
         gods.add(new Ares());
         gods.add(new Chronus());
         gods.add(new Hestia());
         gods.add(new Triton());
         gods.add(new Zeus());
         */
        gods.add(new Hephaestus());
        gods.add(new Hephaestus());
        gods.add(new Hephaestus());
        Collections.shuffle(gods);
    }

    /**
     * method extraction cards
     *
     * @param players number of players in game
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
