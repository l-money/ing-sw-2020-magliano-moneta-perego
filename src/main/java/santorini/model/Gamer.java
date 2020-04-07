package santorini.model;

import santorini.model.utils.Color;


public class Gamer {
    private String name;
    private God mycard;
    private int steps = 1;
    private int levels_up = 1;
    private int level_down = 5;
    private boolean overwrite = false;
    private int builds = 1;
    private Pawn[] pawn;
    private int idGamer;
    private Color colorGamer;
    private boolean winner = false;

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public int getSteps() {
        return steps;
    }

    public int getLevels_up() {
        return levels_up;
    }

    public int getLevel_down() {
        return level_down;
    }

    public boolean isOverwrite() {
        return overwrite;
    }

    public int getBuilds() {
        return builds;
    }

    public God getMycard() {
        return mycard;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void setLevels_up(int levels_up) {
        this.levels_up = levels_up;
    }

    public void setLevel_down(int level_down) {
        this.level_down = level_down;
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }

    public void setBuilds(int builds) {
        this.builds = builds;
    }

    public void setGod(God newGodCard) {
        this.mycard = newGodCard;
        mycard.initializeOwner(this);
    }

    public God getGod() {
        return mycard;
    }

    /**
     * method getIdGamer
     *
     * @return the identification number of the gamer
     */
    public int getIdGamer() {
        return idGamer;
    }

    /**
     * method setIdGamer
     *
     * @param newIdGamer the identification number of the gamer
     */
    public void setIdGamer(int newIdGamer) {
        this.idGamer = newIdGamer;
    }

    /**
     * method getColorGamer
     *
     * @return the color (team) of the gamer
     */
    public Color getColorGamer() {
        return colorGamer;
    }

    /**
     * method setIdColorGamer
     *
     * @param newColorGamer It sets the color (team) of the gamer
     */
    public void setColorGamer(Color newColorGamer) {
        this.colorGamer = newColorGamer;
    }

    /**
     * method getIdPawn
     *
     * @param idPawn choose the number of the pawn, pawn1 and pawn2
     * @return pawn1 or pawn2
     */
    public Pawn getIdPawn(int idPawn) {
        return pawn[idPawn];
    }

    /**
     * method setPawn
     *
     * @param idGamer    the number identification of the gamer
     * @param colorGamer the color (team) of the gamer
     *                   It sets the number identification and the color of the two pawns of the gamer
     */
    public void setPawn(int idGamer, Color colorGamer) {
        pawn = new Pawn[2];
        for (int i = 0; i < 2; i++) {
            pawn[i].setIdPawn(i);
            pawn[i].setIdGamer(idGamer);
            pawn[i].setColorPawn(colorGamer);
        }
    }


    /*public void moveMyPawn(Pawn pedina, Cell to) {
        mycard.beforeOwnerMoving();
        baseMove(pedina, to);
        mycard.afterOwnerMoving();
    }

    public void buildBrick(Cell on) {
        mycard.afterOwnerBuilding();
        baseBuild(on);
        mycard.beforeOwnerBuilding();
    }
*/
    /**
     * Moves player pawn from cell to another
     * @param pedina pawn to move
     * @param to target cell
     */
    /*private void baseMove(Pawn pedina, Cell to){
        Cell from = pedina.getCell();
        //Controllo che to sia tra le 8 attorno a from
        //Controlla Distanza in passi tra le 2 celle e compara con steps
        if(to.getLevel()-from.getLevel()<=levels_up || from.getLevel()-to.getLevel()<=level_down){
            //Livello inacessibile con le permission correnti
        }
        if(!to.isFree() && !overwrite){
            //cella non libera e non si può sovrascrivere
        }
    }*/

    /**
     * Builds a new block upon a cell
     *
     * @param on cell to build
     * @return operation validation
     */
    private boolean baseBuild(Cell on) {
        if (on.isFree() && !on.isComplete()) {
            /*Metodo build ancora mancante
            on.build();*/
            return true;
        }
        return false;
    }

}
