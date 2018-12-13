package org.jgoeres.adventofcode.Day06;

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
        Grid grid = new Grid(500, 500);
//        Grid grid = new Grid(100, 100);

        for (GridPoint gridPoint : grid.getGridPoints()) {
            GridPoint closestGridPoint = GridService.closestGridPoint(gridPoint, centers);
//            if (closestGridPoint != null) {
//                System.out.println("[ " + gridPoint.getX() + ", " + gridPoint.getY()
//                        + " ]\tclosest to\t" + closestGridPoint.getName() + " [ " + closestGridPoint.getX() + ", " + closestGridPoint.getY()
//                        + " ]\tdistance\t" + GridService.manhattanDistance(gridPoint, closestGridPoint));
//            } else {
//                System.out.println("[ " + gridPoint.getX() + ", " + gridPoint.getY() + "\tclose to more than one center");
//            }
        }
    }

    public static void problem6B() {
        /*
        Problem Description
        */
        System.out.println("=== DAY 6B ===");

    }
}
