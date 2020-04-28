package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Gamer;
import santorini.model.God;
import santorini.model.Mossa;

import java.io.IOException;

public class Hephaestus extends God {
    private boolean HEffect;
    private Mossa buildingPlus;

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
     * @param turno
     */
    public void beforeOwnerMoving(Turno turno) {

    }

    /**
     * Features added by card after its owner does his moves
     *
     * @param turno
     */
    public void afterOwnerMoving(Turno turno) {

    }

    /**
     * Features added by card before its owner starts building
     *
     * @param turno
     */
    public void beforeOwnerBuilding(Turno turno) {

    }

    /**
     * Features added by card after its owner starts building
     *
     * @param turno
     */
    public void afterOwnerBuilding(Turno turno) {
        int x = turno.getMove().getTargetX();
        int y = turno.getMove().getTargetY();
        do {
            HEffect = false;
            //I ask to the gamer if he wants to build again on the same cell
            //The building is applicated if the gamers puts the same coordinates
            //If the gamer doesn't build, he have to put idPawn = -1 into buildPlus
            try {
                buildingPlus = turno.getGameHandler().richiediMossa(Mossa.Action.MOVE, getOwner());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (buildingPlus.getIdPawn() == -1) {
                HEffect = true; //The gamer doesn't want to build again on the same cell
            } else {
                if (buildingPlus.getIdPawn() != turno.getIdStartPawn()) {
                    HEffect = false;
                } else {
                    if ((buildingPlus.getTargetX() != x) || (buildingPlus.getTargetY() != y)) {
                        HEffect = false;
                    } else {
                        int levelPlus = turno.getTable().getTableCell(x, y).getLevel() + 1;
                        if (levelPlus > 3) {
                            HEffect = false;
                        } else {
                            turno.getTable().getTableCell(x, y).setLevel(levelPlus);
                            HEffect = true;
                        }
                    }
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
