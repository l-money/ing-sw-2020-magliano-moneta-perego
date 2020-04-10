package santorini.model;

import santorini.Turno;
import santorini.model.Gamer;

import java.io.Serializable;

public abstract class God implements Serializable {
    protected Gamer owner;
    protected String name, description;

    public God(Gamer gamer) {
        this.owner = gamer;
    }

    public Gamer getOwner() {
        return owner;
    }

    /**
     * Initialize player variables with card
     *
     * @param g player owner of card
     */
    public abstract void initializeOwner(Gamer g);

    /**
     * Features added by card before its owner does his moves
     */
    public abstract void beforeOwnerMoving(Turno turno);

    /**
     * Features added by card after its owner does his moves
     */
    public abstract void afterOwnerMoving(Turno turno);

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
     * @param other player to customize
     */
    public abstract void beforeOtherMoving(Gamer other);

    /**
     * Features added by card after other player does his moves
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
        return name.equalsIgnoreCase(god.name);
    }

    /**
     * Features added by card before other player starts building
     *
     * @param other player to customize
     */
    public abstract void afterOtherBuilding(Gamer other);

}
