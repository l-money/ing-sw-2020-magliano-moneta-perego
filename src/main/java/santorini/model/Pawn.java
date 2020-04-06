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
    private int row;
    private int column;
    private int presentLevel;
    private int pastLevel;
    private Color color;

    /**
     * constructor of class Pawn
     */


    public Pawn() {
        this.presentLevel = 0;
        this.pastLevel = 0;

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

    /**
     * method setCellNotFree
     *
     * @param cell the new position of the pawn
     */
    public void setCellNotFree(Cell cell) {
        if ((cell.getX() == getRow()) && (cell.getY() == getColumn())) {
            cell.setFree(false);
        }
    }

}
