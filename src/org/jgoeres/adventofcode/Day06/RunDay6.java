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
        On the other hand, if the coordinates are safe, maybe the best you can do
        is try to find a region near as many coordinates as possible.

        For example, suppose you want the sum of the Manhattan distance to all of
        the coordinates to be less than 32. For each location, add up the distances
        to all of the given coordinates; if the total of those distances is less
        than 32, that location is within the desired region. Using the same coordinates
        as above, the resulting region looks like this:

        ..........
        .A........
        ..........
        ...###..C.
        ..#D###...
        ..###E#...
        .B.###....
        ..........
        ..........
        ........F.

        In particular, consider the highlighted location 4,3 located at the
        top middle of the region. Its calculation is as follows, where abs()
        is the absolute value function:

        Distance to coordinate A: abs(4-1) + abs(3-1) =  5
        Distance to coordinate B: abs(4-1) + abs(3-6) =  6
        Distance to coordinate C: abs(4-8) + abs(3-3) =  4
        Distance to coordinate D: abs(4-3) + abs(3-4) =  2
        Distance to coordinate E: abs(4-5) + abs(3-5) =  3
        Distance to coordinate F: abs(4-8) + abs(3-9) = 10
        Total distance: 5 + 6 + 4 + 2 + 3 + 10 = 30

        Because the total distance to all coordinates (30) is less than 32,
        the location is within the region.

        This region, which also includes coordinates D and E, has a total size
        of 16.

        Your actual region will need to be much larger than this example,
        though, instead including all locations with a total distance of less
        than 10000.

        What is the size of the region containing all locations which have a
        total distance to all given coordinates of less than 10000?
        */
        System.out.println("=== DAY 6B ===");

        Centers centers = new Centers(pathToInputs);
        GridBoundary gridBoundary = GridService.findGridBoundary(centers);

        // Initialize a grid to hold everything.
        Grid grid = new Grid(gridBoundary);

        Integer numClosePoints = 0;
        Integer closeDistance = 10000;
        
        // Iterate through the whole grid, adding up all the distances for each point.
        for (GridPoint gridPoint : grid.getGridPoints()) {
            Integer totalDistance = 0;
            for (GridPoint center : centers.getGridPoints()) {
                // calculate the distance to every center point.
                totalDistance += GridService.manhattanDistance(gridPoint, center);
            }
            if (totalDistance < closeDistance) {
                // If this is a "close point" add it to the running total.
                numClosePoints = numClosePoints + 1;
            }
        }
        System.out.println("Number of points closer than " + closeDistance + " to all centers: " + numClosePoints);

        // Answer:
        // Number of points closer than 10000 to all centers: 35334
    }
}
