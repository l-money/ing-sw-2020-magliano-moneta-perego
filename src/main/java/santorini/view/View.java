package santorini.view;

import santorini.NetworkHandlerClient;
import santorini.model.Mossa;
import santorini.model.Table;
import santorini.model.godCards.God;

import java.util.ArrayList;

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

    public God getGod() {
        return god;
    }

    public void setGod(God god) {
        this.god = god;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public synchronized void setTable(Table table) {
        this.table = table;
        counter++;
    }

    public void setHandlerClient(NetworkHandlerClient handlerClient) {
        this.handlerClient = handlerClient;
        listen = new Thread(handlerClient);
        listen.start();
    }

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

    public void disablePawn(int n, boolean enabled) {
        pawnEnabled[n] = enabled;
    }

    public Table getTable() {
        return table;
    }


    public abstract void chooseCards(ArrayList<God> gods);

    public abstract void setNewAction(Mossa.Action action);

    public abstract void setNumeroGiocatori();

    public abstract void setFailed(String msg);

    public abstract void printMessage(String msg);

    public abstract void setInitializePawn();

    public abstract void vittoria();

    public abstract void sconfitta(String winner);

    public abstract void networkError(String player);

    public abstract void printTable();

}
