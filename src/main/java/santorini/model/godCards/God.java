package santorini.model.godCards;

import santorini.controller.Turno;
import santorini.model.Gamer;
import santorini.model.Mossa;
import santorini.model.Table;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class God
 */

public abstract class God implements Serializable {
    protected Gamer owner;
    protected String name, description;
    protected Table table;
    protected Mossa effectMove;
    protected ArrayList<Gamer> others;

    /**
     * constructor of God
     *
     * @param n    name of the god
     * @param desc description of the power of the god
     */

    public God(String n, String desc) {
        this.name = n;
        this.description = desc;
    }

    /**
     * method getOwner
     * @return the player
     */
    public Gamer getOwner() {
        return owner;
    }

    /**
     * method setOwner
     * @param newOwner .
     */
    public void setOwner(Gamer newOwner) {
        this.owner = newOwner;
    }

    /**
     * method setTable
     * @param table .
     */
    public void setTable(Table table) {
        this.table = table;
    }

    /**
     * method getName
     * @return the name of the god
     */
    public String getName() {
        return this.name;
    }

    /**
     * method getDescription
     * @return the description of the power of the god
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * method getEffectMove
     *
     * @return effect move
     */
    public Mossa getEffectMove() {
        return effectMove;
    }

    /**
     * method setOthers
     *
     * @param gamers .
     */
    public void setOthers(ArrayList<Gamer> gamers) {
        others = new ArrayList<>(gamers);
        others.remove(getOwner());
    }

    /**
     * method setEffectMove
     *
     * @param effectMove .
     */
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
