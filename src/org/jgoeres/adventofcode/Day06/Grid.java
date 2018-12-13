package org.jgoeres.adventofcode.Day06;

import java.util.ArrayList;

public class Grid {
    private ArrayList<GridPoint> gridPoints = new ArrayList<>();

    public Grid() {
    }

    public Grid(Integer width, Integer height) {
        for (Integer x = 0; x < width; x++) {
            for (Integer y = 0; y < height; y++) {
                GridPoint p = new GridPoint(x, y);
                getGridPoints().add(p);
            }
        }
    }

    public ArrayList<GridPoint> getGridPoints() {
        return gridPoints;
    }
}
