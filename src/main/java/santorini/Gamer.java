package santorini;

import santorini.model.God;


public class Gamer {
    private God mycard;
    private int steps = 1;
    private int levels_up = 1;
    private int level_down = 5;
    private boolean overwrite = false;
    private int builds = 1;

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void setLevels_up(int levels_up) {
        this.levels_up = levels_up;
    }

    public void setLevel_down(int level_down) {
        this.level_down = level_down;
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }

    public void setBuilds(int builds) {
        this.builds = builds;
    }
}
