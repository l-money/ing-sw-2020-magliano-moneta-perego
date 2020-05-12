package santorini.model.godCards;

import santorini.Turno;
import santorini.model.Gamer;
import santorini.model.Mossa;
import santorini.model.Table;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class God implements Serializable {
    protected Gamer owner;
    protected String name, description;
    protected Table table;
    protected Mossa effectMove;
    protected ArrayList<Gamer> others;

    //TODO rivedere i messaggi per le divinità
    public God(String n, String desc) {
        this.name = n;
        this.description = desc;
    }

    public Gamer getOwner() {
        return owner;
    }

    public void setOwner(Gamer newOwner) {
        this.owner = newOwner;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Mossa getEffectMove() {
        return effectMove;
    }

    public void setOthers(ArrayList<Gamer> gamers) {
        others = new ArrayList<>(gamers);
        others.remove(getOwner());
    }

    public void setEffectMove(Mossa effectMove) {
        this.effectMove = effectMove;
    }

    /**
     * Initialize player variables with card
     *
     * @param turno player owner of card
     */
    public abstract void initializeOwner(Turno turno);

    /**
     * Features added by card before its owner does his moves
     */
    public abstract void beforeOwnerMoving(Turno turno);

    /**
     * Features added by card after its owner does his moves
     */
    public abstract void afterOwnerMoving(Turno turno) throws InterruptedException;

    /**
     * Features added by card before its owner starts building
     */
    public abstract void beforeOwnerBuilding(Turno turno);

    /**
     * Features added by card after its owner starts building
     */
    public abstract void afterOwnerBuilding(Turno turno);

    /**
     * Features added by card before other player does his moves
     *
     * @param other player to customize
     */
    public abstract void beforeOtherMoving(Gamer other);

    /**
     * Features added by card after other player does his moves
     *
     * @param other player to customize
     */
    public abstract void afterOtherMoving(Gamer other);

    /**
     * Features added by card before other player starts building
     *
     * @param other player to customize
     */
    public abstract void beforeOtherBuilding(Gamer other);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof God)) return false;
        God god = (God) o;
        return name.equalsIgnoreCase(god.getName());
    }

    /**
     * Features added by card before other player starts building
     *
     * @param other player to customize
     */
    public abstract void afterOtherBuilding(Gamer other);

}
