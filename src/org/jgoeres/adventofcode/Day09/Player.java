package org.jgoeres.adventofcode.Day09;

public class Player {
    private Integer currentScore = 0;

    public Integer getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(Integer currentScore) {
        this.currentScore = currentScore;
    }

    public void addToCurrentScore(Integer pointsToAdd) {
        setCurrentScore(getCurrentScore()+pointsToAdd);
    }
}
