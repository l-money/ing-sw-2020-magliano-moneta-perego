package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Cell;
import santorini.model.Gamer;
import santorini.model.God;

public class Athena extends God {
    private Cell start;
    private boolean athenaEffect;

    @Override
    public String getName() {
        return "Athena";
    }

    @Override
    public String getDescription() {
        return "Tuo avversario :se nel tuo ultimo turno uno dei tuoi lavoratori è salito di livello,\nin questo turno i lavoratori avversari non possono salire di livello";
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
        int x1 = turno.getGamer().getPawn(turno.getIdStartPawn()).getRow();
        int y1 = turno.getGamer().getPawn(turno.getIdStartPawn()).getColumn();
        start = turno.getTable().getTableCell(x1, y1);
    }

    /**
     * Features added by card after its owner does his moves
     *
     * @param turno
     */
    public void afterOwnerMoving(Turno turno) {
        athenaEffect = false;
        int x2 = turno.getMove().getTargetX();
        int y2 = turno.getMove().getTargetY();
        Cell end = turno.getTable().getTableCell(x2, y2);
        if (((end.getLevel() - start.getLevel()) == 1) &&
                (end.getPawn() == turno.getTable().getTableCell(x2, y2).getPawn()) &&
                (end.getPawn() == turno.getGamer().getPawn(turno.getIdStartPawn()))
        ) {
            athenaEffect = true;
        }
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

    }

    /**
     * Features added by card before other player does his moves
     *
     * @param other player to customize
     */
    public void beforeOtherMoving(Gamer other) {
        if (athenaEffect) {
            other.setLevelsUp(0);
        }
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
        other.setLevelsUp(1);
    }
}
