package org.jgoeres.adventofcode.Day06;

import java.util.Map;

public class RunDay6 {
    static String pathToInputs = "day06/input.txt";

    public static void problem6A() {
        /*
            Using only the Manhattan distance, determine the area around each coordinate
            by counting the number of integer X,Y locations that are closest to that
            coordinate (and aren't tied in distance to any other coordinate).

            Your goal is to find the size of the largest area that isn't infinite.

         */
        System.out.println("=== DAY 6A ===");

        Centers centers = new Centers(pathToInputs);
        GridBoundary gridBoundary = GridService.findGridBoundary(centers);

        // Initialize a grid to hold everything.
        Grid grid = new Grid(gridBoundary);


        for (GridPoint gridPoint : grid.getGridPoints()) {
            GridPoint closestGridPoint = GridService.closestGridPoint(gridPoint, centers);
//            if (closestGridPoint != null) {
//                System.out.println("[ " + gridPoint.getX() + ", " + gridPoint.getY()
//                        + " ]\tclosest to\t" + closestGridPoint.getName() + " [ " + closestGridPoint.getX() + ", " + closestGridPoint.getY()
//                        + " ]\tdistance\t" + GridService.manhattanDistance(gridPoint, closestGridPoint));
//            } else {
//                System.out.println("[ " + gridPoint.getX() + ", " + gridPoint.getY() + "\tclose to more than one center");
//            }
            if (closestGridPoint != null) {
                // set the name of this point to the closest center.
                Character closestName = closestGridPoint.getName();
                gridPoint.setName(closestName);

                if (GridService.isOnBoundary(gridPoint, gridBoundary)) {
                    // But if we're at the edge of the boundary, then this area is
                    // infinite. So also remove this center
                    // from the centersMap, so we don't try to count it later.
                    centers.getCenterCountsMap().remove(closestName);
                }

                // If we're on a non-infinite area (as far as we know), add this point to the count for that area
                if (centers.getCenterCountsMap().containsKey(closestName)) {
                    Integer currentCount = centers.getCenterCountsMap().get(closestName);
                    centers.getCenterCountsMap().put(closestName, currentCount + 1);
                }
            }
        }

        // Traverse the grid one more time, and count up the size of the areas
        // remaining in the Map (they're the non-infinite ones).

        Map.Entry<Character, Integer> maxCenter = null;
        for (Map.Entry<Character, Integer> centerEntry : centers.getCenterCountsMap().entrySet()) {
            if (maxCenter == null || centerEntry.getValue().compareTo(maxCenter.getValue()) > 0) {
                maxCenter = centerEntry;
            }
        }

        System.out.println("Center \"" + maxCenter.getKey() + "\" has a size of " + maxCenter.getValue());
        // Answer: Center "-" has a size of 5333
    }

    public static void problem6B() {
        /*
        Problem Description
        */
        System.out.println("=== DAY 6B ===");

    }
}
