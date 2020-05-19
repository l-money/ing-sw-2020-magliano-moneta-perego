package santorini.model;

import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Class Pawn
 * @author G. Perego
 */

public class Pawn implements Serializable {
    private int idPawn;
    private int idGamer;
    private int row;
    private int column;
    private int presentLevel;
    private int pastLevel;
    private Color color;
    private boolean iCanPlay;

    /**
     * constructor of class Pawn
     */


    public Pawn() {
        this.idPawn = -2;
        this.idGamer = -2;
        //this.row = -2;
        //this.column = -2;
        this.presentLevel = 0;
        this.pastLevel = 0;
        this.color = null;
        this.iCanPlay = true;

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
     * method getICanPlay
     *
     * @return true if the pawn can move or build, else false
     */

    public boolean getICanPlay() {
        return iCanPlay;
    }

    /**
     * method setICanPlay
     *
     * @param iCanPlay boolean tells to me if my pawn can move
     */

    public void setICanPlay(boolean iCanPlay) {
        this.iCanPlay = iCanPlay;
    }

    /**
     * method setIdPawn
     * @param newIdPawn the identification number of the pawn
     */


    public void setIdPawn(int newIdPawn) {
        idPawn = newIdPawn;
    }

    /**
     * method setIdGamer
     * @param newIdGamer .
     */

    public void setIdGamer (int newIdGamer){
        idGamer = newIdGamer;
        switch (idGamer) {
            case 0:
                setColorPawn(Color.YELLOW);
                break;
            case 1:
                setColorPawn(Color.RED);
                break;
            case 2:
                setColorPawn(Color.BLUE);
                break;
            default:
                setColorPawn(null);
                break;
        }

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
     * method getRow
     * @return the row position of the pawn
     */
    public int getRow() {
        return row;
    }

    /**
     * method getColumn
     *
     * @return the column position of the pawn
     */
    public int getColumn() {
        return column;
    }

    /**
     * method setRow
     * @param newRow the new row position of the pawn
     */
    public void setRow(int newRow) {
        row = newRow;
    }

    /**
     * method setColumn
     * @param newColumn the new column position of the pawn
     */
    public void setColumn(int newColumn) {
        column = newColumn;
    }

}
