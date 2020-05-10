package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Gamer;
import santorini.model.God;
//FINITA
public class Athena extends God {
    private boolean athenaEffect;

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

    }

    /**
     * Features added by card before its owner does his moves
     *
     * @param turno current turn
     */
    public void beforeOwnerMoving(Turno turno) {
        if (athenaEffect) {
            turno.getGameHandler().getGame().broadcastMessage("\u001B[34m" + "Effetto di Athena annullato.\n" + "\u001B[0m");
        }
        athenaEffect = false;
    }

    /**
     * Features added by card after its owner does his moves
     *
     * @param turno current turn
     */
    public void afterOwnerMoving(Turno turno) {

        if (turno.isValidationMove()) {

            //broadcast message of movement
            turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha mosso: " + turno.getMove().getIdPawn() +
                    " in [" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
            //print status of the table
            turno.printTableStatusTurn(turno.isValidationMove());

            int id = turno.getMove().getIdPawn();
            if (turno.getGamer().getPawn(id).getPresentLevel() - turno.getGamer().getPawn(id).getPastLevel() == 1) {
                //athenaEffect is true
                athenaEffect = true;
                for (Gamer g : others) {
                    g.setLevelsUp(0);
                }
            } else {
                //set others levelsUp == 1
                athenaEffect = false;
                for (Gamer g : others) {
                    g.setLevelsUp(1);
                }
            }

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
        if (turno.isValidationBuild()) {
            //broadcast message of building
            turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha costruito in: " +
                    "[" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
            //print status of the table
            //turno.printTableStatusTurn(turno.isValidationBuild());

            if (athenaEffect) {
                for (Gamer g : others) {
                    turno.getGameHandler().sendMessage(g, "\u001B[34m" + turno.getGamer().getName() + " è salito di livello con Athena.\n" +
                            "In questo turno non puoi salire di livello." + "\u001B[0m" + "\n");
                }
            }
        }
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
