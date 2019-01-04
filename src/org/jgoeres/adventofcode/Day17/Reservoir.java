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
    WaterCell waterSource = new WaterCell(500, 0, null); // per problem statement

    Stack<WaterCell> waterCellStack = new Stack<>();

//    XYPair one = new XYPair(1, 1);
//    XYPair two = new XYPair(1, 1);

    private static final boolean DEBUG_PRINT_RESERVOIR = false;
    private static boolean DEBUG_PRINT_ONLY_WATER = false;


    public Reservoir(String pathToFile) {

//        wallCells.add(one);
//        System.out.println(wallCells.contains(two));

        waterCells.add(waterSource);
        waterCellStack.push(waterSource);
        loadReservoir(pathToFile);
    }

    public WaterCell getWaterSource() {
        return waterSource;
    }

    public Stack<WaterCell> getWaterCellStack() {
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

        if (DEBUG_PRINT_RESERVOIR) {
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
        WaterCell waterCell = waterCellStack.pop();

        if (waterCell.getY() >= BottomRight.getY()) {
            // If we've run off the bottom of the scan...
            // Go back up the stack to see if there are any
            // "down" flows that haven't been processed.
            printReservoir();
            WaterCell previousWaterCell = waterCell.cellAbove();
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
            waterCellStack.pop(); // but also pop the immediate previous cell so we don't reprocess where the water turns the corner.
        }

        waterCells.add(waterCell);

        if (DEBUG_PRINT_ONLY_WATER) {
            printReservoir();
        }

        ArrayList<WaterCell> nextFlowCells = waterCell.nextFlowCells(this);
        for (WaterCell nextFlowCell : nextFlowCells) {
            waterCellStack.push(nextFlowCell);
        }

    }


    public boolean isEmpty(XYPair xyPair) {
        boolean isEmpty = ((!isWall(xyPair)) && (!isWater(xyPair)));
        return isEmpty && isInBounds(xyPair); // Cell must be empty AND be in bounds, otherwise it's not a valid place to go.
    }

    public boolean isWall(XYPair xyPair) {
        boolean isWall = wallCells.contains(xyPair);
        return isWall;
    }

    public boolean isWater(XYPair xyPair) {
        boolean isWater = waterCells.contains(xyPair);
        return isWater;
    }

    public boolean isInBounds(XYPair xyPair) {
        boolean isInBounds = ((xyPair.getX() >= TopLeft.getX()) && (xyPair.getX() <= BottomRight.getX())
                && (xyPair.getY() >= 0) && (xyPair.getY() <= BottomRight.getY())); // y starts at 0 even though the walls don't.
        return isInBounds;
    }

    public void printReservoir() {
        int yMax = Integer.MIN_VALUE;
        if (DEBUG_PRINT_ONLY_WATER) {
            // find the maximum y coordinate of the waterCells. Print only just past that.
            for (XYPair waterCell : waterCells) {
                yMax = Integer.max(yMax, waterCell.getY());
            }
            yMax += 3; // Print a little past it.
            yMax = Integer.min(yMax, BottomRight.getY()); // But not past the bottom of the whole reservoir.
        } else {
            yMax = BottomRight.getY();
        }

//        for (int y = 0; y <= BottomRight.getY(); y++) { // start at y=0 so we print the water source.
        for (int y = 0; y <= yMax; y++) { // start at y=0 so we print the water source.
            String output = y + ": ";
            for (int x = TopLeft.getX(); x <= BottomRight.getX(); x++) {
                if (wallCells.contains(new XYPair(x, y))) {
                    output += "#";
                } else if ((x == waterSource.getX() && y == waterSource.getY())) {
                    output += "+";
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
