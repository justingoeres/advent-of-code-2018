package org.jgoeres.adventofcode.Day06;

public class GridBoundary {
    private GridPoint topLeft = new GridPoint(); // minimums
    private GridPoint bottomRight = new GridPoint(); // maximums

    public GridPoint getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(GridPoint topLeft) {
        this.topLeft = topLeft;
    }

    public GridPoint getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(GridPoint bottomRight) {
        this.bottomRight = bottomRight;
    }
}
