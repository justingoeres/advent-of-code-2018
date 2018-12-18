package org.jgoeres.adventofcode.Day12;

public class Pot {
    private Integer potId;
    private boolean hasPlant;
    private Pot nextPot;
    private Pot prevPot;

    public Pot(Integer potId, boolean hasPlant) {
        this.potId = potId;
        this.hasPlant = hasPlant;
    }

    public Integer getPotId() {
        return potId;
    }

    public void setPotId(Integer potId) {
        this.potId = potId;
    }

    public boolean isHasPlant() {
        return hasPlant;
    }

    public void setHasPlant(boolean hasPlant) {
        this.hasPlant = hasPlant;
    }

    public Pot getNextPot() {
        return nextPot;
    }

    public void setNextPot(Pot nextPot) {
        this.nextPot = nextPot;
    }

    public Pot getPrevPot() {
        return prevPot;
    }

    public void setPrevPot(Pot prevPot) {
        this.prevPot = prevPot;
    }
}
