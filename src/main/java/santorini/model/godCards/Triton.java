package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Cell;
import santorini.model.Gamer;
import santorini.model.Mossa;

import java.util.ArrayList;

public class Triton extends God {
    private Cell myCell;
    private Cell start;
    private boolean tritonEffect;
    private boolean printStatus;
    private Mossa tritonMove;
    private int idM;

    public Triton() {
        super("Triton", "Tuo spostamento: ogni volta che il tuo lavoratore si sposta su una casella perimentrale\n" +
                "(terreno o blocco), può subito spostarsi di nuovo");
    }

    /**
     * method getEffectMove
     *
     * @return move
     */
    @Override
    public Mossa getEffectMove() {
        return tritonMove;
    }

    /**
     * method setEffectMove
     *
     * @param effectMove move
     */
    @Override
    public void setEffectMove(Mossa effectMove) {
        this.tritonMove = effectMove;
    }

    /**
     * method getIdM
     *
     * @return the id pawn of the movement
     */
    public int getIdM() {
        return idM;
    }

    /**
     * method setIdM
     *
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
    @Override
    public void initializeOwner(Turno turno) {

    }

    /**
     * Features added by card before its owner does his moves
     *
     * @param turno
     */
    @Override
    public void beforeOwnerMoving(Turno turno) {
        tritonEffect = false;
        printStatus = true;
        idM = turno.getMove().getIdPawn();
        int startX = turno.getGamer().getPawn(idM).getRow();//save start position X
        int startY = turno.getGamer().getPawn(idM).getColumn();//save start position Y
        start = turno.getTable().getTableCell(startX, startY);

    }

    /**
     * Features added by card after its owner does his moves
     *
     * @param turno current turn
     */
    @Override
    public void afterOwnerMoving(Turno turno) {
        if (turno.isValidationMove()) {
            //broadcast message of movement
            turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha mosso: " + turno.getMove().getIdPawn() +
                    " in [" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
            //print status of the table
            turno.printTableStatusTurn(printStatus);
            printStatus = false;
            int x = turno.getMove().getTargetX();
            int y = turno.getMove().getTargetY();
            myCell = turno.getTable().getTableCell(x, y);
            ArrayList<Cell> perimetralCells = turno.getTable().tablePerimetralCells(turno.getTable());
            if (perimetralCells.contains(myCell)) {
                tritonEffect = false;
                turno.setCount(0);
                do {
                    setIdM(turno.getMove().getIdPawn());
                    turno.getGamer().setSteps(1);
                    turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "\"Sei su una casella perimetrale.\n" +
                            "Hai Triton, puoi muoverti una volta in più.\n" +
                            "Se non vuoi muoverti scegli l'opzione 'No'." + "\u001B[0m");
                    tritonMove = turno.moveRequest();
                    if (turno.nullEffectForGodCards(tritonMove)) {
                        tritonEffect = true;
                        printStatus = false;
                        turno.getGameHandler().sendMessage(turno.getGamer(), "\u001B[34m" + "Effetto annullato" + "\u001B[0m");
                    } else if (idM != tritonMove.getIdPawn()) {
                        tritonEffect = false;
                        turno.getValidation(false);
                    } else {
                        Cell end = turno.getTable().getTableCell(tritonMove.getTargetX(), tritonMove.getTargetY());
                        if ((end.getX() == start.getX()) &&
                                (end.getY() == start.getY())) {
                            turno.getGameHandler().sendFailed(turno.getGamer(), "##Non puoi tornare indietro##");
                            tritonEffect = false;
                            turno.getValidation(false);
                        } else {
                            turno.baseMovement(tritonMove);
                            turno.getValidation(turno.isValidationMove());
                            tritonEffect = turno.isValidationMove();
                            printStatus = tritonEffect;
                            if (tritonEffect) {
                                //broadcast message of movement
                                turno.getGameHandler().getGame().broadcastMessage(turno.getGamer().getName() + " ha mosso: " + turno.getMove().getIdPawn() +
                                        " in [" + turno.getMove().getTargetX() + "," + turno.getMove().getTargetY() + "]");
                                //print status of the table
                                turno.printTableStatusTurn(true);
                                turno.getMove().setIdPawn(tritonMove.getIdPawn());
                                turno.getMove().setTargetX(tritonMove.getTargetX());
                                turno.getMove().setTargetY(tritonMove.getTargetY());
                            }
                        }
                    }
                } while (!tritonEffect && turno.getCount() < 5);
                turno.setValidationMove(true);
                turno.getMove().setIdPawn(idM);
            }
        }
    }


    /**
     * Features added by card before its owner starts building
     *
     * @param turno
     */
    @Override
    public void beforeOwnerBuilding(Turno turno) {

    }

    /**
     * Features added by card after its owner starts building
     *
     * @param turno
     */
    @Override
    public void afterOwnerBuilding(Turno turno) {
        if (turno.isValidationBuild()) {
            //broadcast message of building
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
    @Override
    public void beforeOtherMoving(Gamer other) {

    }

    /**
     * Features added by card after other player does his moves
     *
     * @param other player to customize
     */
    @Override
    public void afterOtherMoving(Gamer other) {

    }

    /**
     * Features added by card before other player starts building
     *
     * @param other player to customize
     */
    @Override
    public void beforeOtherBuilding(Gamer other) {

    }

    /**
     * Features added by card before other player starts building
     *
     * @param other player to customize
     */
    @Override
    public void afterOtherBuilding(Gamer other) {

    }
}
