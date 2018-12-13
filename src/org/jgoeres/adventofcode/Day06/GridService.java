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

    public static GridBoundary findGridBoundary(Grid grid) {
        GridBoundary gridBoundary = new GridBoundary();

        for (GridPoint gridPoint:grid.getGridPoints()) {
            // Process each grid point and find the min & max extents.

            Integer x = gridPoint.getX();
            Integer y = gridPoint.getY();

            // check left (min x)
            if ((gridBoundary.getTopLeft().getX() == null)
                    || x < gridBoundary.getTopLeft().getX()) {
                gridBoundary.getTopLeft().setX(x);
            }

            // check right (max x)
            if ((gridBoundary.getBottomRight().getX() == null)
                    || x > gridBoundary.getBottomRight().getX()) {
                gridBoundary.getBottomRight().setX(x);
            }

            // check top (min y)
            if ((gridBoundary.getTopLeft().getY() == null)
                    || y < gridBoundary.getTopLeft().getY()) {
                gridBoundary.getTopLeft().setY(y);
            }

            // check bottom (max y)
            if ((gridBoundary.getBottomRight().getY() == null)
                    || y > gridBoundary.getBottomRight().getY()) {
                gridBoundary.getBottomRight().setY(y);
            }
        }
        return gridBoundary;
    }

    public static boolean isOnBoundary(GridPoint gridPoint, GridBoundary gridBoundary) {
        boolean isOnBoundary = (gridPoint.getX() == gridBoundary.getTopLeft().getX())
                || (gridPoint.getX() == gridBoundary.getBottomRight().getX())
                || (gridPoint.getY() == gridBoundary.getTopLeft().getY())
                || (gridPoint.getY() == gridBoundary.getBottomRight().getY());
        return isOnBoundary;
    }

}
