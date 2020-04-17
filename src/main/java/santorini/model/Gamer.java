package santorini.model;

import java.awt.*;

import java.net.Socket;


public class Gamer {
    private String name;
    private God mycard;
    private int id;
    private int steps = 1;
    private int levelsUp = 1;
    private int levelsDown = 3;
    private boolean looser = false;
    private int builds = 1;
    private Pawn[] pawn;
    private int idGamer;
    private Color colorGamer;
    private boolean winner = false;
    private Socket socket;

    public Gamer(Socket socket, String name, int id) {
        this.socket = socket;
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean isWinner() {
        return winner;
    }
    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public int getSteps() {
        return steps;
    }
    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getLevelsUp() {
        return levelsUp;
    }

    public void setLevelsUp(int newLevelsUp) {
        this.levelsUp = newLevelsUp;
    }

    public int getLevelsDown() {
        return levelsDown;
    }

    public void setLevelsDown(int newLevelsDown) {
        this.levelsDown = newLevelsDown;
    }

    public boolean isLooser() {
        return looser;
    }

    public void setLooser(boolean looser) {
        this.looser = looser;
    }


    public God getMyGodCard() {
        return mycard;
    }

    public void setGod(God newGodCard) {
        this.mycard = newGodCard;
        mycard.initializeOwner(this);
    }

    public int getBuilds() {
        return builds;
    }

    public void setBuilds(int builds) {
        this.builds = builds;
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

    public Pawn[] getmyPawn() {
        return pawn;
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
        for (int i = 0; i < 2; i++) {
            this.pawn[i].setIdPawn(i);
            this.pawn[i].setIdGamer(idGamer);
            this.pawn[i].setColorPawn(colorGamer);
            this.pawn[i].setPastLevel(0);
            this.pawn[i].setPresentLevel(0);
        }
    }

}

