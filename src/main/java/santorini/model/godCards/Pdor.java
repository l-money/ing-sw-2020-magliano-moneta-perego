package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Gamer;
import santorini.model.God;
//P
public class Pdor extends God {

    public Pdor() {
        super("Pdor", "Io sono Pdor, figlio di Kmer, della tribù di Istar,\n" +
                "nella terra desolata dei Kfnir");
    }

    @Override
    public void initializeOwner(Gamer g) {


    }

    @Override
    public void beforeOwnerMoving(Turno turno) {

    }

    @Override
    public void afterOwnerMoving(Turno turno) {

    }

    @Override
    public void beforeOwnerBuilding(Turno turno) {

    }

    @Override
    public void afterOwnerBuilding(Turno turno) {

    }

    @Override
    public void beforeOtherMoving(Gamer other) {


    }

    @Override
    public void afterOtherMoving(Gamer other) {


    }

    @Override
    public void beforeOtherBuilding(Gamer other) {


    }

    @Override
    public void afterOtherBuilding(Gamer other) {


    }
}
