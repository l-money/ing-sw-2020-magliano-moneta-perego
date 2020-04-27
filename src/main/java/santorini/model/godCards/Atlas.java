package santorini.model.godCards;

import santorini.Turno;
import santorini.model.*;

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
        Pawn myPawn = turno.getGamer().getPawn(turno.getIdStartPawn());
        Cell myPawnPosition = turno.getTable().getTableCell(myPawn.getRow(), myPawn.getColumn());
        //request a movement from the gamer
            try {
                buildDome = turno.getGameHandler().richiediMossa(Mossa.Action.BUILD, getOwner());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        //if the gamer doesn't want to move puts the code of the nullEffect: -1,-1,-1
        if (turno.nullEffectForGodCards(buildDome)) {
            //null effect
            atlasEffect = true;
        }
        //else the gamer wants to build a dome
        else {
            do {
                Cell end = turno.getTable().getTableCell(buildDome.getTargetX(), buildDome.getTargetY());//save destination of the building
                ArrayList<Cell> nearCells = turno.getTable().searchAdjacentCells(myPawnPosition);//save the adjacent cells of my position
                //if the destination is adjacent and it is free and not complete
                if ((nearCells.contains(end)) && (end.getLevel() >= 0) && (end.getLevel() <= 3) &&
                        (end.isFree()) && (!end.isComplete()) && (end.getPawn() == null) &&
                        (turno.controlStandardParameter(buildDome))) {
                    //build a dome
                    turno.getTable().getTableCell(end.getX(), end.getY()).setComplete(true);
                    atlasEffect = true;
                    turno.getGamer().setBuilds(0);
                }

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
