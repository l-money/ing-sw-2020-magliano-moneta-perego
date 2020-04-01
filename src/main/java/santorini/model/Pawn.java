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
    private int presentLevel;
    private int pastLevel;
    private Color color;
    private Cell position;

    /**
     * constructor of class Pawn
     * @param idPawn identification number of the pawn
     * @param idGamer identification number of the gamer
     * @param position the position of the pawn
     * @param  presentLevel the present level of the building of the pawn
     * @param pastLevel the past level of the building of the pawn
     * @param color the team of the pawn
     */


    public Pawn(int idPawn, int idGamer, Cell position, int presentLevel, int pastLevel, Color color) {
        this.idPawn = idPawn;
        this.idGamer = idGamer;
        this.position = position;
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
     * method getPositionPawn
     * @return the position (cell) of the pawn
     */
    public Cell getPositionPawn() {
        return position;
    }

    /**
     * method setPositionPawn
     *
     * @param newCell the new position (cell) of the pawn
     */

    public void setPositionPawn(Cell newCell) {
        position.setX(newCell.getX());
        position.setY(newCell.getY());
    }

}
