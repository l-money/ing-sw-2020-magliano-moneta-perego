package santorini.model;

import santorini.Turno;
import santorini.model.Gamer;

public class Atlas extends God {


    /*Salvare la cella di partenza per impedirne il ritorno*/
    public Atlas(Gamer gamer) {
        super(gamer);
    }

    /**
     * Initialize owner game variables
     *
     * @param g player owner of card
     */
    public void initializeOwner(Gamer g) {
    }

    public void beforeOwnerMoving(Turno turno) {

    }

    public void afterOwnerMoving(Turno turno) {

    }

    public void beforeOwnerBuilding(Turno turno) {

    }

    public void afterOwnerBuilding(Turno turno) {

    }

    public void beforeOtherMoving(Gamer other) {

    }

    public void afterOtherMoving(Gamer other) {

    }

    public void beforeOtherBuilding(Gamer other) {

    }

    public void afterOtherBuilding(Gamer other) {

    }

}

