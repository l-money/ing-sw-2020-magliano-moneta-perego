package santorini.model.godCards;

import santorini.Turno;
import santorini.model.*;

import java.io.IOException;

public class Atlas extends God {
    private Mossa buildDome;
    private boolean atlasEffect;

    public Atlas() {
        super("Atlas", "Tua costruzione: il tuo lavoratore può costruire una cupola\n" +
                "su qualsiasi livello, compreso il terreno");
    }

    /**
     * Initialize player variables with card
     *
     * @param g player owner of card
     */
    public void initializeOwner(Gamer g) {

    }

    /**
     * Features added by card before its owner does his moves
     *
     * @param turno current turn
     */
    public void beforeOwnerMoving(Turno turno) {
    }

    /**
     * Features added by card after its owner does his moves
     *
     * @param turno current turn
     */
    public void afterOwnerMoving(Turno turno) {

    }

    /**
     * Features added by card before its owner starts building
     *
     * @param turno current turn
     */
    public void beforeOwnerBuilding(Turno turno) {
        //atlasEffect = false;
        //request a movement from the gamer
            try {
                buildDome = turno.getGameHandler().richiediMossa(Mossa.Action.BUILD, getOwner());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            do {
                turno.godCardEffect(buildDome, atlasEffect, 1, null);
                if (!atlasEffect) {
                    //if atlasEffect is false, the destination is incorrect, insert the correct destination
                    turno.sendFailed();
                    //ask another destination of the build
                    try {
                        buildDome = turno.getGameHandler().richiediMossa(Mossa.Action.BUILD, getOwner());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } while (!atlasEffect);
        }

    /**
     * Features added by card after its owner starts building
     *
     * @param turno current turn
     */
    public void afterOwnerBuilding(Turno turno) {
        turno.getGamer().setBuilds(1);
    }

    /**
     * Features added by card before other player does his moves
     *
     * @param other player to customize
     */
    public void beforeOtherMoving(Gamer other) {

    }

    /**
     * Features added by card after other player does his moves
     *
     * @param other player to customize
     */
    public void afterOtherMoving(Gamer other) {

    }

    /**
     * Features added by card before other player starts building
     *
     * @param other player to customize
     */
    public void beforeOtherBuilding(Gamer other) {

    }

    /**
     * Features added by card before other player starts building
     *
     * @param other player to customize
     */
    public void afterOtherBuilding(Gamer other) {

    }

}
