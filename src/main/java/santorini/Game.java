package santorini;

import santorini.model.God;

import java.util.ArrayList;

public class Game implements  Runnable{
    ArrayList<Gamer> giocatori = new ArrayList<Gamer>();
    ArrayList<God> godCards = new ArrayList<God>();

    /**
     * Initialize game with 2 players
     * @param gamer1 first player
     * @param gamer2 second player
     */
    public Game(Gamer gamer1, Gamer gamer2){
        giocatori.add(gamer1);
        giocatori.add(gamer2);
    }

    /**
     * Initialize game with 2 players
     * @param gamer1 first player
     * @param gamer2 second player
     * @param gamer3 third player
     */
    public Game(Gamer gamer1, Gamer gamer2, Gamer gamer3){
        giocatori.add(gamer1);
        giocatori.add(gamer2);
        giocatori.add(gamer3);
    }

    /**
     * Starts the game in new thread
     */
    public void startPlay(){
        new Thread(this).start();
    }

    public void run() {

    }
}
