package org.jgoeres.adventofcode.Day12;

public class Pot {
    private Integer potId;
    private boolean hasPlant;

    public Pot(Integer potId, boolean hasPlant) {
        this.potId = potId;
        this.hasPlant = hasPlant;
    }

    public Integer getPotId() {
        return potId;
    }

    public boolean isHasPlant() {
        return hasPlant;
    }

}
