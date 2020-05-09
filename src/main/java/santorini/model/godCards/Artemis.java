package santorini.model.godCards;

import santorini.Turno;
import santorini.model.*;
//FINITA
public class Artemis extends God {
    private Cell start;
    private boolean artemisEffect;
    private Mossa effectMove2;
    private int idM;

    public Artemis() {
        super("Artemis", "Tuo spostamento: il tuo lavoratore può spostarsi una volta in più\n" +
                "ma non può tornare alla casella da cui è partito");
    }

    @Override
    public Mossa getEffectMove() {
        return effectMove2;
    }

    @Override
    public void setEffectMove(Mossa effectMove) {
        this.effectMove2 = effectMove;
    }

    public int getIdM() {
        return idM;
    }

    public void setIdM(int idM) {
        this.idM = idM;
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
     * @param turno the current turn
     */
    public void beforeOwnerMoving(Turno turno) {
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
            setIdM(turno.getMove().getIdPawn());
            turno.printTableStatusTurn(turno.isValidationMove());
            artemisEffect = false;
            turno.setCount(0);
            turno.getGamer().setSteps(1);
            do {
                effectMove2 = turno.moveRequest();
                if (turno.nullEffectForGodCards(effectMove2)) {
                    artemisEffect = true;
                } else {
                    if (idM != effectMove2.getIdPawn()) {
                        //TODO errore in Artemide
                        // Se il giocatore cambia pedina e poi decide di non muovere più
                        // la view si è salvato ID dell'altra pedina
                        // quindi fa fare solo costruzioni nelle celle adiacenti all'altra pedina
                        artemisEffect = false;
                    } else {
                        Cell end = turno.getTable().getTableCell(effectMove2.getTargetX(), effectMove2.getTargetY());
                        if ((end.getX() == start.getX()) &&
                                (end.getY() == start.getY())) {
                            artemisEffect = false;
                        } else {
                            turno.baseMovement(effectMove2);
                            turno.getValidationMove(turno.isValidationMove());
                            artemisEffect = turno.isValidationMove();
                        }
                    }
                }
                if (!artemisEffect) {
                    turno.sendFailed();
                }
                //else the movement is correct and artemisEffect is true
                //until the movement is correct, the gamer have to insert the correct movement
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
        if (turno.isValidationMove()) {
            turno.getMove().setIdPawn(getIdM());
        }
    }

    /**
     * Features added by card after its owner starts building
     *
     * @param turno the current turn
     */
    public void afterOwnerBuilding(Turno turno) {
        turno.getGamer().setSteps(1);
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
