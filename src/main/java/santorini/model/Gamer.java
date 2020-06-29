package santorini.model;

import santorini.model.godCards.God;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Class Gamer
 */

public class Gamer {
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private String name;
    private God myCard;
    private int id;
    private int steps = 1;
    private int levelsUp = 1;
    private int levelsDown = 3;
    private int builds = 1;
    private boolean loser = false;
    private Pawn pawn0;
    private Pawn pawn1;
    private int idGamer;
    private Color colorGamer;
    private boolean winner = false;
    private Socket socket;

    /**
     * constructor of gamer
     *
     * @param socket socket of the gamer
     * @param name   of the gamer
     * @param id     number identification of the gamer
     * @param ois    ObjectInputStream of the gamer
     * @param oos    ObjectOutputStream of the gamer
     */
    public Gamer(Socket socket, String name, int id, ObjectInputStream ois, ObjectOutputStream oos) {
        this.socket = socket;
        this.name = name;
        this.id = id;
        this.outputStream = oos;
        this.inputStream = ois;
        pawn0 = new Pawn();
        pawn1 = new Pawn();
        switch (id) {
            case 0:
                this.colorGamer = Color.YELLOW;
                break;
            case 1:
                this.colorGamer = Color.RED;
                break;
            case 2:
                this.colorGamer = Color.BLUE;
                break;
            default:
                this.colorGamer = null;
                break;
        }
        pawn0.setIdPawn(0);
        pawn0.setIdGamer(this.id);
        pawn0.setColorPawn(getColorGamer());
        pawn0.setRow(-1);
        pawn0.setColumn(-1);
        pawn0.setPastLevel(0);
        pawn0.setPresentLevel(0);
        pawn1.setIdPawn(1);
        pawn1.setIdGamer(this.id);
        pawn1.setColorPawn(getColorGamer());
        pawn1.setRow(-1);
        pawn1.setColumn(-1);
        pawn1.setPastLevel(0);
        pawn1.setPresentLevel(0);
        myCard = null;
    }

    /**
     * method getOutputStream
     *
     * @return outputStream
     */
    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    /**
     * method getInputStream
     *
     * @return inputStream
     */
    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    /**
     * method getname
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * method setName
     *
     * @param name of the gamer
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * method getMyGodCard
     *
     * @return myCard
     */
    public God getMyGodCard() {
        return myCard;
    }

    /**
     * method setMyGodCard
     *
     * @param myCard the godcard of the gamer
     */
    public void setMyGodCard(God myCard) {
        this.myCard = myCard;
    }

    /**
     * method getId
     *
     * @return the identification number of the gamer
     */
    public int getId() {
        return id;
    }

    /**
     * method setId
     *
     * @param id the identification number of the gamer
     */
    public void setId(int id) {
        this.id = id;
        switch (id) {
            case 0:
                this.colorGamer = Color.YELLOW;
                break;
            case 1:
                this.colorGamer = Color.RED;
                break;
            case 2:
                this.colorGamer = Color.BLUE;
                break;
            default:
                this.colorGamer = null;
                break;
        }
    }

    /**
     * method getSteps
     *
     * @return steps
     */
    public int getSteps() {
        return steps;
    }

    /**
     * method setSteps
     *
     * @param steps of the gamer
     */
    public void setSteps(int steps) {
        this.steps = steps;
    }

    /**
     * method getLevelsUp
     *
     * @return levelsUp
     */
    public int getLevelsUp() {
        return levelsUp;
    }

    /**
     * method setLevelsUp
     *
     * @param levelsUp of the gamer
     */
    public void setLevelsUp(int levelsUp) {
        this.levelsUp = levelsUp;
    }

    /**
     * method getLevelsDown
     *
     * @return levelsDown
     */
    public int getLevelsDown() {
        return levelsDown;
    }

    /**
     * method setLevelsDown
     *
     * @param levelsDown of the gamer
     */
    public void setLevelsDown(int levelsDown) {
        this.levelsDown = levelsDown;
    }

    /**
     * method getLoser
     *
     * @return loser
     */
    public boolean getLoser() {
        return loser;
    }

    /**
     * method setLoser
     *
     * @param loser if the gamer can't move or build, he loses
     */

    public void setLoser(boolean loser) {
        this.loser = loser;
    }

    /**
     * method getBuilds
     *
     * @return builds
     */
    public int getBuilds() {
        return builds;
    }

    /**
     * method setBuilds
     *
     * @param builds of the gamer
     */
    public void setBuilds(int builds) {
        this.builds = builds;
    }

    /**
     * method getIdGamer
     *
     * @return idGamer
     */
    public int getIdGamer() {
        return idGamer;
    }

    /**
     * method setIdGamer
     *
     * @param idGamer of the gamer
     */
    public void setIdGamer(int idGamer) {
        this.idGamer = idGamer;
    }

    /**
     * method getColorGamer
     *
     * @return colorGamer
     */
    public Color getColorGamer() {
        return colorGamer;
    }

    /**
     * method isWinner
     *
     * @return winner
     */
    public boolean isWinner() {
        return winner;
    }

    /**
     * method setWinner
     *
     * @param winner if the gamer wins the game
     */
    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    /**
     * method getSocket
     *
     * @return socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * method setSocket
     *
     * @param socket of the gamer
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /**
     * method getIdPawn
     *
     * @param idPawn choose the number of the pawn, pawn1 and pawn2
     * @return pawn1 or pawn2
     */
    public Pawn getPawn(int idPawn) {
        switch (idPawn) {
            case 0:
                return pawn0;
            case 1:
                return pawn1;
            default:
                return null;
        }
    }

    /**
     * method setAPawn
     *
     * @param idPawn  the id of my pawn: pawn0 or pawn1
     * @param x       row of the pawn
     * @param y       column of the pawn
     * @param past    past level of the pawn
     * @param present present level of the pawn
     */
    public void setAPawn(int idPawn, int x, int y, int past, int present) {
        getPawn(idPawn).setRow(x);
        getPawn(idPawn).setColumn(y);
        getPawn(idPawn).setPastLevel(past);
        getPawn(idPawn).setPresentLevel(present);
    }

    /**
     * method otherPawn
     *
     * @param p my pawn
     * @return the other pawn
     */
    public Pawn otherPawn(Pawn p) {
        int i = p.getIdPawn();
        switch (i) {
            case 0:
                return pawn1;
            case 1:
                return pawn0;
            default:
                return null;
        }
    }

}

