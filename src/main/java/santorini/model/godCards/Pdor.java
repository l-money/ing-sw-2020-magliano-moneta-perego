package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Gamer;
import santorini.model.God;

public class Pdor extends God {
    @Override
    public String getName() {
        return "Pdor";
    }

    @Override
    public String getDescription() {
        return "Io sono Pdor, figlio di Kmer, della tribù di Istar,\n" +
                "nella terra desolata dei Kfnir";
    }

    @Override
    public void initializeOwner(Gamer g) {
        g.setLevelsUp(1);

    }

    @Override
    public void beforeOwnerMoving(Turno turno) {
        turno.getGamer().setSteps(1);
    }

    @Override
    public void afterOwnerMoving(Turno turno) {
        turno.getGamer().setSteps(0);

    }

    @Override
    public void beforeOwnerBuilding(Turno turno) {
        turno.getGamer().setBuilds(1);

    }

    @Override
    public void afterOwnerBuilding(Turno turno) {
        turno.getGamer().setBuilds(0);

    }

    @Override
    public void beforeOtherMoving(Gamer other) {
        other.setSteps(1);

    }

    @Override
    public void afterOtherMoving(Gamer other) {
        other.setSteps(0);

    }

    @Override
    public void beforeOtherBuilding(Gamer other) {
        other.setBuilds(1);

    }

    @Override
    public void afterOtherBuilding(Gamer other) {
        other.setBuilds(0);

    }
}
