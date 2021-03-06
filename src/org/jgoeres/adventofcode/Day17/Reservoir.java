package org.jgoeres.adventofcode.Day17;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reservoir {
    HashSet<XYPair> wallCells = new HashSet<>();
    HashSet<XYPair> waterCells = new HashSet<>();
    XYPair TopLeft = new XYPair(Integer.MAX_VALUE, Integer.MAX_VALUE);
    XYPair BottomRight = new XYPair(Integer.MIN_VALUE, Integer.MIN_VALUE);
    XYPair waterSource = new XYPair(500, 0); // per problem statement

    Stack<XYPair> waterCellStack = new Stack<>();

    private static final boolean DEBUG_PRINT_RESERVOIR_ON_LOAD = false;
    private static final boolean DEBUG_PRINT_ONLY_WATER = false;
    private static final boolean DEBUG_PRINT_AT_BOTTOM = false;


    public Reservoir(String pathToFile) {

        waterCellStack.push(waterSource);
        loadReservoir(pathToFile);
    }

    public Stack<XYPair> getWaterCellStack() {
        return waterCellStack;
    }

    private void loadReservoir(String pathToFile) {
        /*
        File looks like:
            x=399, y=453..458
            x=557, y=590..599
            y=1182, x=364..369
         */

        Pattern hPattern = Pattern.compile("^y.+"); // y is constant, run is in x.
        Pattern vPattern = Pattern.compile("^x.+"); // x is constant, run is in y.

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {    // Keep reading until the end of the file.
                Matcher hMatcher = hPattern.matcher(line);
                Matcher vMatcher = vPattern.matcher(line);

                if (hMatcher.matches()) { // If this is a HORIZONTAL wall.
                    try (Scanner sc = new Scanner(line)) {
                        sc.useDelimiter("\\D+");
                        int y = sc.nextInt();
                        int xMin = sc.nextInt();
                        int xMax = sc.nextInt();

                        for (int x = xMin; x <= xMax; x++) {
                            addAndCheckExtents(x, y);
                        }

                    }
                }
                if (vMatcher.matches()) { // If this is a VERTICAL wall.
                    try (Scanner sc = new Scanner(line)) {
                        sc.useDelimiter("\\D+");
                        int x = sc.nextInt();
                        int yMin = sc.nextInt();
                        int yMax = sc.nextInt();

                        for (int y = yMin; y <= yMax; y++) {
                            addAndCheckExtents(x, y);

                        }
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }

        if (DEBUG_PRINT_RESERVOIR_ON_LOAD) {
            printReservoir();
        }
        return;
    }

    private void addAndCheckExtents(int x, int y) {
        wallCells.add(new XYPair(x, y));

        // Check the x extents
        if (x < TopLeft.getX()) {
            TopLeft.setX(x);
        }
        if (x > BottomRight.getX()) {
            BottomRight.setX(x);
        }

        // Check the y extents
        if (y < TopLeft.getY()) {
            TopLeft.setY(y);
        }
        if (y > BottomRight.getY()) {
            BottomRight.setY(y);
        }

    }

    public void processWaterStack() {
        XYPair waterCell = waterCellStack.pop();

        if (waterCell.getY() > BottomRight.getY()) {
            // If we've run off the bottom of the scan...
            // Go back up the stack to see if there are any
            // "down" flows that haven't been processed.

            if (DEBUG_PRINT_AT_BOTTOM) {
                printReservoir();
            }

            XYPair previousWaterCell = waterCell.cellAbove();
            while (!waterCellStack.isEmpty() && !isEmpty(previousWaterCell)) {
                // find the first empty WaterCell on the stack, going backward.
                if (waterCellStack.isEmpty()) {
                    return; // If we run out of stack items, we're done!
                } else {
                    previousWaterCell = waterCellStack.pop(); // otherwise keep going up the stack until we find an empty cell.
                }
            }
            // Then continue on and process it.
            waterCell = previousWaterCell;
        }

        waterCells.add(waterCell);

        if (DEBUG_PRINT_ONLY_WATER) {
            printReservoir();
        }

        ArrayList<XYPair> nextFlowCells = waterCell.nextFlowCells(this);
        for (XYPair nextFlowCell : nextFlowCells) {
            waterCellStack.push(nextFlowCell);
        }
    }


    public boolean isEmpty(XYPair xyPair) {
        boolean isEmpty = ((!isWall(xyPair)) && (!isWater(xyPair)));
        return (isEmpty);// && isInBounds(xyPair));
    }

    public boolean isWall(XYPair xyPair) {
        boolean isWall = wallCells.contains(xyPair);
        return (isWall);//|| !isInBounds(xyPair));
    }

    public boolean isWater(XYPair xyPair) {
        boolean isWater = waterCells.contains(xyPair);
        return isWater;
    }

    public boolean isSolidWater(XYPair xyPair) {
        boolean boundedOnLeft;
        boolean boundedOnRight;

        if (!isWater(xyPair)) return false; // If it's not water, there's nothing else to talk about.

        // A water cell is "solid" if it's bounded on both sides by wall (or boundary).

        // Check to the left.
        XYPair nextCellLeft = xyPair.cellLeft();
        while (isWater(nextCellLeft)) {
            nextCellLeft = nextCellLeft.cellLeft(); // scan to the left until we hit a cell that is NOT water.
        }
        // If the first non-water cell is either a wall or a boundary
        // then we are bounded on that side.
        boundedOnLeft = isWall(nextCellLeft);

        // Check to the right.
        XYPair nextCellRight = xyPair.cellRight();
        while (isWater(nextCellRight)) {
            nextCellRight = nextCellRight.cellRight(); // scan to the right until we hit a cell that is NOT water.
        }
        // If the first non-water cell is either a wall or a boundary
        // then we are bounded on that side.
        boundedOnRight = isWall(nextCellRight);

        return (boundedOnLeft && boundedOnRight); // Are we bounded on both sides? If so, we're "solid".
    }

    public boolean isInBounds(XYPair xyPair) {
        // Only y matters for inBounds. x goes on forever.
        boolean isInBounds = (xyPair.getY() >= TopLeft.getY()) && (xyPair.getY() <= BottomRight.getY()); // y starts at 0 even though the walls don't.

        return isInBounds;
    }

    public void printReservoir() { // print everything
        int yMin = 0; // start at the source
        int yMax;

        if (DEBUG_PRINT_ONLY_WATER) {
            // find the maximum y coordinate of the waterCells. Print only just past that.
            for (XYPair waterCell : waterCells) {
                yMax = Integer.max(yMax, waterCell.getY());
            }
            yMax += 3; // Print a little past it.
            yMax = Integer.min(yMax, BottomRight.getY()); // But not past the bottom of the whole reservoir.
        } else { // print everything.
            yMax = BottomRight.getY();
        }

        printReservoir(yMin, yMax, null);
    }

    public void printReservoir(int yMin, int yMax, XYPair currentCell) {
        final int X_MARGIN = 5;
        for (int y = yMin; y <= yMax; y++) {
            String output = y + ": ";
            for (int x = TopLeft.getX() - X_MARGIN; x <= BottomRight.getX() + X_MARGIN; x++) {
                if (wallCells.contains(new XYPair(x, y))) {
                    output += "#";
                } else if ((x == waterSource.getX() && y == waterSource.getY())) {
                    output += "+";
                } else if (currentCell != null && x == currentCell.getX() && y == currentCell.getY()) {
                    output += "@";
                } else if (waterCells.contains(new XYPair(x, y))) {
                    output += "|";
                } else {
                    output += ".";
                }
            }
            System.out.println(output);
        }
        System.out.println(); //blank line after
    }
}
