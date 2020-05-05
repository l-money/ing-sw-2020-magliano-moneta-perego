package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Cell;
import santorini.model.Gamer;
import santorini.model.God;

public class Athena extends God {
    private Cell start;
    private boolean athenaEffect;
    private int idMyGamer;

    public Athena() {
        super("Athena", "Tuo avversario :se nel tuo ultimo turno uno dei tuoi lavoratori è salito di livello,\n" +
                "in questo turno i lavoratori avversari non possono salire di livello");
    }


    /**
     * Initialize player variables with card
     *
     * @param g player owner of card
     */
    public void initializeOwner(Gamer g) {
        idMyGamer = g.getIdGamer();

    }

    /**
     * Features added by card before its owner does his moves
     *
     * @param turno
     */
    public void beforeOwnerMoving(Turno turno) {
        turno.setAthenaEffect(false);
        int x1 = turno.getGamer().getPawn(turno.getIdStartPawn()).getRow();
        int y1 = turno.getGamer().getPawn(turno.getIdStartPawn()).getColumn();
        start = turno.getTable().getTableCell(x1, y1);//save the start position of the pawn
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
        Cell end = turno.getTable().getTableCell(x2, y2);//save the end position of the pawn
        //if the pawn goes up to one level
        //and the pawn is the same of the end cell and of the gamer
        if (((end.getLevel() - start.getLevel()) == 1) &&
                (end.getPawn() == turno.getTable().getTableCell(x2, y2).getPawn()) &&
                (end.getPawn() == turno.getGamer().getPawn(turno.getIdStartPawn()))
        ) {
            //athenaEffect is true
            athenaEffect = true;
        }
        turno.setAthenaEffect(athenaEffect);
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
        //if athenaEffect is true, the others gamers can not go up to one level
        /**
         if ((athenaEffect)&& (other.getIdGamer()!=idMyGamer)) {
            other.setLevelsUp(0);
         System.out.println(other.getId()+" "+other.getColorGamer()+" "+other.getLevelsUp());
        }
         */

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
