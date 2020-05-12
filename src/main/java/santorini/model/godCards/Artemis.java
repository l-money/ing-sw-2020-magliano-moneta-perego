package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Cell;
import santorini.model.Gamer;
import santorini.model.Mossa;

//FINITA
public class Artemis extends God {
    private Cell start;
    private boolean artemisEffect;
    private boolean printerStatus = true;
    private Mossa effectMove2;
    private int idM;
    private boolean control = true;


    public Artemis() {
        super("Artemis", "Tuo spostamento: il tuo lavoratore può spostarsi una volta in più\n" +
                "ma non può tornare alla casella da cui è partito");
    }

    /**
     * method getEffectMove
     *
     * @return move
     */
    @Override
    public Mossa getEffectMove() {
        return effectMove2;
    }

    /**
     * method setEffectMove
     * @param effectMove move
     */
    @Override
    public void setEffectMove(Mossa effectMove) {
        this.effectMove2 = effectMove;
    }

    /**
     * method getIdM
     * @return the id pawn of the movement
     */
    public int getIdM() {
        return idM;
    }

    /**
     * method setIdM
     * @param idM sets the id pawn of the movement
     */
    public void setIdM(int idM) {
        this.idM = idM;
    }

    /**
     * Initialize player variables with card
     *
     * @param turno player owner of card
     */
    public void initializeOwner(Turno turno) {

    }

    /**
     * Features added by card before its owner does his moves
     *
     * @param turno the current turn
     */
    public void beforeOwnerMoving(Turno turno) {
        control = true;
        printerStatus = true;
        idM = turno.getMove().getIdPawn();
        int startX = turno.getGamer().getPawn(idM).getRow();//save start position X
        int startY = turno.getGamer().getPawn(idM).getColumn();//save start position Y
        start = turno.getTable().getTableCell(startX, startY);
    }

    /**
     * Features added by card after its owner does his moves
     *
     * @param turno the current turn
     */
    public void afterOwnerMoving(Turno turno) {
        if (turno.isValidationMove()) {
            if (printerStatus) {
                //broadcast message of movement
                turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha mosso: " + turno.getMove().getIdPawn() +
                        " in [" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
                //print status of the table
                turno.printTableStatusTurn(turno.isValidationMove());
                printerStatus = false;
            }
            artemisEffect = false;
            setIdM(turno.getMove().getIdPawn());
            turno.setCount(0);
            turno.getGamer().setSteps(1);
            do {
                turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Hai Artemis, puoi muoverti una volta in più.\n" +
                        "Se non vuoi muoverti scegli l'opzione 'No'" + "\u001B[0m");
                effectMove2 = turno.moveRequest();
                if (turno.nullEffectForGodCards(effectMove2)) {
                    artemisEffect = true;
                    printerStatus = false;
                    turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Effetto annullato" + "\u001B[0m");
                } else {
                    if (idM != effectMove2.getIdPawn()) {
                        artemisEffect = false;
                    } else {
                        Cell end = turno.getTable().getTableCell(effectMove2.getTargetX(), effectMove2.getTargetY());
                        if ((end.getX() == start.getX()) &&
                                (end.getY() == start.getY())) {
                            turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[31m" + "##Non puoi tornare indietro##" + "\u001B[0m");
                            artemisEffect = false;
                        } else {
                            turno.baseMovement(effectMove2);
                            turno.getValidationMove(turno.isValidationMove());
                            artemisEffect = turno.isValidationMove();
                            printerStatus = artemisEffect;
                            //if printStatus=true, the move is validate, send message and print table
                            if (artemisEffect) {
                                //broadcast message of movement
                                turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha mosso: " + turno.getMove().getIdPawn() +
                                        " in [" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
                                //print status of the table
                                turno.printTableStatusTurn(true);
                                turno.getMove().setIdPawn(effectMove2.getIdPawn());
                                turno.getMove().setTargetX(effectMove2.getTargetX());
                                turno.getMove().setTargetY(effectMove2.getTargetY());
                            }
                        }
                    }
                }
            } while (!artemisEffect && turno.getCount() < 5);
            turno.setValidationMove(true);
            turno.getMove().setIdPawn(idM);
        }


    }


    /**
     * Features added by card before its owner starts building
     *
     * @param turno the current turn
     */
    public void beforeOwnerBuilding(Turno turno) {
        //save the id pawn I use for the move
        //if (turno.isValidationMove()) {
        // turno.getMove().setIdPawn(getIdM());
        //}
    }

    /**
     * Features added by card after its owner starts building
     *
     * @param turno the current turn
     */
    public void afterOwnerBuilding(Turno turno) {
        if (turno.isValidationBuild()) {
            //broadcast message of building
            turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha costruito in: " +
                    "[" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
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
