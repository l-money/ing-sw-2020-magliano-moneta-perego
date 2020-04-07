package santorini.model;

import santorini.model.Gamer;

public class Artemis extends God {


    /*Salvare la cella di partenza per impedirne il ritorno*/
    public Artemis(Gamer gamer) {
        super(gamer);
    }

    /**
     * Initialize owner game variables
     * @param g player owner of card
     */
    public void initializeOwner(Gamer g) {
        this.owner.setSteps(2);
    }

    public void beforeOwnerMoving() {

    }

    public void afterOwnerMoving() {

    }

    public void beforeOwnerBuilding() {

    }

    public void afterOwnerBuilding() {

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
