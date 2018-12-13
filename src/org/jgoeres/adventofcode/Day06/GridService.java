package org.jgoeres.adventofcode.Day06;

public abstract class GridService {
    public static Integer manhattanDistance(GridPoint p1, GridPoint p2) {
        Integer xDist = Math.abs(p2.getX() - p1.getX());
        Integer yDist = Math.abs(p2.getY() - p1.getY());

        return (xDist + yDist);
    }

    public static GridPoint closestGridPoint(GridPoint reference, Centers centers) {
        GridPoint closest = null;
        Integer closestDistance = null;
        Integer numClosest = 0;

        for (GridPoint gridPoint : centers.getGridPoints()) {
            Integer distance = manhattanDistance(reference, gridPoint);

            if ((closestDistance == null)
                    || (distance < closestDistance)) {
                //new closest point, or this is the first one we've checked
                closest = gridPoint; // store this point as the closest one so far
                closestDistance = distance;
                numClosest = 1;
            } else if (distance == closestDistance) {
                // this is at least a second point the same distance away.
                numClosest = numClosest + 1; // increase our count
            }
        }
        // After checking all the points in the centers,
        // see if we have a valid result.

        // Is there only ONE closest point?
        if (numClosest == 1) {
            return closest;
        } else { // there is no single closest point
            return null;
        }
    }
}
