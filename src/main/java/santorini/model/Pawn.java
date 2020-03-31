package santorini.model;

import santorini.model.utils.Color;

import java.io.Serializable;

/**
 * Class Pawn
 * @author G. Perego
 */

public class Pawn implements Serializable {
    private int idPawn;
    private int idGamer;
    private int rowPawn;
    private int columnPawn;
    private int presentLevel;
    private int pastLevel;
    private Color color;

    /**
     * constructor of class Pawn
     * @param idPawn identification number of the pawn
     * @param idGamer identification number of the gamer
     * @param rowPawn row where the pawn is located
     * @param columnPawn column where the pawn is located
     * @param  presentLevel the present level of the building of the pawn
     * @param pastLevel the past level of the building of the pawn
     * @param color the team of the pawn
     */


    public Pawn(int idPawn, int idGamer, int rowPawn, int columnPawn, int presentLevel, int pastLevel, Color color) {
        this.idPawn = idPawn;
        this.idGamer = idGamer;
        this.rowPawn = rowPawn;
        this.columnPawn = columnPawn;
        this.presentLevel = presentLevel;
        this.pastLevel = pastLevel;
        this.color = color;
    }

    /**
     * method getIdPawn
     * @return the identification number of the pawn
     */

    public int getIdPawn(){
        return idPawn;
    }

    /**
     * method getIdGamer
     * @return the identification number of the gamer
     */

    public int getIdGamer(){
        return idGamer;
    }

    /**
     * method getPresentLevel
     * @return the present level of the pawn
     */

    public int getPresentLevel(){
        return presentLevel;
    }

    /**
     * method getPastLevel
     * @return the past level of the pawn
     */

    public int getPastLevel(){
        return pastLevel;
    }

    /**
     * method getColorPawn
     * @return the color (team) of the pawn
     */

    public Color getColorPawn(){
        return color;
    }

    /**
     * method getRowPawn
     * @return the row of the pawn
     */

    public int getRowPawn(){
        return rowPawn;
    }

    /**
     * method getColumnPawn
     * @return the column of the pawn
     */

    public int getColumnPawn(){
        return columnPawn;
    }

    /**
     * method setIdPawn
     * @param newIddPawn .
     */

    public void setIdPawn(int newIddPawn){
        idPawn = newIddPawn;

    }

    /**
     * method setIdGamer
     * @param newIdGamer .
     */

    public void setIdGamer (int newIdGamer){
        idGamer = newIdGamer;
    }

    /**
     * method setRowPaen
     * @param newRowPawn .
     */

    public void setRowPawn(int newRowPawn){
        rowPawn = newRowPawn;
    }

    /**
     * method setColumnPaw
     * @param newColumnPawn .
     */

    public void setColumnPawn(int newColumnPawn){
        columnPawn = newColumnPawn;
    }

    /**
     * method setPresentLevel
     * @param newPresentLevel .
     */

    public void setPresentLevel (int newPresentLevel){
        presentLevel = newPresentLevel;
    }

    /**
     * method setPastLevel
     * @param pastLevel .
     */

    public void setPastLevel(int pastLevel) {
        this.pastLevel = pastLevel;
    }

    /**
     * method setColorPawn
     * @param newColor .
     */

    public void setColorPawn (Color newColor){
        color = newColor;
    }

    /**
     * method positionPawn
     * @return the position[row][column] of the pawn
     */

    public int[] positionPawn(){
        int[] position;
        position = new int[2];
        position[0]= getRowPawn();
        position[1]= getColumnPawn();
        return position;
    }
}
