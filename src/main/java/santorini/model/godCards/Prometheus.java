package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Gamer;
import santorini.model.Mossa;

//G
public class Prometheus extends God {
    private boolean promEffect = false;
    private boolean printStatus = false;
    private Mossa promBuild;
    private int control = 0;


    public Prometheus() {
        super("Prometheus", "Tuo turno: se il tuo lavoratore\n" +
                "non sale di livello, allora puoi\n" +
                "costruire sia prima, sia dopo aver mosso\n" +
                "il tuo lavoratore");
    }

    /**
     * Initialize player variables with card
     *
     * @param turno player owner of card
     */
    public void initializeOwner(Turno turno) {
        promEffect = false;
        printStatus = false;
        if (control == 0) {
            do {
                turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Hai Prometheus, se costruisci sia prima,\n" +
                        "sia dopo il movimento, non puoi salire di livello." +
                        "\nSe non vuoi attivare l'effetto, scegli l'opzione 'No'" + "\u001B[0m");
                promBuild = turno.buildingRequest();
                if (turno.nullEffectForGodCards(promBuild)) {
                    promEffect = true;
                    printStatus = false;
                    turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Effetto annullato " + "\u001B[0m");
                    control++;
                    turno.getGamer().setLevelsUp(1);
                } else {
                    turno.baseBuilding(promBuild);
                    promEffect = turno.isValidationBuild();
                    printStatus = promEffect;
                    turno.getValidation(promEffect);
                }
                if (promEffect && printStatus) {
                    turno.setMove(promBuild);
                    turno.getGameHandler().getGame().broadcastMessage("\u001B[34m" +
                            "Effetto di Prometheus attivato" + "\u001B[0m");
                    //broadcast message of building
                    turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha costruito in: " +
                            "[" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
                    //print table status
                    turno.printTableStatusTurn(promEffect);
                    //set levelsUp = 0
                    turno.getGamer().setLevelsUp(0);
                    turno.getGamer().setBuilds(1);
                    control++;
                    turno.setCount(0);
                }
            } while (!promEffect && turno.getCount() < 3);
            turno.methodLoser(promEffect, turno.getCount(), turno.getGamer());
            if (turno.getGamer().getLoser()) {
                turno.getGamer().setSteps(0);
                turno.setValidationMove(true);
                turno.getGamer().setBuilds(0);
                turno.setValidationBuild(true);
            }
        }
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
        if (turno.isValidationMove()) {
            //broadcast message of movement
            turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha mosso: " + turno.getMove().getIdPawn() +
                    " in [" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
            //print status of the table
            turno.printTableStatusTurn(true);
            control = 0;
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
            turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha costruito in: " +
                    "[" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
            //print status of the table
            turno.printTableStatusTurn(true);
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
