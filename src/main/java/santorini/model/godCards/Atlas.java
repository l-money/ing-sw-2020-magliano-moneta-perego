package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Cell;
import santorini.model.Gamer;
import santorini.model.God;
import santorini.model.Mossa;

import java.io.IOException;
import java.util.ArrayList;

public class Atlas extends God {
    private Mossa buildDome;
    private boolean atlasEffect;

    @Override
    public String getName() {
        return "Atlas";
    }

    @Override
    public String getDescription() {
        return "Tua costruzione: il tuo lavoratore può costruire una cupola\nsu qualsiasi livello, compreso il terreno";
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
        atlasEffect = false;
        do {
            try {
                buildDome = turno.getGameHandler().richiediMossa(Mossa.Action.BUILD, getOwner());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (buildDome.getIdPawn() != turno.getIdStartPawn()) {
                atlasEffect = false;
            } else {
                if ((buildDome.getTargetX() < 0) || (buildDome.getTargetY() < 0)) {
                    atlasEffect = false;
                } else {
                    int x = buildDome.getTargetX();
                    int y = buildDome.getTargetY();
                    int i = turno.getGamer().getPawn(turno.getIdStartPawn()).getRow();
                    int j = turno.getGamer().getPawn(turno.getIdStartPawn()).getColumn();
                    ArrayList<Cell> nearCells = turno.getTable().searchAdjacentCells(turno.getTable().getTableCell(i, j));
                    if (!(nearCells.contains(turno.getTable().getTableCell(x, y)))) {
                        atlasEffect = false;
                    } else {
                        if ((!turno.getTable().getTableCell(x, y).isFree()) ||
                                (turno.getTable().getTableCell(x, y).isComplete())) {
                            atlasEffect = false;
                        } else {
                            turno.getTable().getTableCell(x, y).setComplete(true);
                            turno.getGamer().setBuilds(0);
                            atlasEffect = true;
                        }
                    }
                }
            }
            //if(!atlasEffect){message sendField}
        } while (!atlasEffect);
    }

    /**
     * Features added by card after its owner starts building
     *
     * @param turno current turn
     */
    public void afterOwnerBuilding(Turno turno) {

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
