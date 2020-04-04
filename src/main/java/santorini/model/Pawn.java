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
    private Cell cell;
    private int presentLevel;
    private int pastLevel;
    private Color color;

    /**
     * constructor of class Pawn
     */


    public Pawn() {
        this.idPawn = idPawn;
        this.idGamer = idGamer;
        cell = new Cell();
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
     * method setIdPawn
     * @param newIddPawn the identification number of the pawn
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

    public void setPresentLevel (int newPresentLevel){
        presentLevel = newPresentLevel;
    }

    /**
     * method setPastLevel
     * @param newPastLevel .
     */

    public void setPastLevel(int newPastLevel) {
        pastLevel = newPastLevel;
    }

    /**
     * method setColorPawn
     * @param newColor .
     */

    public void setColorPawn (Color newColor){
        color = newColor;
    }

    /**
     * method getPawnCell
     *
     * @return the pawn's cell position
     */
    public Cell getPawnCell() {
        return cell;
    }

    /**
     * method setPawnNewCell
     *
     * @param newCell the new pawn's cell position
     */

    public void setPawnNewCell(Cell newCell) {
        cell = newCell;
    }

}
