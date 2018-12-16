package org.jgoeres.adventofcode.Day09;

public class Player {
    private Long currentScore = 0L;

    public Long getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(Long currentScore) {
        this.currentScore = currentScore;
    }

    public void addToCurrentScore(Integer pointsToAdd) {
        setCurrentScore(getCurrentScore() + pointsToAdd);
    }
}
