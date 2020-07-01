package santorini.view;

import santorini.network.NetworkHandlerClient;
import santorini.model.Mossa;
import santorini.model.Table;
import santorini.model.godCards.God;

import java.util.ArrayList;

/**
 * Class View
 */

public abstract class View {
    protected int ID, counter = 0, currentPawn;
    protected Table table;
    protected boolean inTurno = false;
    protected boolean[] pawnEnabled = new boolean[2];
    protected Thread listen;
    protected NetworkHandlerClient handlerClient;
    protected String name;
    protected God god;
    protected boolean effetto = true;

    /**
     * method getGod
     *
     * @return god card
     */
    public God getGod() {
        return god;
    }

    /**
     * method setGod
     *
     * @param god card
     */
    public void setGod(God god) {
        this.god = god;
    }

    /**
     * method setName
     *
     * @param name .
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * method getName
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * method getID
     *
     * @return ID
     */
    public int getID() {
        return ID;
    }

    /**
     * method setID
     *
     * @param ID .
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * method setTable
     *
     * @param table .
     */
    public synchronized void setTable(Table table) {
        this.table = table;
        counter++;
    }

    /**
     * method setHandlerClient
     *
     * @param handlerClient .
     */
    public void setHandlerClient(NetworkHandlerClient handlerClient) {
        this.handlerClient = handlerClient;
        listen = new Thread(handlerClient);
        listen.start();
    }

    /**
     * method switchCurrentPawn
     */
    public void switchCurrentPawn() {
        switch (currentPawn) {
            case 0:
                currentPawn = 1;
                break;
            default:
                currentPawn = 0;
        }
    }

    public int getCurrentPawn() {
        return currentPawn;
    }

    public NetworkHandlerClient getHandlerClient() {
        return handlerClient;
    }

    /**
     * method disablePawn
     *
     * @param n       id pawn
     * @param enabled boolean
     */
    public void disablePawn(int n, boolean enabled) {
        pawnEnabled[n] = enabled;
    }

    /**
     * method getTable
     * @return table
     */
    public Table getTable() {
        return table;
    }

    /**
     * method chooseCards
     * @param gods god cards
     */
    public abstract void chooseCards(ArrayList<God> gods);

    /**
     * method setNewAction
     * @param action my action
     */
    public abstract void setNewAction(Mossa.Action action);

    /**
     * method setNumeroGiocatori
     */
    public abstract void setNumeroGiocatori();

    /**
     * method setFailed
     * @param msg message
     */
    public abstract void setFailed(String msg);

    /**
     * method printMessage
     * @param msg message
     */
    public abstract void printMessage(String msg);

    /**
     * method setInitializePawn
     */
    public abstract void setInitializePawn();

    /**
     * method vittoria
     */
    public abstract void vittoria();

    /**
     * method sconfitta
     * @param winner .
     */
    public abstract void sconfitta(String winner);

    /**
     * method networkError
     * @param player .
     */
    public abstract void networkError(String player);

    /**
     * method printTable
     */
    public abstract void printTable();

}
