package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Cell;
import santorini.model.Gamer;
import santorini.model.God;
import santorini.model.Mossa;

import java.io.IOException;

public class Hephaestus extends God {
    private boolean HEffect;
    private Mossa buildingPlus;
    private Cell sameBuildingCell;

    @Override
    public String getName() {
        return "Hephaestus";
    }

    @Override
    public String getDescription() {
        return "Tua costruzione: il tuo lavoratore\n" +
                "può costruire un blocco aggiuntivo\n" +
                "(non una cupola) al di sopra del primo blocco.";
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

    }

    /**
     * Features added by card after its owner starts building
     *
     * @param turno current turn
     */
    public void afterOwnerBuilding(Turno turno) {
        int x = turno.getMove().getTargetX();
        int y = turno.getMove().getTargetY();
        sameBuildingCell = turno.getTable().getTableCell(x, y);
            try {
                buildingPlus = turno.getGameHandler().richiediMossa(Mossa.Action.BUILD, getOwner());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        do {
            turno.getGamer().setBuilds(1);
            turno.godCardEffect(buildingPlus, HEffect, 3, sameBuildingCell);
            if (!HEffect) {
                turno.sendFailed();
                //ask another destination of the build
                try {
                    buildingPlus = turno.getGameHandler().richiediMossa(Mossa.Action.BUILD, getOwner());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } while (!HEffect);
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
